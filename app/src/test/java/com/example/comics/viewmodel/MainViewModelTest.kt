package com.example.comics.viewmodel

import com.example.comics.CoroutinesTestRule
import com.example.comics.entity.ItemVO
import com.example.comics.entity.Resource
import com.example.comics.repository.ComicRepository
import com.example.comics.util.ProcessStatus
import com.example.comics.view.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private lateinit var viewModel: MainViewModel
    private val repository: ComicRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getComics returns success, comics LiveData should post success value`() = runBlocking {
        coEvery { repository.getComics() } returns Resource.Success(listOf(mockk<ItemVO>()))

        viewModel.getComics()

        val value = viewModel.comics.value
        assertTrue(value is Resource.Success && value.data.isNotEmpty())
    }

    @Test
    fun `when getComics returns error, comics LiveData should post fail value`() = runBlocking {
        coEvery { repository.getComics() } returns Resource.Fail(ProcessStatus.Fail, MOCK_EXCEPTION)

        viewModel.getComics()

        val value = viewModel.comics.value
        assertTrue(value is Resource.Fail && value.message == MOCK_EXCEPTION)
    }

    companion object {
        const val MOCK_EXCEPTION = "Error mockk"
    }
}
