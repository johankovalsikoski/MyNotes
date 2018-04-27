package com.kovalsikoski.johan.mynotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.cardview_note.view.*

class MyNotesAdapter (private  val notesList: MutableList<Note>,
                      private val context: Context, private val realm: Realm) : RecyclerView.Adapter<MyNotesAdapter.ViewHolder>() {

    fun newNote(note: Note){
        notesList.add(note)

        realm.beginTransaction()

        val realmNote: Note = realm.createObject(Note::class.java)
        realmNote.title = note.title
        realmNote.description = note.description

        realm.commitTransaction()

        notifyDataSetChanged()
    }

    fun removeNote(position: Int, id: Int){
        notesList.removeAt(position)

        realm.beginTransaction()

        val result = realm.where(Note::class.java)
                .equalTo("id",id)
                .findAll()

        result.deleteAllFromRealm()
        realm.commitTransaction()

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

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

                val id = it.id

                removeNote(position, id)
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