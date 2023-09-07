package com.example.comics.interactor

import com.example.comics.presenter.IPresenter
import com.example.comics.repository.network.NetworkService
import com.example.comics.util.safeRunDispatcher
import com.example.comics.util.Result.Success
import com.example.comics.util.Result.Failure

class Interactor(
    private val iPresenter: IPresenter,
    private val networkService: NetworkService = NetworkService()
) : IInteractor {


    override suspend fun getComics() {
        when (val result = safeRunDispatcher {
            networkService.getComics()
        }) {
            is Success -> iPresenter.setupList(result.data)
            is Failure -> iPresenter.error()
        }
    }

}