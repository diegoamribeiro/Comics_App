package com.example.comics.repository.network

import android.util.Log
import com.example.comics.entity.Resource
import com.example.comics.repository.ItemModel
import com.example.comics.util.ProcessStatus
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ComicService(
    private val service: IComicService
) {

    suspend fun getComics() : Resource<ItemModel> {
        return try {

            val response = service.getComics(
                ts = "1682982412",
                apikey = "b7e14bab409c70a5c4e7c2b319c09d7b",
                hash = "3482f01e9bf207a437a4b621c91339ad"
            )

            if (response.isSuccessful && response.body() != null){
                Log.d("***Service", response.body().toString())
                Resource.Success(response.body()!!)
            }else{
                var message = response.code().toString()
                if (response.body() != null){
                    message += " - ${response.message()}"
                }
                if (response.message().isNotBlank()){
                    message += " - ${response.message()}"
                }
                if (response.body() != null){
                    if (response.code() in 400..499){
                        Resource.Fail(ProcessStatus.WrongParameter,
                            response.errorBody().toString())
                    }else{
                        message += "${response.errorBody()}"
                        Resource.Fail(ProcessStatus.Fail, message)
                    }
                }else{
                    Resource.Fail(ProcessStatus.Fail, message)
                }
            }
        }catch (e: UnknownHostException){
            Resource.Fail(ProcessStatus.NoInternet, e.message ?: e.toString())
        } catch (e: SocketTimeoutException) {
            Resource.Fail(ProcessStatus.TimeOut, e.message ?: e.toString())
        } catch (e: Exception) {
            Resource.Fail(ProcessStatus.Fail, e.message ?: e.toString())
        }
    }

}