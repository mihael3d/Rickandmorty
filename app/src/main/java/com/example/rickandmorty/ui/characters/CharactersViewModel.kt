package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.Repository
import com.example.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(private val repository: Repository) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentCharactersResult: Flow<PagingData<Character>>? = null

    fun searchCharaters(queryString: String): Flow<PagingData<Character>> {
        val lastResult = currentCharactersResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Character>> = repository.getCharactersSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentCharactersResult = newResult
        return newResult
    }
}