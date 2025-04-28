package com.Mihin.BookStore.controllers

import com.Mihin.BookStore.domain.AuthorUpdateRequest
import com.Mihin.BookStore.domain.dto.AuthorDto
import com.Mihin.BookStore.domain.dto.AuthorUpdateRequestDto
import com.Mihin.BookStore.services.AuthorService
import com.Mihin.BookStore.toAuthorDto
import com.Mihin.BookStore.toAuthorEntity
import com.Mihin.BookStore.toAuthorUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path=["/v1/authors"])
class AuthorController(private val authorService: AuthorService) {

    @PostMapping
    fun createAuthor(@RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        return try {
            val createdAuthor = authorService.create(
                authorDto.toAuthorEntity()
            ).toAuthorDto()
            ResponseEntity(createdAuthor, HttpStatus.CREATED)
        } catch(ex: IllegalArgumentException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun readManyAuthor(): List<AuthorDto>{
        return authorService.list().map{ it.toAuthorDto()}
    }

    @GetMapping(path = ["/{id}"])
    fun readOneAuhtor(@PathVariable("id") id: Long) : ResponseEntity<AuthorDto>{
        val foundAuthor = authorService.get(id)?.toAuthorDto()
        return foundAuthor?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping(path =["/{id}"])
    fun fullUpdateAuthor(@PathVariable("id") id:Long, @RequestBody authorDto: AuthorDto): ResponseEntity<AuthorDto>{
        return try{
            val updateAuthor = authorService.fullUpdate(id, authorDto.toAuthorEntity())
            ResponseEntity(updateAuthor.toAuthorDto(), HttpStatus.OK)
        } catch(ex: IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping(path= ["/{id}"])
        fun partialUpdateAuthor(@PathVariable("id") id: Long, @RequestBody authorUpdateRequest: AuthorUpdateRequestDto): ResponseEntity<AuthorDto>{
            return try {
                val updatedAuthor = authorService.partialUpdate(id, authorUpdateRequest.toAuthorUpdateRequest())
                ResponseEntity(updatedAuthor.toAuthorDto(), HttpStatus.OK)
            } catch(ex: IllegalStateException){
                ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        }

    @DeleteMapping(path=["/{id}"])
    fun deleteAuthor(@PathVariable("id") id: Long) : ResponseEntity<Unit>{
        return try {
            authorService.delete(id)
            ResponseEntity(HttpStatus.NO_CONTENT)  // Successfully deleted
        } catch (ex: IllegalStateException) {
            ResponseEntity(HttpStatus.NOT_FOUND)  // Author not found
        }
    }
}