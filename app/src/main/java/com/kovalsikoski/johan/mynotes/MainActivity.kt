package com.kovalsikoski.johan.mynotes

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NoteInputDialog.NoteInputDialogListener {

    private val notesList: MutableList<NoteModel> = mutableListOf()

    private lateinit var adapter: MyNotesAdapter
    private lateinit var realm: Realm

    //REGION LIFE CYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        initRecyclerView()
        initFab()
        loadNotesFromRealm()

    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    //END REGION LIFE CYCLE

    //REGION PRIVATED METHODS

    private fun initRecyclerView(){
        val recyclerView = notesRecyclerView
        adapter = MyNotesAdapter(notesList, this, object : NoteInterface{

            override fun removeNoteFromRealmById(id: Int) {
                realm.beginTransaction()

                val result = realm.where(NoteModel::class.java)
                        .equalTo("id",id)
                        .and()
                        .equalTo("user", "") //implementar user
                        .findAll()

                result.deleteAllFromRealm()
                realm.commitTransaction()
            }

            override fun removeNoteFromAdapterByPosition(position: Int) {
                adapter.removeNoteFromAdapterByPosition(position)
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
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
        val allNotes = realm.where(NoteModel::class.java)
                .equalTo("user", "").findAll() //implementar user
        allNotes.forEach {
            notesList.add(it)
        }
    }

    //END REGION PRIVATED METHODS

    //REGION OVERRIDED METHODS

    override fun onNoteInputDialogPositiveButtonClicked(dialog: DialogInterface?, title: String, description: String) {
        val realmId = realm.where(NoteModel::class.java)
                .equalTo("user", "")//implementar user
                .max("id")
        val nextId: Int

        nextId = if(realmId==null){
            0
        } else {
            realmId.toInt() + 1
        }

        realm.beginTransaction()
        val noteObject = realm.createObject(NoteModel::class.java, nextId)
        noteObject.user = "" //implementar user
        noteObject.title = title
        noteObject.description = description
        realm.commitTransaction()

        notesList.clear()
        adapter.clear()
        loadNotesFromRealm()
    }

    override fun onNoteInputDialogNegativeButtonClicked(dialog: DialogInterface?) {
        dialog?.dismiss()
    }

    //END REGION OVERRIDED METHODS
}

