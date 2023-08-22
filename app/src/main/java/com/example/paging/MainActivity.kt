package com.example.paging

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging.api.RetrofitService
import com.example.paging.databinding.ActivityMainBinding
import com.example.paging.model.User
import com.example.paging.paging.UserPagerAdapter
import com.example.paging.repository.UserRepository
import com.example.paging.viewmodel.UserViewModel
import com.example.paging.viewmodel.UserViewModelFactory
 import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), UserPagerAdapter.ClickListner{
    lateinit var viewModel: UserViewModel
    private val adapter = UserPagerAdapter(this)
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = UserRepository(retrofitService)

        binding.quoteList.layoutManager = LinearLayoutManager(this)
        binding.quoteList.setHasFixedSize(true)
        binding.quoteList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(mainRepository)
        ).get(UserViewModel::class.java)
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                binding.progressDialog.isVisible = true
            else {
                binding.progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.getMovieList().observe(this@MainActivity) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }

    override fun clikedItem(user: User) {
        val intent = Intent(this@MainActivity, UserDetails::class.java)
        intent.putExtra("firstName", user.firstName)
        intent.putExtra("gender", user.gender)
        intent.putExtra("phone", user.phone)
        intent.putExtra("image", user.image)
        startActivity(intent)
    }


}