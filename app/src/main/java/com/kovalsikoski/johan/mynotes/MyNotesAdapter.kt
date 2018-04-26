package com.kovalsikoski.johan.mynotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.cardview_note.view.*

class MyNotesAdapter (private  val notesList: MutableList<Note>,
                      private val context: Context) : RecyclerView.Adapter<MyNotesAdapter.ViewHolder>() {

    var gambPosition: Int = -1

    fun newNote(note: Note){
        notesList.add(note)
        notifyDataSetChanged()
    }

    fun removeNote(position: Int){
        notesList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        Toast.makeText(context, "Removido item: $position", Toast.LENGTH_LONG).show()
    }

    private fun gambSetPosition(position: Int){
        gambPosition = position
    }

    fun gambGetPosition():Int = gambPosition

    override fun getItemCount(): Int = notesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder?.let {
            it.bindView(note)

            it.itemView.setOnLongClickListener {
                gambSetPosition(position)
                true
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(note: Note) {
            val title = itemView.titleCardViewTextView
            val description = itemView.descriptionCardViewTextView

            title.text = note.title
            description.text = note.description
        }
    }
}