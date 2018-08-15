package com.kovalsikoski.johan.mynotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.cardview_note.view.*

class MyNotesAdapter (private  val notesList: MutableList<Note>, private val context: Context,
                      private val realm: Realm, private val listenerNoteInterface: NoteInterface) : RecyclerView.Adapter<MyNotesAdapter.ViewHolder>() {

    fun removeNoteFromAdapterByPosition(position: Int){
        notesList.removeAt(position)

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
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
            val title = itemView.titleCardViewTextView
            val description = itemView.descriptionCardViewTextView

            title.text = note.title
            title.contentDescription = note.title

            description.text = note.description
            description.contentDescription = note.description
        }
    }
}