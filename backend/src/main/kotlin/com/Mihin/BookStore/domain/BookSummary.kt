package com.Mihin.BookStore.domain


data class BookSummary (
    val isbn: String,
    val title: String,
    val description: String,
    val image: String,
    val author:  AuthorSummary
)