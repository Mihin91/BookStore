package com.Mihin.BookStore.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.Mihin.BookStore.domain.entities.BookEntity

@Repository
interface BookRepository : JpaRepository<BookEntity,String>{

    fun findByAuthorId(id: Long): List<BookEntity>
}