package br.com.pedrobam.ceepws.note

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("notes")
class NoteController {

    @Autowired
    private lateinit var noteService: NoteService

    @GetMapping
    fun list(): ResponseEntity<List<Note>> {
        return ResponseEntity(noteService.list(), HttpStatus.OK)
    }

    @PostMapping
    fun add(@RequestBody note: Note): ResponseEntity<Note> {
        return ResponseEntity(noteService.add(note), HttpStatus.OK)
    }

    @PutMapping("{id}")
    fun alter(@PathVariable id: Long, @RequestBody note: Note): ResponseEntity<Note> {
        return ResponseEntity(noteService.alter(id, note), HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        noteService.delete(id)
        return ResponseEntity("Note is deleted successsfully", HttpStatus.OK)
    }
}