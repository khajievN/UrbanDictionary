package com.everything4droid.urbandickotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.databinding.WordItemBinding

/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class WordAdapter : RecyclerView.Adapter<WordAdapter.BindingHolder>() {

    lateinit var listener: OnItemClickListener
    private var wordList: ArrayList<Word> = ArrayList()


    fun addWords(list: List<Word>) {
        if (!wordList.isEmpty()) {
            wordList.clear()
        }
        wordList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        setOnItemClickListener(listener)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WordItemBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {

        val data = wordList[position]
        holder.binding.model = data
        holder.binding.root.setOnClickListener {
            listener.onClick(it, data)
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: Word)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class BindingHolder(var binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root)
}