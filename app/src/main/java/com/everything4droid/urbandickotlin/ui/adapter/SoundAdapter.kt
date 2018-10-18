package com.everything4droid.urbandickotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everything4droid.urbandickotlin.data.response.Sound
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.databinding.SoundItemBinding

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class SoundAdapter(val soundList: List<Sound>) : RecyclerView.Adapter<SoundAdapter.BindingHolder>() {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        setOnItemClickListener(listener)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SoundItemBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun getItemCount(): Int = soundList.size

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val data = soundList[position]
        holder.binding.model = data
        holder.binding.root.setOnClickListener {
            listener.onClick(it, data)
        }
    }




    interface OnItemClickListener {
        fun onClick(view: View, data: Sound)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class BindingHolder(var binding: SoundItemBinding) : RecyclerView.ViewHolder(binding.root)
}