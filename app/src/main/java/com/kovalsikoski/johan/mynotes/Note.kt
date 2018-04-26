package com.kovalsikoski.johan.mynotes

import io.realm.RealmObject

open class Note(var title: String = "",
                 var description: String = "") : RealmObject()
