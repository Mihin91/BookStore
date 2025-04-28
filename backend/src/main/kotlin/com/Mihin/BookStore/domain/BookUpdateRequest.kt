package com.Mihin.BookStore.domain

data class BookUpdateRequest (
    val title: String? = null,
    val description: String? = null,
    val image: String? = null,
)
