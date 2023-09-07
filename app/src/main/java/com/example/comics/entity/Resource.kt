package com.example.comics.entity

import com.example.comics.util.ProcessStatus

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Fail(val status: ProcessStatus, val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success -> "Success[data=$data]"
            is Fail -> "Error[status=$status, $message]"
            is Loading -> "Carregando"
        }
    }
}