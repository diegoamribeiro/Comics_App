package com.example.comics.repository

import com.example.comics.CoroutinesTestRule
import com.example.comics.entity.Resource
import com.example.comics.repository.network.ComicService
import com.example.comics.util.ProcessStatus
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private lateinit var repository: ComicRepository
    private val service: ComicService = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = ComicRepository(service)
    }

    @Test
    fun `when getComics from service returns success, repository should return list of ItemVO`() = runBlocking {
        coEvery { service.getComics() } returns Resource.Success(mockk<ItemModel>())

        val result = repository.getComics()

        assertTrue(result is Resource.Success && result.data.isNotEmpty())
    }

    @Test
    fun `when getComics from service returns error, repository should return fail resource`() = runBlocking {
        coEvery { service.getComics() } returns Resource.Fail(ProcessStatus.Fail, MOCK_EXCEPTION)

        val result = repository.getComics()

        assertTrue(result is Resource.Fail && result.message == MOCK_EXCEPTION)
    }

    companion object {
        const val MOCK_EXCEPTION = "Error mockk"
    }
}
