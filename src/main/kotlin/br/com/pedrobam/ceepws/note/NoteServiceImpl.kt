package br.com.pedrobam.ceepws.note

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NoteServiceImpl : NoteService {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    override fun list(title: String?, pageable: Pageable): List<Note> {
        return if (title.isNullOrEmpty()) {
            noteRepository.findAll(pageable).toList()
        } else {
            noteRepository.findByTitle(title, pageable)
        }
    }

    override fun add(note: Note): Note {
        return noteRepository.save(note)
    }

    override fun alter(id: Long, note: Note): Note {
        if (noteRepository.existsById(id)) {
            val safeNote = note.copy(id = id)
            return noteRepository.save(safeNote)
        }
        return Note()
    }

    override fun delete(id: Long) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id)
        }
    }
}