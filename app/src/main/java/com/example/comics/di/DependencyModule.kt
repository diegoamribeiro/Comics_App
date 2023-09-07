package com.example.comics.di

import com.example.comics.repository.ComicRepository
import com.example.comics.repository.network.ComicService
import com.example.comics.repository.network.IComicService
import com.example.comics.repository.network.NetworkModule
import com.example.comics.view.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModule {

    val moduleApp = module {
        single {
            NetworkModule().retrofitInstance().create(
                IComicService::class.java
            )
        }

        single<ComicService>{ ComicService(get()) }
        factory { ComicRepository(get()) }
        viewModel { MainViewModel(get()) }

    }
}