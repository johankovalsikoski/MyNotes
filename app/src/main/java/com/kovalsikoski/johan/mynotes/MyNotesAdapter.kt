package com.kovalsikoski.johan.mynotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cardview_note.view.*

class MyNotesAdapter (private  val notesList: MutableList<NoteModel>,
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
        fun bindView(noteModel: NoteModel, position: Int, listenerNoteInterface: NoteInterface) {

            val title = itemView.til_title
            val description = itemView.tv_description
            val deleteItem = itemView.iv_delete

            title.text = noteModel.title
            title.contentDescription = noteModel.title

            description.text = noteModel.description
            description.contentDescription = noteModel.description

            deleteItem.setOnClickListener {
                listenerNoteInterface.removeNoteFromRealmById(noteModel.id)
                listenerNoteInterface.removeNoteFromAdapterByPosition(position)
            }
        }
    }
}