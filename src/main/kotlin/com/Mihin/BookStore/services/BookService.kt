package com.Mihin.BookStore.services

import com.Mihin.BookStore.domain.BookSummary
import com.Mihin.BookStore.domain.entities.BookEntity

interface BookService {

    fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean>
}