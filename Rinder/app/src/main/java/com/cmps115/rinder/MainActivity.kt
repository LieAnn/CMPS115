package com.cmps115.rinder

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.Gson
import com.yuyakaido.android.cardstackview.*
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity(), CardStackListener, SharedPreferences.OnSharedPreferenceChangeListener {


    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(this, createSpots()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
        setContentView(R.layout.activity_main)
        setupCardStackView()
        setupButton()
    }

    override fun onBackPressed() {
        if (true) {
            this.recreate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")

    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 3) {
            paginate()
        }

        if (direction == Direction.Right) {
            val cards = adapter.getSpots()
            val card = cards.get(manager.topPosition-1)

            val builder = AlertDialog.Builder(this)

            builder.setTitle(card.name.toString())
                .setMessage(card.address.toString() + '\n' + card.contact.toString())
                .setPositiveButton("call resturant") { _, _ ->
                    val callIntent: Intent = Uri.parse("tel:${card.contact.toString()}").let { number ->
                        Intent(Intent.ACTION_DIAL, number)
                    }
                    startActivity(callIntent)

                }
                .setNegativeButton("Cancel") { _, _ ->
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                }
                .show()

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


        val setting = findViewById<View>(R.id.setting_button)
        setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.startActivity(intent)

            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show()
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

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        Log.d("onSharedPreferenc", "right before create")

    }


    private fun createSpots(): List<Spot> {

        val restrauntList = ArrayList<Spot>()
        val gson = Gson()

        try {
            val listA = assets.open("restaurants.json")
            val buffer = ByteArray(listA.available())
            listA.read(buffer)
            listA.close()
            val json = String(buffer, Charsets.UTF_8)

            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("restaurants")

            var index = 0

            while (index < jsonArray.length()) {
                val spots = gson.fromJson(jsonArray.get(index).toString(), Spot::class.java)
                restrauntList.add(spots)

                index++

            }


        } catch (e: Exception) {
            e.printStackTrace()

        }

        Log.d("createSpots", "right before filter")
        val fiteredList = filterSpots(restrauntList)
        restrauntList.clear()
        restrauntList.addAll(fiteredList)

        Log.d("createSpots", "Count number : ${restrauntList.size}")
        return restrauntList

    }


    private fun filterSpots(spots: List<Spot>): List<Spot> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val american = sharedPreferences.getBoolean("americanPref", true)
        val european = sharedPreferences.getBoolean("europeanPref", true)
        val asian = sharedPreferences.getBoolean("asianPref", true)
        val rates = sharedPreferences.getInt("ratingPref", 0)
        Log.d("filterSpots", "setting data ${rates}")
        val restrauntList = ArrayList<Spot>()
        val spotsArray = ArrayList<Spot>()
        spotsArray.addAll(spots)

        if (american) {
            restrauntList.addAll(spotsArray.filter { it.tags == "American" })
        }
        if (european) {
            restrauntList.addAll(spotsArray.filter { it.tags == "European" })
        }
        if (asian) {
            restrauntList.addAll(spotsArray.filter { it.tags == "Asian" })
        }

        spotsArray.clear()
        spotsArray.addAll(restrauntList.filter { it.rating >= rates })

        return spotsArray
    }

}