package com.Mihin.BookStore.services

import com.Mihin.BookStore.domain.AuthorUpdateRequest
import com.Mihin.BookStore.domain.entities.AuthorEntity

interface AuthorService {
    fun create(authorEntity: AuthorEntity): AuthorEntity

    fun list(): List<AuthorEntity>

    fun get(id: Long): AuthorEntity?

    fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity

    fun partialUpdate(id: Long, authorUpdate: AuthorUpdateRequest): AuthorEntity

    fun delete(id: Long)
}
