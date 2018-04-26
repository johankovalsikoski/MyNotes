package com.kovalsikoski.johan.mynotes

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NoteInputDialog.NoteInputDialogListener {

    private val myNotesList: MutableList<Note> = mutableListOf()

    lateinit var adapter: MyNotesAdapter
    lateinit var realm: Realm

    //REGION LIFE CYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initFab()

    }

    override fun onResume() {
        realm = Realm.getDefaultInstance()
        super.onResume()
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    //END REGION LIFE CYCLE

    //REGION PRIVATED METHODS

    private fun initRecyclerView(){
        val recyclerView = notesRecyclerView
        adapter = MyNotesAdapter(myNotesList, this)
        recyclerView.adapter = adapter

        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    private fun initFab(){
        val newNoteInputDialog = NoteInputDialog.newInstance(
                this,
                "SALVAR",
                "CANCELAR")

        fab.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .add(newNoteInputDialog, null)
                    .commitAllowingStateLoss()
        }
    }

    private fun loadNotesFromRealm(){
        val allNotes = realm.where(Note::class.java).findAll()
        allNotes.forEach {
            adapter.newNote(it)
        }
    }

    //END REGION PRIVATED METHODS

    //REGION OVERRIDED METHODS

    override fun onNoteInputDialogPositiveButtonClicked(dialog: DialogInterface?, title: String, description: String) {
        adapter.newNote(Note(title, description))

        realm.beginTransaction()

        val note: Note = realm.createObject(Note::class.java)
        note.title = title
        note.description = description

        realm.commitTransaction()
    }

    override fun onNoteInputDialogNegativeButtonClicked(dialog: DialogInterface?) {
        dialog?.dismiss()
    }

    //END REGION OVERRIDED METHODS
}

