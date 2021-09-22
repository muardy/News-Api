package com.ardy.jobsubmis.connect

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardy.jobsubmis.R
import com.ardy.jobsubmis.databinding.ItemApiBinding
import com.ardy.jobsubmis.web
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterModelView: RecyclerView.Adapter<AdapterModelView.WeatherViewHolder>() {

    private val mData = ArrayList<NewsDataApi>()

    fun setData(items: ArrayList<NewsDataApi>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): WeatherViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_api, viewGroup, false)
        return WeatherViewHolder(mView)
    }

    override fun onBindViewHolder(weatherViewHolder: WeatherViewHolder, position: Int) {
        weatherViewHolder.bind(mData[position])

        weatherViewHolder.itemView.setOnClickListener() {

            val activity = weatherViewHolder.itemView.context as Activity
            val intent = Intent(activity , web::class.java).apply {
                putExtra(web.ARG_section_username, mData[position])

            }
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = mData.size

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemApiBinding.bind(itemView)
        fun bind(gith: NewsDataApi) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(gith.photo)
                    .apply(RequestOptions().override(1000, 1000))
                    .into(binding.tvItemImage)
                binding.tvItemTitle.text = gith.namenews
                binding.tvItemDate.text = gith.date
                binding.tvItemDescription.text = gith.desk

            }
        }

    }
}