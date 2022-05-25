package com.signlingo.main.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.signlingo.databinding.ItemCharBinding

class CharAdapter(private val listChar: Array<String>) :
    RecyclerView.Adapter<CharAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listChar[position]
        if (data != null) {
            holder.bind(data)
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_POSITION,position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listChar.size

    class ListViewHolder(val binding: ItemCharBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){
            binding.tvChar.text = data
        }
    }

}