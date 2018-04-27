package com.kovalsikoski.johan.mynotes

import io.realm.RealmObject

open class Note(
        var id: Int = -1,
        var title: String = "",
        var description: String = "") : RealmObject()
