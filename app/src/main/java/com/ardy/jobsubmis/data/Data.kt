package com.ardy.jobsubmis.data

import com.ardy.jobsubmis.R

object Data {
    private val Name_Exercise = arrayOf("BBC",
        "CNN",
        "Sky Sports")



    private val Detail = arrayOf("Trusted News Portal",
        "Trusted News Portal",
        "Sports news portal")


    private val Images = intArrayOf(
        R.drawable.bbcn,
        R.drawable.cnn,
        R.drawable.sky
    )

    val listData: ArrayList<NewsData>
        get() {
            val list = arrayListOf<NewsData>()
            for (position in Name_Exercise.indices) {
                val latihan = NewsData()
                latihan.name = Name_Exercise[position]
                latihan.desk = Detail[position]
                latihan.photo = Images[position]
                list.add(latihan )
            }
            return list
        }
}