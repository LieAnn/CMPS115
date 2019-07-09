package com.cmps115.rinder


class AlbumVO {
    var thumb: String? = null
    var artist: String? = null
    var date: String? = null
    var type: String? = null
    var title: String? = null
    var song: ArrayList<String>? = null
    var introduce: String? = null


    override fun toString(): String {
        return "AlbumVO{" +
                "thumb='" + thumb + '\''.toString() +
                ", artist='" + artist + '\''.toString() +
                ", date='" + date + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", song=" + song +
                ", introduce='" + introduce + '\''.toString() +
                '}'.toString()

    }

}