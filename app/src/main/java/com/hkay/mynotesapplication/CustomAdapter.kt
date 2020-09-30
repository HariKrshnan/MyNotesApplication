package com.hkay.mynotesapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hkay.mynotesapplication.room.NotesEntity

class CustomAdapter(private val clickListener: ClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    private var notes = emptyList<NotesEntity>() // Cached copy of words

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = notes[position]
        holder.textView.text = current.textContent
        holder.itemView.setOnClickListener{
            clickListener.itemClickListener(current.textContent, position)
        }
        holder.cardView.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                clickListener.itemLongClickListener(current.textContent, position)
                return true
            }
        })
        holder.itemView.isLongClickable = true
    }

    override fun getItemCount() = notes.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById<TextView>(R.id.mTVtextView)
        val cardView: CardView = view.findViewById<CardView>(R.id.cardView)
    }

    internal fun setWords(notes: List<NotesEntity>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun itemClickListener(textContent: String?, position: Int)
        fun itemLongClickListener(textContent: String?, position: Int)
    }

}