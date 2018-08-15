package com.kovalsikoski.johan.mynotes

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration

@Suppress("unused")
class MyNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())

        Realm.init(this)

        val newConfiguration: RealmConfiguration = RealmConfiguration.Builder().name("myNotes.realm").build()
        Realm.setDefaultConfiguration(newConfiguration)
    }

}