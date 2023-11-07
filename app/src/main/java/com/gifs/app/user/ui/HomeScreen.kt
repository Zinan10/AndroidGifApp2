package com.gifs.app.user.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.gifs.app.user.R
import com.gifs.app.user.ui.viewmodel.TestViewModel
import com.gifs.app.user.util.BaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TestViewModel) {
    val state by viewModel.itemData.collectAsState()
    val context = LocalContext.current
    var query by rememberSaveable {
        mutableStateOf(viewModel.searchInputData.value)
    }
    when (val response = state) {
        is BaseState.Failure -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(id = R.string.retry))
            }
        }

        BaseState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is BaseState.Success -> {
            Column(Modifier.fillMaxSize()) {
                LazyColumn(contentPadding = PaddingValues(8.dp)) {
                    item {
                        SearchBar(
                            query = query,
                            onQueryChange = {
                                val tempQuery = it.takeUnless { it.isEmpty() }
                                if (query != tempQuery) {
                                    viewModel.getData(tempQuery ?: "cats")
                                    query = it
                                }
                            },
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                        ) {
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(response.data, key = {
                        it.id
                    }) {
                        val imageLoader = ImageLoader.Builder(context)
                            .components {
                                if (SDK_INT >= 28) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }
                            .build()
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context)
                                    .data(data = it.images.original.url).apply(block = {
                                        size(Size.ORIGINAL)
                                    }).build(),
                                placeholder = painterResource(id = R.drawable.loading),
                                imageLoader = imageLoader,

                                ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
