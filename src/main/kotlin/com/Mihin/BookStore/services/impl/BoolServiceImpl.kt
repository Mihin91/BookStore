package com.Mihin.BookStore.services.impl

import com.Mihin.BookStore.domain.BookSummary
import com.Mihin.BookStore.domain.entities.BookEntity
import com.Mihin.BookStore.repositories.AuthorRepository
import com.Mihin.BookStore.repositories.BookRepository
import com.Mihin.BookStore.services.BookService
import com.Mihin.BookStore.toBookEntity
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoolServiceImpl(
    val bookRepository: BookRepository,
    val authorRepository: AuthorRepository) : BookService {

        @Transactional
        override fun createUpdate(isbn: String, bookSummary: BookSummary): Pair<BookEntity, Boolean> {
            val normalisedBook = bookSummary.copy(isbn = isbn)
            val isExists = bookRepository.existsById(isbn)
            val author = authorRepository.findByIdOrNull(normalisedBook.author.id)
            checkNotNull(author)

            normalisedBook.toBookEntity(author)
            val savedBook = bookRepository.save(normalisedBook.toBookEntity(author))
            return Pair(savedBook, !isExists)
    }

}