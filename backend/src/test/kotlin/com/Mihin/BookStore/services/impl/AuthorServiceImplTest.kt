package com.Mihin.BookStore.services.impl

import com.Mihin.BookStore.domain.AuthorUpdateRequest
import com.Mihin.BookStore.domain.entities.AuthorEntity
import com.Mihin.BookStore.repositories.AuthorRepository
import com.Mihin.BookStore.testAuthorEntityA
import com.Mihin.BookStore.testAuthorEntityB
import com.Mihin.BookStore.testAuthorUpdateRequestA
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
@Transactional
class AuthorServiceImplTest @Autowired constructor(
    private val underTest: AuthorServiceImpl,
    private val authorRepository : AuthorRepository
) {
    @Test
    fun `test that save persists the Author in the database` () {
        val savedAuthor = underTest.create(testAuthorEntityA())
        assertThat(savedAuthor.id).isNotNull()

        val recalledAuthor = authorRepository.findByIdOrNull(savedAuthor.id!!)
        assertThat(recalledAuthor).isNotNull()
        assertThat(recalledAuthor!!).isEqualTo(
            testAuthorEntityA(id=savedAuthor.id)
        )
    }

    @Test
    fun `test that an Author with an ID throws an IllegalArgumentException ` (){
        assertThrows<IllegalArgumentException> {
            val existingAuthor = testAuthorEntityA(id=999)
            underTest.create(existingAuthor)
        }
    }

    @Test
    fun `test that list returns empty list when no authors in the database` () {
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    fun `test that list returns authors when authors present in the database` () {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val result = underTest.get(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)
    }

    @Test
    fun `test that get returns null when author not present in the database`() {
        val result = underTest.get(999)
        assertThat(result).isNull()
    }

    @Test
    fun `test that get returns author when author is present in the database`() {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val result = underTest.get(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)
    }

    @Test
    fun `test that full update successfully updates the author in the database `(){
        val existingAuthor = authorRepository.save(testAuthorEntityA())
        val existingAuthorId = existingAuthor.id!!
        val updateAuthor = testAuthorEntityB(id=existingAuthorId)

        val result = underTest.fullUpdate(existingAuthorId, updateAuthor)
        assertThat(result).isEqualTo(updateAuthor)

        val retrievedAuthor = authorRepository.findByIdOrNull(existingAuthorId)
        assertThat(retrievedAuthor).isNotNull()
        assertThat(retrievedAuthor).isEqualTo(updateAuthor)
    }

    @Test
    fun `test that full update throws IllegalStateException when Author does not exist in the database ` (){
        assertThrows<IllegalStateException>{
            val nonExistingAuthorId = 999L
            val updatedAuthor = testAuthorEntityB(id=nonExistingAuthorId)
            underTest.fullUpdate(nonExistingAuthorId, updatedAuthor)
        }
    }

    @Test
    fun `test that partial update throws IllegalStateException when Author does not exist in the database `(){
        assertThrows<IllegalStateException>{
            val nonExistingAuthorId = 999L
            val updatedRequest = testAuthorUpdateRequestA(id=nonExistingAuthorId)
            underTest.partialUpdate(nonExistingAuthorId, updatedRequest)
        }
    }

    @Test
    fun `test that partial update Author does not update Author when all values are null `(){
        val existingAuthor = authorRepository.save(testAuthorEntityA())
        val updatedAuthor = underTest.partialUpdate(existingAuthor.id!!, AuthorUpdateRequest())
        assertThat(updatedAuthor).isEqualTo(existingAuthor)
    }

    @Test
    fun `test that partial update Author updates author name `(){

        val newName = "New name"
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(
            name = newName
        )
        val authorUpdateRequest = AuthorUpdateRequest(
            name = newName
        )
        assertThatAuthorPartialUpdateIsUpdated(
           existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = authorUpdateRequest,
        )
    }


    @Test
    fun `test that partial update Author updates author age `(){

        val newAge = 50
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(
            age = newAge
        )
        val authorUpdateRequest = AuthorUpdateRequest(
            age = newAge
        )
        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = authorUpdateRequest,


            )

    }

    @Test
    fun `test that partial update Author updates author description `(){

        val newDescription = "A new description"
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(
            description = newDescription
        )
        val authorUpdateRequest = AuthorUpdateRequest(
            description = newDescription
        )
        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = authorUpdateRequest,

            )
    }

    @Test
    fun `test that partial update Author updates author image `() {

        val newImage = "new-image.jpeg"
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(
            image = newImage
        )
        val authorUpdateRequest = AuthorUpdateRequest(
            image = newImage
        )
        assertThatAuthorPartialUpdateIsUpdated(
            existingAuthor = existingAuthor,
            expectedAuthor = expectedAuthor,
            authorUpdateRequest = authorUpdateRequest,
            )
    }

    private fun assertThatAuthorPartialUpdateIsUpdated(
        existingAuthor: AuthorEntity,
        expectedAuthor: AuthorEntity,
        authorUpdateRequest: AuthorUpdateRequest
    ){

        // save existing author
        val savedExistingAuthor = authorRepository.save(existingAuthor)
        val existingAuthorsId = savedExistingAuthor.id!!

        // update author
        val updatedAuthor = underTest.partialUpdate(savedExistingAuthor.id!!, authorUpdateRequest)


        // set up expected author
        val expected = expectedAuthor.copy(id=existingAuthorsId)
        assertThat(updatedAuthor).isEqualTo(expected)

        val retrievedAuthor = authorRepository.findByIdOrNull(existingAuthorsId)
        assertThat(retrievedAuthor).isNotNull()

        assertThat(retrievedAuthor).isEqualTo(expected)
    }

    @Test
    fun `test that delete deleted and existing Author in the database `(){
        val existingAuthor = authorRepository.save(testAuthorEntityA())
        val existingAuthorsId = existingAuthor.id!!

        underTest.delete(existingAuthorsId)

        assertThat(
            authorRepository.existsById(existingAuthorsId)
        ).isFalse()
    }

    @Test
    fun `test that delete deleted an non-existing Author in the database `(){
        val nonExistingId = 999L

        underTest.delete(nonExistingId)

        assertThat(
            authorRepository.existsById(nonExistingId)
        ).isFalse()
    }

}


