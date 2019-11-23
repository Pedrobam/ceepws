package br.com.pedrobam.ceepws.note

interface NoteService {

    fun list(): List<Note>

    fun add(note: Note): Note

    fun alter(id: Long, note: Note): Note

    fun delete(id: Long)
}