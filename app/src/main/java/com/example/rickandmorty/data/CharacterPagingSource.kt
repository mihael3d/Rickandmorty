package com.example.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.api.RickAndMortyService
import com.example.rickandmorty.model.Character
import retrofit2.HttpException
import java.io.IOException

//private const val CHARACTER_STARTING_PAGE_INDEX = 1

// Character page API is based: https://rickandmortyapi.com/documentation/#character
class CharacterPagingSource(
    private val service: RickAndMortyService,
    private val name: String
) : PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
//        val position = params.key ?: CHARACTER_STARTING_PAGE_INDEX
        return try {
            val pageNumber = params.key ?: 1
            val response = service.searchCharacters(pageNumber,name)
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (response.results.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}




