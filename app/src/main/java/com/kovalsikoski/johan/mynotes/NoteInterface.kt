package com.kovalsikoski.johan.mynotes

interface NoteInterface {

    fun removeNoteFromRealmById(id: Int)

    fun removeNoteFromAdapterByPosition(position: Int)

}