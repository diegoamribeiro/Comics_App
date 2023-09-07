package com.example.comics.view

import com.example.comics.entity.ItemVO

interface IView {

    fun viewList(list: List<ItemVO>)

    fun refresh()

    fun error()

}