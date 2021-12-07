package com.example.restaurantreviewapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class RestaurantAdapter(private val imageModelArrayList: MutableList<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.review_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]


        holder.title.text = info.getTitle()
        holder.comment.text = info.getComment()
        holder.rating.rating = info.getRating().toFloat()

        //Picasso.get().load(info.getImage()).placeholder(R.drawable.progress_animation).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var title = itemView.findViewById<View>(R.id.review_layout_title) as TextView
        var rating = itemView.findViewById<View>(R.id.review_layout_rating) as RatingBar
        var comment = itemView.findViewById<View>(R.id.review_layout_comment) as TextView
        var image = itemView.findViewById<View>(R.id.review_layout_image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val msg = title.text
            val snackbar = Snackbar.make(v, "$msg", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}