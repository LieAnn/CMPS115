package com.cmps115.rinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


internal class CardStackAdapter(
    context: Context, private var spots: List<Spot> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var f_category: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
        var menu: ImageView = view.findViewById(R.id.menu_image)
        var rating: RatingBar = view.findViewById(R.id.ratingBar)

    }

    private val context: Context

    init {
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val spot = spots[position]


        holder.name.text = "${spot.name}"
        holder.f_category.text = spot.f_category
        holder.rating.setRating(spot.rating)
        Glide.with(holder.image)
                .load(spot.url)
                .into(holder.image)


        Glide.with(holder.menu)
            .load(spot.menu)
            .into(holder.menu)

        holder.itemView.setOnClickListener { v ->

            if (holder.image.getVisibility() == View.VISIBLE) {
                holder.image.setVisibility(View.INVISIBLE)
                holder.menu.setVisibility(View.VISIBLE)
            } else {
                holder.image.setVisibility(View.VISIBLE)
                holder.menu.setVisibility(View.INVISIBLE)
            }

            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<Spot>) {
        this.spots = spots
    }

    fun getSpots(): List<Spot> {
        return spots
    }


}
