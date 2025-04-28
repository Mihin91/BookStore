package com.Mihin.BookStore.services.impl

import com.Mihin.BookStore.domain.AuthorUpdateRequest
import com.Mihin.BookStore.domain.entities.AuthorEntity
import com.Mihin.BookStore.repositories.AuthorRepository
import com.Mihin.BookStore.services.AuthorService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository): AuthorService {
    override fun create(authorEntity: AuthorEntity): AuthorEntity {
        require(null == authorEntity.id)
        return authorRepository.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepository.findAll()
    }

    override fun get(id: Long): AuthorEntity? {
        return authorRepository.findByIdOrNull(id)
    }

    @Transactional
    override fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity {
        check(authorRepository.existsById(id))
        val normalisedAuthor =authorEntity.copy(id=id)
        return authorRepository.save(normalisedAuthor)
    }

    @Transactional
    override fun partialUpdate(id: Long, authorUpdate: AuthorUpdateRequest): AuthorEntity {
        val existingAuthor = authorRepository.findByIdOrNull(id)
        checkNotNull(existingAuthor)

        val updatedAuthor = existingAuthor.copy(
            name=authorUpdate.name ?: existingAuthor.name,
            age=authorUpdate.age ?: existingAuthor.age,
            description=authorUpdate.description ?: existingAuthor.description,
            image=authorUpdate.image ?: existingAuthor.image,
        )

       return authorRepository.save(updatedAuthor)
    }

    @Transactional
    override fun delete(id: Long) {
        authorRepository.deleteById(id)
    }

}