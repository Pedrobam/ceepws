package br.com.pedrobam.ceepws.repositories

import br.com.pedrobam.ceepws.model.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {

    fun findByTitle(title: String): Note
}