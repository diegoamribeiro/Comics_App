package com.example.comics.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.entity.ItemVO
import com.example.comics.entity.Resource
import com.example.comics.repository.ComicRepository
import com.example.comics.util.ProcessStatus
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ComicRepository
): ViewModel() {

    private val _comics = MutableLiveData<Resource<List<ItemVO>>>()
    val comics: LiveData<Resource<List<ItemVO>>> = _comics

    private var _alert = MutableLiveData<String?>()
    var alert: LiveData<String?> = _alert

    fun getComics() {
        viewModelScope.launch {
            _comics.value = Resource.Loading
            val resource = repository.getComics()
            _comics.value = resource
            if (resource is Resource.Fail){
                when(resource.status){
                    ProcessStatus.NoInternet -> {
                        _alert.value = "No Internet Connection"
                    }
                    ProcessStatus.TimeOut ->
                        _alert.value = "Timeout"

                    ProcessStatus.WrongParameter -> _alert.value =
                        "Unknown Fail"
                    else -> {
                        _alert.value = "Unknown Fail"
                    }
                }
                Resource.Fail(ProcessStatus.Fail, resource.toString())
            }
        }
    }

}