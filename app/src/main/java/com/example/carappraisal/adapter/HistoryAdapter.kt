package com.example.carappraisal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carappraisal.R
import com.example.carappraisal.model.History

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private val historyList: MutableList<History> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setData(newList: List<History>) {
        historyList.clear()
        historyList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandTextView: TextView = itemView.findViewById(R.id.brand_type)
        private val yearTextView: TextView = itemView.findViewById(R.id.year_history)
        private val gradeTextView: TextView = itemView.findViewById(R.id.grade_car)
        private val photoImageView: ImageView = itemView.findViewById(R.id.car_img_history)

        fun bind(historyItem: History) {
            brandTextView.text = historyItem.brand
            yearTextView.text = historyItem.year
            gradeTextView.text = historyItem.grade
            photoImageView.setImageBitmap(historyItem.photo)
        }
    }
}
