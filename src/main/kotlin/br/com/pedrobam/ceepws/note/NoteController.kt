package br.com.pedrobam.ceepws.note

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("notes")
class NoteController {

    @Autowired
    private lateinit var noteService: NoteService

    @GetMapping
    fun list(
        @RequestParam(required = false) title: String?,
        @PageableDefault(
            sort = ["id"], direction = Sort.Direction.ASC,
            page = 0,
            size = 10
        ) pageable: Pageable
    ): ResponseEntity<List<Note>> {
        return ResponseEntity(noteService.list(title, pageable), HttpStatus.OK)
    }

    @PostMapping
    fun add(@RequestBody note: Note): ResponseEntity<Note> {
        return ResponseEntity(noteService.add(note), HttpStatus.CREATED)
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