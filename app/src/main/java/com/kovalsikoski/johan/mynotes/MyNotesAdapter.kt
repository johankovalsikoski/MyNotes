package com.kovalsikoski.johan.mynotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cardview_note.view.*

class MyNotesAdapter (private  val notesList: MutableList<Note>,
                      private val context: Context,
                      private val listenerNoteInterface: NoteInterface) : RecyclerView.Adapter<MyNotesAdapter.ViewHolder>() {

    fun removeNoteFromAdapterByPosition(position: Int){
        notesList.removeAt(position)

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun clear(){
        notesList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = notesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_note, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.bindView(note, position, listenerNoteInterface)

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(note: Note, position: Int, listenerNoteInterface: NoteInterface) {

            val title = itemView.til_title
            val description = itemView.tv_description
            val deleteItem = itemView.iv_delete

            title.text = note.title
            title.contentDescription = note.title

            description.text = note.description
            description.contentDescription = note.description

            deleteItem.setOnClickListener {
                listenerNoteInterface.removeNoteFromRealmById(note.id)
                listenerNoteInterface.removeNoteFromAdapterByPosition(position)
            }
        }
    }
}