package com.cmps115.rinder

data class Spot(
        val id: Long = counter++,
        val name: String? = null,
        val f_category: String? = null,
        val url: String? = null,
        val contact: String? = null,
        val address: String? = null,
        val worktime: String? = null,
        val rating: Float =  5.0f,
        val menu: String? = null,
        val introduce : String? = null
)


{
    companion object {
        private var counter = 0L
    }
}


