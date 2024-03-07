package com.danielleitelima.resume.presentation.foundation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Suppress("UNCHECKED_CAST")
@Composable
inline fun <reified T : ViewModel> rememberViewModel(noinline factory: () -> T): T {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val savedStateRegistryOwner = checkNotNull(LocalSavedStateRegistryOwner.current)
    val viewModelFactory =
        object : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, Bundle()) {
            override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                return factory() as T
            }
        }
    return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(T::class.java)
}
