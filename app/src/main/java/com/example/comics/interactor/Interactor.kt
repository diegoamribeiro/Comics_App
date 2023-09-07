package com.example.comics.interactor

import com.example.comics.presenter.IPresenter
import com.example.comics.repository.ItemModel
import com.example.comics.repository.network.NetworkModule
import com.example.comics.util.safeRunDispatcher
import com.example.comics.util.Result.Success
import com.example.comics.util.Result.Failure

class Interactor(
    private val iPresenter: IPresenter,
    private val networkModule: NetworkModule = NetworkModule()
) : IInteractor {


    override suspend fun getComics() {
//        when (val result = safeRunDispatcher {
//            //networkModule.getComics()
//        }) {
//            is Success -> iPresenter.setupList(ItemModel())
//            is Failure -> iPresenter.error()
//        }
    }

}