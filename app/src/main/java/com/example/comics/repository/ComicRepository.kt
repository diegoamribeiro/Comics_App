package com.example.comics.repository

import android.util.Log
import com.example.comics.entity.ItemVO
import com.example.comics.entity.Resource
import com.example.comics.repository.network.ComicService

class ComicRepository(
    private val service: ComicService
) {

    suspend fun getComics(): Resource<List<ItemVO>> {
        val resource = service.getComics()

        return if (resource is Resource.Success) {
            val itemVOList = resource.data.data.results.map { resultModel ->
                resultModelToItemVO(resultModel)
            }
            Log.d("***Repo", itemVOList.toString())
            Resource.Success(itemVOList)
        } else {
            resource as Resource.Fail
        }
    }

    private fun resultModelToItemVO(resultModel: ResultModel): ItemVO {
        return ItemVO(
            image = "${resultModel.thumbnail.path}.${resultModel.thumbnail.extension}",
            title = resultModel.title,
            subtitle = resultModel.description ?: "No description"
        )
    }

}
