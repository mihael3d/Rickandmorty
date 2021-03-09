package com.example.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.api.RickAndMortyService
import com.example.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * Repository class. Work with local and remote data sources.
 */

class Repository(private val service: RickAndMortyService) {

    /**
     * Search characters whose match the query, exposed as a stream of data that will emit
     * every time get more data from the network.
     */
    fun getCharactersSearchResultStream(name: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {CharacterPagingSource(service = service,name = name)}
        ).flow
    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}