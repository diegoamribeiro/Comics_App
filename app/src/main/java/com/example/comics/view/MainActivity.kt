package com.example.comics.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.entity.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

        addObserver()

        setListeners()

        viewModel.getComics()
    }

    private fun setupAdapter() {
        adapter = Adapter()
        binding.listItem.layoutManager = LinearLayoutManager(this)
        binding.listItem.adapter = adapter
    }

    private fun addObserver() {
        viewModel.comics.observe(this@MainActivity) { resource ->
            when(resource) {
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                    binding.listItem.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.listItem.visibility = View.VISIBLE
                    binding.swipeRefresh.isRefreshing = false
                    adapter.setData(resource.data)
                }
                is Resource.Fail -> {
                    binding.listItem.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }

        viewModel.alert.observe(this){ alertInfo ->
            alertInfo.let {
                Toast.makeText(this, it!!, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getComics()
        }
    }
}
