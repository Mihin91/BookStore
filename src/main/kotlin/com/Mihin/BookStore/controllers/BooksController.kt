package com.Mihin.BookStore.controllers

import com.Mihin.BookStore.domain.dto.BookSummaryDto
import com.Mihin.BookStore.domain.dto.BookUpdateRequestDto
import com.Mihin.BookStore.exceptions.InvalidAuthorException
import com.Mihin.BookStore.services.BookService
import com.Mihin.BookStore.toBookSummary
import com.Mihin.BookStore.toBookSummaryDto
import com.Mihin.BookStore.toBookUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path=["/v1/books"])
class BooksController(val bookService: BookService) {

    @PutMapping(path = ["/{isbn}"])
    fun createFullUpdateBook(@PathVariable("isbn") isbn:String, @RequestBody book: BookSummaryDto): ResponseEntity<BookSummaryDto> {
        try {
            val (savedBook, isCreated) = bookService.createUpdate(isbn, book.toBookSummary())
            val responseCode = if (isCreated) HttpStatus.CREATED else HttpStatus.OK
            return ResponseEntity(savedBook.toBookSummaryDto(), responseCode)
        } catch (ex: InvalidAuthorException){
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        } catch (ex: IllegalStateException){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun readManyBooks(@RequestParam("author") authorId: Long?): List<BookSummaryDto> {
        return bookService.list(authorId).map {
            it.toBookSummaryDto()
        }
    }

    @GetMapping(path = ["/{isbn}"])
    fun readOneBook(@PathVariable("isbn") isbn:String): ResponseEntity<BookSummaryDto>{
        return bookService.get(isbn)?.let{ ResponseEntity(it.toBookSummaryDto(), HttpStatus.OK)}
            ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PatchMapping(path=["/{isbn}"])
    fun partialUpdateBook(@PathVariable("isbn") isbn:String,
                          @RequestBody bookUpdateRequestDto: BookUpdateRequestDto )
    : ResponseEntity<BookSummaryDto>{
        try{
            val updatedBook =  bookService.partialUpdate(isbn, bookUpdateRequestDto.toBookUpdateRequest())
            return ResponseEntity(updatedBook.toBookSummaryDto(), HttpStatus.OK)
        } catch (ex: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping(path=["/{isbn}"])
    fun deleteBook(@PathVariable("isbn") isbn:String):  ResponseEntity<Unit> {
        bookService.delete(isbn)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}