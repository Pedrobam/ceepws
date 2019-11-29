package br.com.pedrobam.ceepws.note

import org.springframework.data.domain.Pageable

interface NoteService {

    fun list(title: String?, pageable: Pageable): List<Note>

    fun add(note: Note): Note

    fun alter(id: Long, note: Note): Note

    fun delete(id: Long)
}