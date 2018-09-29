package com.kovalsikoski.johan.mynotes

import android.content.*
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import android.os.IBinder
import android.widget.Toast


class MainActivity : BaseActivity(), NoteInputDialog.NoteInputDialogListener {

    private val notesList: MutableList<NoteModel> = mutableListOf()

    private lateinit var adapter: MyNotesAdapter
    private lateinit var realm: Realm
    private lateinit var user: String
    private lateinit var serviceConnection: ServiceConnection
    //REGION LIFE CYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        user = intent.extras.getString("user")

        initRecyclerView()
        initFab()
        loadNotesFromRealm()
    }

    private fun startService(){
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                val meuBinder = iBinder as MyBindService.MyBinder
                val number = meuBinder.service.generateNumber()
                Toast.makeText(this@MainActivity, "Número gerado: $number", Toast.LENGTH_SHORT).show()
            }

            override fun onServiceDisconnected(componentName: ComponentName) {}
        }

        val intent = Intent(this, MyBindService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService(){
        unbindService(serviceConnection)
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
                        .equalTo("user", user) //implementar user
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
                .equalTo("user", user).findAll()
        allNotes.forEach {
            notesList.add(it)
        }
    }

    //END REGION PRIVATED METHODS

    //REGION OVERRIDED METHODS

    override fun onNoteInputDialogPositiveButtonClicked(dialog: DialogInterface?, title: String, description: String) {
        val realmId = realm.where(NoteModel::class.java).max("id")
        val nextId: Int

        nextId = if(realmId==null){
            0
        } else {
            realmId.toInt() + 1
        }

        realm.beginTransaction()
        val noteObject = realm.createObject(NoteModel::class.java, nextId)
        noteObject.user = user
        noteObject.title = title
        noteObject.description = description
        realm.commitTransaction()

        notesList.clear()
        adapter.clear()
        loadNotesFromRealm()

        startService()
        stopService()
    }

    override fun onNoteInputDialogNegativeButtonClicked(dialog: DialogInterface?) {
        dialog?.dismiss()
    }

    //END REGION OVERRIDED METHODS
}

