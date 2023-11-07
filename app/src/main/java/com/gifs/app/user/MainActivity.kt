package com.gifs.app.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gifs.app.user.ui.HomeScreen
import com.gifs.app.user.ui.theme.AndroidGifAppTheme
import com.gifs.app.user.ui.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidGifAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel)
                }
            }
        }
//        _binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.recyclerview.apply {
//            setHasFixedSize(true)
//            adapter = itemAdapter
//        }
//        setItemDataObserver()
//        setSearchQuery()
    }

//    private fun setSearchQuery() {
//        binding.searchView.setOnQueryTextListener(
//            object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(query: String?): Boolean {
//                    val tempQuery = query.takeUnless { it.isNullOrEmpty() }
//                    val lastSearchQuery = viewModel.searchInputData.value
//                    if (lastSearchQuery != tempQuery) {
//                        viewModel.getData(tempQuery ?: "cats")
//                    }
//                    return true
//                }
//            })
//    }

//    private fun setItemDataObserver() {
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.itemData.collectLatest {
//                    when (it) {
//                        is BaseState.Failure -> {
//                            setErrorVisibility(true)
//                            binding.tvError.text = it.errorMsg
//                            binding.btnRetry.setOnClickListener {
//                                setErrorVisibility(false)
//                                viewModel.getData(viewModel.searchInputData.value)
//                            }
//                        }
//
//                        is BaseState.Success -> {
//                            setProgressVisibility(false)
//                            val isEmpty = it.data.isEmpty()
//                            binding.recyclerview.isVisible = !isEmpty
//                            itemAdapter.submitList(it.data)
//                        }
//
//                        BaseState.Loading -> {
//                            setProgressVisibility(true)
//                        }
//                    }
//                }
//            }
//        }
//    }

//    private fun setErrorVisibility(isVisible: Boolean) {
//        setProgressVisibility(false)
//        binding.recyclerview.isVisible = !isVisible
//        binding.tvError.isVisible = isVisible
//        binding.btnRetry.isVisible = isVisible
//    }
}