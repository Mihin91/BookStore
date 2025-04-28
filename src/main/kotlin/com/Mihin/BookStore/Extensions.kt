package com.Mihin.BookStore

import com.Mihin.BookStore.domain.AuthorSummary
import com.Mihin.BookStore.domain.AuthorUpdateRequest
import com.Mihin.BookStore.domain.BookSummary
import com.Mihin.BookStore.domain.BookUpdateRequest
import com.Mihin.BookStore.domain.dto.*
import com.Mihin.BookStore.domain.entities.AuthorEntity
import com.Mihin.BookStore.domain.entities.BookEntity
import com.Mihin.BookStore.exceptions.InvalidAuthorException

fun AuthorEntity.toAuthorDto() =AuthorDto(
        id=this.id,
        name=this.name,
        age=this.age,
        description=this.description,
        image=this.image
)

fun AuthorEntity.toAuthorSummaryDto(): AuthorSummaryDto {
    val authorId = this.id?: throw InvalidAuthorException()
    return AuthorSummaryDto(
        id = authorId,
        name = this.name,
        image = this.image
    )
}


fun AuthorDto.toAuthorEntity() =AuthorEntity(
    id=this.id,
    name=this.name,
    age=this.age,
    description=this.description,
    image=this.image
)

fun AuthorUpdateRequestDto.toAuthorUpdateRequest() = AuthorUpdateRequest(
    id=this.id,
    name=this.name,
    age=this.age,
    description=this.description,
    image=this.image
)

fun BookSummary.toBookEntity(author: AuthorEntity) = BookEntity(
    isbn=this.isbn,
    title=this.title,
    description = this.description,
    image = this.image,
    author = author
)

fun AuthorSummaryDto.toAuthorSummary() = AuthorSummary(
    id = this.id,
    name = this.name,
    image = this.image,
)



fun BookSummaryDto.toBookSummary() = BookSummary(
    isbn=this.isbn,
    title=this.title,
    description = this.description,
    image = this.image,
    author = this.author.toAuthorSummary()
)

fun BookEntity.toBookSummaryDto()  = BookSummaryDto(
    isbn=this.isbn,
    title=this.title,
    description = this.description,
    image = this.image,
    author = author.toAuthorSummaryDto()
)

fun BookUpdateRequestDto.toBookUpdateRequest() = BookUpdateRequest(
    title = this.title,
    description = this.description,
    image = this.image
)