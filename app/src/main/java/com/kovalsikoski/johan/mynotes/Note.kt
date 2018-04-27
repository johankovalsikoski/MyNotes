package com.kovalsikoski.johan.mynotes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Note(
        @PrimaryKey
        var id: Int = -1,
        var title: String = "",
        var description: String = "") : RealmObject()
