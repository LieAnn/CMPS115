package com.cmps115.rinder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*
import java.util.*

class MainActivity : AppCompatActivity(), CardStackListener {

    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(this,createSpots()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       //setupNavigation()
        setupCardStackView()
        setupButton()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }


    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
        val skip = findViewById<View>(R.id.skip_button)
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        val rewind = findViewById<View>(R.id.rewind_button)
        rewind.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }

        val like = findViewById<View>(R.id.like_button)
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.Bottom)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getSpots()
        val new = old.plus(createSpots())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun reload() {
        val old = adapter.getSpots()
        val new = createSpots()
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addFirst(size: Int) {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                add(manager.topPosition, createSpot())
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addLast(size: Int) {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            addAll(List(size) { createSpot() })
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeFirst(size: Int) {
        if (adapter.getSpots().isEmpty()) {
            return
        }

        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(manager.topPosition)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeLast(size: Int) {
        if (adapter.getSpots().isEmpty()) {
            return
        }

        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(this.size - 1)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun replace() {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            removeAt(manager.topPosition)
            add(manager.topPosition, createSpot())
        }
        adapter.setSpots(new)
        adapter.notifyItemChanged(manager.topPosition)
    }

    private fun swap() {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            val first = removeAt(manager.topPosition)
            val last = removeAt(this.size - 1)
            add(manager.topPosition, last)
            add(first)
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }
    private fun createSpot(): Spot {
        return Spot(
            name = "Lillian's Italian Kitchen",
            f_category = "Italian",
            url = "https://b.zmtcdn.com/data/reviews_photos/dc6/3eee375ec1296cacce40619edfa78dc6.jpg"
        // address: 1148 Soquel Ave, Santa Cruz, CA 95062
        )
    }

//    private fun createSpot(): Spot {
//        return Spot(
//            name = "Sandwich with Boiled egg",
//            f_category = "sandwich",
//            url = "https://source.unsplash.com/fdlZBWIP0aM/600x800"
//        )
//    }


    private fun createSpots(): List<Spot> {
        val spots = ArrayList<Spot>()
        spots.add(
            Spot(
                name = "Lillian's Italian Kitchen",
                f_category = "Italian",
                url = "https://b.zmtcdn.com/data/reviews_photos/dc6/3eee375ec1296cacce40619edfa78dc6.jpg"
                // address: 1148 Soquel Ave, Santa Cruz, CA 95062
            )
        )
        spots.add(
            Spot(
                name = "Crow's Nest Restaurant",
                f_category = "American",
                url = "https://s3-media1.fl.yelpcdn.com/bphoto/wuNZAIXYO8DkxDgPACzGKQ/o.jpg"
                // address: 2218 E Cliff Dr, Santa Cruz, CA 95062
            )
        )
        spots.add(
            Spot(
                name = "Hula's Island Grill",
                f_category = "Californian",
                url = "https://s3-media2.fl.yelpcdn.com/bphoto/F7X0FHSMk8GnM8vx3LQ6iA/o.jpg"
                // address: 221 Cathcart St, Santa Cruz, CA 95060
            )
        )
        spots.add(
            Spot(
                name = "Zachary's Restaurant",
                f_category = "Breakfast",
                url = "https://s3-media1.fl.yelpcdn.com/bphoto/xihk4YE9qQV99WGt-tR31w/o.jpg"
                // address: 819 Pacific Ave, Santa Cruz, CA 95060
            )
        )
        spots.add(
            Spot(
                name = "Surfrider Cafe",
                f_category = "American",
                url = "https://s3-media4.fl.yelpcdn.com/bphoto/ExOrBH9jbLZ6Lv8E1763zQ/o.jpg"
                // address: 429 Front St, Santa Cruz, CA 95060
            )
        )
        spots.add(
            Spot(
                name = "El Palomar Restaurant",
                f_category = "Mexican",
                url = "https://s3-media3.fl.yelpcdn.com/bphoto/SWP3hctF_sL692tRTjFOxA/o.jpg"
            )
        )

//        spots.add(
//            Spot(
//                name = "Sandwich with Boiled egg",
//                f_category = "sandwich",
//                url = "https://source.unsplash.com/fdlZBWIP0aM/600x800"
//            )
//        )
//        spots.add(
//            Spot(
//                name = "Autumn Soup",
//                f_category = "soup",
//                url = "https://source.unsplash.com/w6ftFbPCs9I/600x800"
//            )
//        )
//        spots.add(
//            Spot(
//                name = "Blueberry Pancake",
//                f_category = "pancake",
//                url = "https://source.unsplash.com/P1aohbiT-EY/600x800"
//            )
//        )
//        spots.add(
//            Spot(
//                name = "Organic Salad",
//                f_category = "salad",
//                url = "https://source.unsplash.com/EvoIiaIVRzU/600x800"
//            )
//        )
//        spots.add(
//            Spot(
//                name = "Dinner Steak",
//                f_category = "steak",
//                url = "https://source.unsplash.com/auIbTAcSH6E/600x800"
//            )
//        )
//        spots.add(
//            Spot(
//                name = "Nothing Better than Pasta",
//                f_category = "pasta",
//                url = "https://source.unsplash.com/-F_5g8EEHYE/600x800"
//            )
//        )


        return spots
    }
}