package com.Mihin.BookStore.services

import com.Mihin.BookStore.domain.BookSummary
import com.Mihin.BookStore.domain.BookUpdateRequest
import com.Mihin.BookStore.domain.entities.BookEntity

interface BookService {

    fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean>

    fun list(authorId: Long?=null): List<BookEntity>

    fun get(isbn: String): BookEntity?

    fun partialUpdate(isbn: String, bookUpdateRequest: BookUpdateRequest): BookEntity

    fun delete(isbn: String)
}