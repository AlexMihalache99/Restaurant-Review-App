package com.example.restaurantreviewapp

class RestaurantModel {
    private var title: String? = null
    private var rating: Double = 0.0
    private var comment: String? = null
    private var image: String? = null


    fun getTitle(): String {
        return title.toString()
    }

    fun setTitle(name: String) {
        this.title = name
    }

    fun getRating(): Double {
        return rating.toDouble()
    }

    fun setRating(rating: Double) {
        this.rating = rating
    }

    fun getComment(): String {
        return comment.toString()
    }

    fun setComment(comment: String) {
        this.comment = comment
    }

    fun getImage(): String {
        return image.toString()
    }

    fun setImage(image: String) {
        this.image = image
    }
}