package com.example.comics.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.entity.ItemVO
import com.example.comics.entity.Resource
import com.example.comics.presenter.Presenter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    //private val interactor: Interactor = Interactor(Presenter(this))
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getComics()
        setupAdapter()
        setListeners()
        addObserver()
        //refresh()

        //swipeList()
    }

    private fun addObserver(){
        viewModel.comics.observe(this@MainActivity){ resource ->
            when(resource){
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    adapter.setData(resource.data)
                }
                is Resource.Fail -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = Adapter()
        binding.listItem.layoutManager = LinearLayoutManager(this)
        binding.listItem.adapter = adapter
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getComics()
            binding.swipeRefresh.isRefreshing = false
        }
    }



//    override fun refresh() {
//        with(binding) {
//            this?.swipeRefresh?.isRefreshing = true
//            lifecycle.coroutineScope.launch {
//                interactor.getComics()
//            }
//        }
//    }

//    override fun viewList(list: List<ItemVO>) {
//        with(binding) {
//            this?.errorTV?.visibility = View.GONE
//            this?.listItem?.visibility = View.VISIBLE
//            this?.listItem?.adapter = Adapter(list)
//            this?.listItem?.layoutManager = LinearLayoutManager(this@MainActivity)
//            this?.swipeRefresh?.isRefreshing = false
//        }
//    }
//
//    override fun error() {
//        with(binding) {
//            this?.listItem?.visibility = View.GONE
//            this?.errorTV?.visibility = View.VISIBLE
//            this?.swipeRefresh?.isRefreshing = false
//        }
//    }

}