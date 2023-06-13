package com.example.carappraisal.adapter

import android.content.Intent
import android.media.metrics.Event
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carappraisal.R
import com.example.carappraisal.model.Brand
import com.example.carappraisal.ui.InsertActivity

class HomeAdapter(private val listBrand: ArrayList<Brand>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    private var listener: AdapterView.OnItemClickListener? = null

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val photo: ImageView = itemView.findViewById(R.id.brand_logo)
        val brandName: TextView = itemView.findViewById(R.id.brand_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_brand, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ListViewHolder, position: Int) {
        val (brandName,photo) = listBrand[position]
        holder.brandName.text = brandName
        holder.photo.setImageResource(photo)

        holder.itemView.setOnClickListener {
            val brand = listBrand[position]
            var name: String = brand.name

            val intent = Intent(holder.itemView.context, InsertActivity::class.java)
            intent.putExtra("names", name)
            holder.itemView.context.startActivities(arrayOf(intent))
        }

    }

    override fun getItemCount(): Int = listBrand.size

    interface OnItemClickListener : AdapterView.OnItemClickListener{
        fun onItemClick(brand: Brand)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

}