package br.com.pedrobam.ceepws.note

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {

    fun findByTitle(title: String, pageable: Pageable): List<Note>
}