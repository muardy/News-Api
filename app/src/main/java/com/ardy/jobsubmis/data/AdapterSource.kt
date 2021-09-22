package com.ardy.jobsubmis.data

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ardy.jobsubmis.R
import com.ardy.jobsubmis.api
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterSource (val listLatih: ArrayList<NewsData>) : RecyclerView.Adapter<AdapterSource.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_title)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_description)
        var imgPhoto: ImageView = itemView.findViewById(R.id.tv_item_image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listLatih.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listLatih[position]
        Glide.with(holder.itemView.context)
            .load(news.photo)
            .apply(RequestOptions().override(1000, 1000))
            .into(holder.imgPhoto)
        holder.tvName.text = news.name
        holder.tvDetail.text = news.desk
        holder.itemView.setOnClickListener {


            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, api::class.java).apply {
                if(holder.adapterPosition == 0 ) {
                    putExtra(api.ARG_section_nol, listLatih[holder.adapterPosition])
                }

                if(holder.adapterPosition == 1 ) {
                    putExtra(api.ARG_section_satu,listLatih[holder.adapterPosition])
                }
                if(holder.adapterPosition == 2 ) {
                    putExtra(api.ARG_section_dua,listLatih[holder.adapterPosition])
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)

        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: NewsData)
    }


}