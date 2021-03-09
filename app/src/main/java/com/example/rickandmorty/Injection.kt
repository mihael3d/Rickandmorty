package com.example.rickandmorty

import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.api.RickAndMortyService
import com.example.rickandmorty.data.Repository
import com.example.rickandmorty.ui.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [Repository] based on the [RickAndMortyService]
     */
    private fun provideRepository(): Repository {
        return Repository(RickAndMortyService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository())
    }
}