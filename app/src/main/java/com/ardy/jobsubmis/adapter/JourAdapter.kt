package com.ardy.jobsubmis.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardy.jobsubmis.R
import com.ardy.jobsubmis.crud
import com.ardy.jobsubmis.databinding.ItemJournalistBinding
import com.ardy.jobsubmis.entity.jour

class JourAdapter(private val activity: Activity) : RecyclerView.Adapter<JourAdapter.NoteViewHolder>() {
    var listNotes = ArrayList<jour>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)

            notifyDataSetChanged()
        }

    fun addItem(jours: jour) {
        this.listNotes.add(jours)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, jour: jour) {
        this.listNotes[position] = jour
        notifyItemChanged(position, jour)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_journalist, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = this.listNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemJournalistBinding.bind(itemView)
        fun bind(jour: jour) {
            binding.tvItemTitle.text = "Name : "  + jour.title
            binding.tvItemDate.text = jour.date
            binding.tvItemDescription.text = "Date of birth : " + jour.birth
            binding.cvItemNote.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                override fun onItemClicked(view: View, position: Int) {
                    val intent = Intent(activity, crud::class.java)
                    intent.putExtra(crud.EXTRA_POSITION, position)
                    intent.putExtra(crud.EXTRA_NOTE, jour)
                    activity.startActivityForResult(intent, crud.REQUEST_UPDATE)
                }
            }))
        }
    }
}