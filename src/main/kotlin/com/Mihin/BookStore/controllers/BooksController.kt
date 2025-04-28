package com.Mihin.BookStore.controllers

import com.Mihin.BookStore.domain.dto.BookSummaryDto
import com.Mihin.BookStore.exceptions.InvalidAuthorException
import com.Mihin.BookStore.services.BookService
import com.Mihin.BookStore.toBookSummary
import com.Mihin.BookStore.toBookSummaryDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BooksController(val bookService: BookService) {

    @PutMapping(path = ["/v1/books/{isbn}"])
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

}