package com.cmps115.rinder

data class Spot(
        val id: Long = counter++,
        val name: String,
        val f_category: String,
        val url: String
) {
    companion object {
        private var counter = 0L
    }
}
