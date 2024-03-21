package com.example.marvelcharacters.fragments.options

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelcharacters.adapter.CharacterAdapter
import com.example.marvelcharacters.animationUtils.AnimationUtils
import com.example.marvelcharacters.databinding.FragmentHomeBinding
import com.example.marvelcharacters.retrofit.MarvelCharacter
import com.example.marvelcharacters.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var editTextSearch: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var characterAdapter: CharacterAdapter
    private var loadingTextHandler: Handler? = null
    private var loadingTextRunnable: Runnable? = null
    private var charactersList: List<MarvelCharacter> = emptyList()
    private var isFetchingData = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        fetchData()
    }

    private fun initializeViews() {
        editTextSearch = binding.editTextSearch
        progressBar = binding.loadingProgressBar

        val imageSearch = binding.imageSearch
        val txtLoading = binding.loadingCharacters
        val rvCharacters = binding.rvCharacter

        progressBar.visibility = View.VISIBLE
        txtLoading.visibility = View.VISIBLE
        imageSearch.visibility = View.INVISIBLE
        rvCharacters.visibility = View.INVISIBLE

        characterAdapter = CharacterAdapter(charactersList,
            { character, _ ->
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailChracterFragment(character)
                findNavController().navigate(action)
            },
            { character ->
                val success = viewModel.addToFavorites(character)
                if (success) {
                    Toast.makeText(
                        requireContext(),
                        "Personaje agregado a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al agregar el personaje a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        binding.rvCharacter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isFetchingData && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    isFetchingData = true
                    viewModel.fetchAllCharacters()
                }
            }
        })

        binding.rvCharacter.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = characterAdapter
        }

        loadingTextHandler?.post(loadingTextRunnable!!)

        imageSearch.setOnClickListener {
            if (editTextSearch.visibility == View.VISIBLE) {
                AnimationUtils.slideViewUp(editTextSearch)
                editTextSearch.visibility = View.INVISIBLE
            } else {
                AnimationUtils.slideViewDown(editTextSearch)
                editTextSearch.visibility = View.VISIBLE
            }
        }

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("TextWatcher", "beforeTextChanged: $s")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCharacters(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("TextWatcher", "beforeTextChanged: $s")
            }
        })
    }

    private fun fetchData() {
        if (isNetworkAvailable()) {
            loadCharacters()
        } else {
            showNoInternetMessage()
        }
    }
    private fun loadCharacters() {
        viewModel.fetchAllCharacters()
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            characters?.let {
                val previousSize = charactersList.size
                charactersList = it
                characterAdapter.characters = it
                characterAdapter.notifyItemRangeInserted(previousSize, characters.size - previousSize)
            }

            progressBar.visibility = View.INVISIBLE
            binding.loadingCharacters.visibility = View.INVISIBLE
            binding.imageSearch.visibility = View.VISIBLE
            binding.rvCharacter.visibility = View.VISIBLE
            isFetchingData = false
        }
    }
    private fun showNoInternetMessage() {
        Toast.makeText(
            requireContext(),
            "No hay conexión a Internet. Intentando de nuevo...",
            Toast.LENGTH_SHORT
        ).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (isNetworkAvailable()) {
                loadCharacters()
            } else {
                showNoInternetMessage()
            }
        }, 5000)
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun filterCharacters(query: String) {
        viewModel.isFiltering = query.isNotEmpty()
        val filteredList = mutableListOf<MarvelCharacter>()
        for (character in charactersList) {
            if (character.name.contains(query, ignoreCase = true)) {
                filteredList.add(character)
            }
        }
        if (filteredList.isEmpty()) {
            binding.imageEmpty.visibility = View.VISIBLE
            binding.emptyPhotos.visibility = View.VISIBLE
        } else {
            binding.imageEmpty.visibility = View.INVISIBLE
            binding.emptyPhotos.visibility = View.INVISIBLE
        }
        characterAdapter.characters = filteredList
        characterAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        loadingTextHandler?.removeCallbacks(loadingTextRunnable!!)
        super.onDestroyView()
    }
}