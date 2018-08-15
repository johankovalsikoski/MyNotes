package com.kovalsikoski.johan.mynotes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NoteModel(
        @PrimaryKey
        var id: Int = -1,
        var user: String = "",
        var title: String = "",
        var description: String = "") : RealmObject()
