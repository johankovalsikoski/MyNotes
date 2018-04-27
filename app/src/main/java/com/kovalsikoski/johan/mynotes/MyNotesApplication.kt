package com.kovalsikoski.johan.mynotes

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

@Suppress("unused")
class MyNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val newConfiguration: RealmConfiguration = RealmConfiguration.Builder().name("myNotes.realm").build()
        Realm.setDefaultConfiguration(newConfiguration)
    }

}