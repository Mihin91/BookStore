package com.Mihin.BookStore.domain.dto

import com.Mihin.BookStore.domain.entities.AuthorEntity

data class BookDto (
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author:  AuthorDto
)