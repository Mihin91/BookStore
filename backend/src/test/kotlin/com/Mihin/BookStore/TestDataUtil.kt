package com.Mihin.BookStore

import com.Mihin.BookStore.domain.AuthorSummary
import com.Mihin.BookStore.domain.AuthorUpdateRequest
import com.Mihin.BookStore.domain.BookSummary
import com.Mihin.BookStore.domain.dto.AuthorDto
import com.Mihin.BookStore.domain.dto.AuthorSummaryDto
import com.Mihin.BookStore.domain.dto.AuthorUpdateRequestDto
import com.Mihin.BookStore.domain.dto.BookSummaryDto
import com.Mihin.BookStore.domain.entities.AuthorEntity
import com.Mihin.BookStore.domain.entities.BookEntity

const val BOOk_A_ISBN = "978-089-230342-0777"

fun testAuthorDtoA(id: Long? = null) = AuthorDto (
        id=id,
        name = "John Doe",
        age =30,
        description = "some-description",
        image = "author-image.jpeg"
)

fun testAuthorEntityA(id: Long? = null) = AuthorEntity (
    id=id,
    name = "John Doe",
    age =30,
    description = "some-description",
    image = "author-image.jpeg"
)

fun testAuthorEntityB(id: Long? = null) = AuthorEntity(
    id= id,
    name="Don Joe",
    age=65,
    description="Some other description",
    image="some-other-image.jpeg"
)

fun testAuthorSummaryDtoA(id: Long) = AuthorSummaryDto(
    id=id,
    name = "John Doe",
    image = "author-image.jpeg"
)

fun testAuthorSummaryA(id: Long) = AuthorSummary(
    id=id,
    name = "John Doe",
    image = "author-image.jpeg"
)



fun testAuthorUpdateRequestDtoA(id: Long? = null) = AuthorUpdateRequestDto(
    id=999L,
    name= "John Doe",
    age=30,
    description = "some description",
    image = "author-image.jpeg"
)


fun testAuthorUpdateRequestA(id: Long? = null) = AuthorUpdateRequest(
    id=999L,
    name= "John Doe",
    age=30,
    description = "some description",
    image = "author-image.jpeg"
)

fun testBookEntityA(isbn: String, author: AuthorEntity) = BookEntity(
    isbn=isbn,
    title = "Test Book A",
    description = "A test book",
    image = "book-image.jpeg",
    author = author
)


fun testBookSummaryDtoA(isbn: String, author: AuthorSummaryDto) = BookSummaryDto(
    isbn=isbn,
    title="Test Book A",
    description = "A test book",
    image = "book-image.jpeg",
    author=author

)

fun testBookSummaryA(isbn: String, author: AuthorSummary) = BookSummary(
    isbn=isbn,
    title="Test Book A",
    description = "A test book",
    image = "book-image.jpeg",
    author=author

)


fun testBookSummaryB(isbn: String, author: AuthorSummary) = BookSummary(
    isbn=isbn,
    title="Test Book B",
    description = "Another test book",
    image = "book-image-B.jpeg",
    author=author

)











