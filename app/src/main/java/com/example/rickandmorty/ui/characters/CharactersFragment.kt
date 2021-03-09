package com.example.rickandmorty.ui.characters

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.rickandmorty.Injection
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


class CharactersFragment : Fragment(R.layout.fragment_characters) {


    private  var _binding : FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharactersViewModel
    private val adapter = CharactersAdapter()

    private var searchJob: Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharactersBinding.bind(view)

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(CharactersViewModel::class.java)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchCharacter.text?.trim()
            .toString())
    }

    private fun initAdapter() {
      binding.list.adapter = adapter
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchCharaters(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initSearch(query: String) {
        binding.searchCharacter.setText(query)

        binding.searchCharacter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateCharacterListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchCharacter.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateCharacterListFromInput()
                true
            } else {
                false
            }
        }

        // Scroll to top when the list is refreshed from network.
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow
            // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
            // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter {it.refresh is LoadState.NotLoading  }
                .collect { binding.list.scrollToPosition(0) }
        }
    }

    private fun updateCharacterListFromInput() {
        binding.searchCharacter.text?.trim().let {
            if (it != null) {
                if (it.isNotEmpty()) {
                    search(it.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
    }



}