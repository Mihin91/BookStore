package com.Mihin.BookStore.services.impl

import com.Mihin.BookStore.*
import com.Mihin.BookStore.domain.AuthorSummary
import com.Mihin.BookStore.domain.BookUpdateRequest
import com.Mihin.BookStore.repositories.AuthorRepository
import com.Mihin.BookStore.repositories.BookRepository
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull


@SpringBootTest
@Transactional
class BookServiceImplTest @Autowired constructor(
    private val underTest: BookServiceImpl,
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {

    @Test
    fun `test that createUpdate throws IllegalStateException when Author does not exist` (){
        val authorSummary = AuthorSummary(id=999L)
        val bookSummary = testBookSummaryA(BOOk_A_ISBN, authorSummary)
        assertThrows<IllegalStateException>{
            underTest.createUpdate(BOOk_A_ISBN, bookSummary)
        }

    }

    @Test
    fun `test that createUpdate successfully creates book in hte database ` () {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()
        val authorSummary = AuthorSummary(id = savedAuthor.id!!)
        val bookSummary = testBookSummaryA(BOOk_A_ISBN, authorSummary)
        val (savedBook, isCreated) = underTest.createUpdate(BOOk_A_ISBN, bookSummary)
        assertThat(savedBook).isNotNull()

        val recalledBook = bookRepository.findByIdOrNull(BOOk_A_ISBN)
        assertThat(recalledBook).isNotNull()
        assertThat((recalledBook)).isEqualTo((savedBook))
        assertThat(isCreated).isTrue()
    }

    @Test
    fun `test that createUpdate successfully updates book in hte database ` () {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val authorSummary = AuthorSummary(id = savedAuthor.id!!)
        val bookSummary = testBookSummaryB(BOOk_A_ISBN, authorSummary)
        val (updatedBook, isCreated) = underTest.createUpdate(BOOk_A_ISBN, bookSummary)
        assertThat(updatedBook).isNotNull()

        val recalledBook = bookRepository.findByIdOrNull(BOOk_A_ISBN)
        assertThat(recalledBook).isNotNull()
        assertThat(isCreated).isFalse()
    }


    @Test
    fun `test that list returns an empty list when no books in the database ` (){
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    fun `test that list returns book when books in the database ` (){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val result = underTest.list()
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(savedBook)
    }

    @Test
    fun `test that list returns no books when the author ID does not match `(){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val result = underTest.list(authorId = savedAuthor.id!! + 1)
        assertThat(result).hasSize(0)
    }

    @Test
    fun `test that list returns books when the author ID does match `(){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val result = underTest.list(authorId = savedAuthor.id)
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(savedBook)
    }


    @Test
    fun `test that get returns null when book not found in the database `(){
        val result = underTest.get(BOOk_A_ISBN)
        assertThat(result).isNull()
    }

    @Test
    fun `test that get returns  book when the book found in the database `(){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val result = underTest.get(savedBook.isbn)
        assertThat(result).isEqualTo(savedBook)

    }

    @Test
    fun `test that partialUpdate throws IllegalStateException when the Book does not exist in the database` (){
        assertThrows<IllegalStateException>{
            val bookUpdateRequest = BookUpdateRequest(
                title = "A new title"
            )
            underTest.partialUpdate(BOOk_A_ISBN, bookUpdateRequest)
        }
    }

    @Test
    fun ` test that partialUpdate updates the tile of an existing book`(){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val newTitle = "A new title"
        val bookUpdateRequest = BookUpdateRequest(
            title = newTitle
        )
        val result = underTest.partialUpdate(BOOk_A_ISBN, bookUpdateRequest)
        assertThat(result.title).isEqualTo(newTitle)
    }

    @Test
    fun ` test that partialUpdate updates the description of an existing book`(){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val newDescription = "A new description"
        val bookUpdateRequest = BookUpdateRequest(
            description = newDescription
        )
        val result = underTest.partialUpdate(BOOk_A_ISBN, bookUpdateRequest)
        assertThat(result.description).isEqualTo(newDescription)
    }

    @Test
    fun ` test that partialUpdate updates the image of an existing book`(){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        val newImage = "A new image"
        val bookUpdateRequest = BookUpdateRequest(
            image = newImage
        )
        val result = underTest.partialUpdate(BOOk_A_ISBN, bookUpdateRequest)
        assertThat(result.image).isEqualTo(newImage)
    }

    @Test
    fun `test that delete successfully deletes a book in the database ` (){
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        assertThat(savedAuthor).isNotNull()

        val savedBook = bookRepository.save(testBookEntityA(BOOk_A_ISBN, savedAuthor))
        assertThat(savedBook).isNotNull()

        underTest.delete(BOOk_A_ISBN)

        val result = bookRepository.findByIdOrNull(BOOk_A_ISBN)
        assertThat(result).isNull()
    }

    @Test
    fun `test that delete successfully deletes a book and does not exist in the database `() {
        underTest.delete(BOOk_A_ISBN)

        val result = bookRepository.findByIdOrNull(BOOk_A_ISBN)
        assertThat(result).isNull()
    }

}

