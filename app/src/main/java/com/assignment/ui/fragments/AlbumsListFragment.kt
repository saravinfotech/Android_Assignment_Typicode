package com.assignment.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.R
import com.assignment.common.SortType
import com.assignment.data.models.AlbumModel
import com.assignment.databinding.FragmentAlbumsListBinding
import com.assignment.ui.adapters.AlbumsListAdapter
import com.assignment.ui.callbacks.AlbumsListAdapterClickEvents
import com.assignment.ui.viewModels.AlbumsListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumsListFragment : BaseFragment() {

    private var binding: FragmentAlbumsListBinding? = null
    private val viewModel: AlbumsListFragmentViewModel by viewModels()
    private val listOfAlbums = ArrayList<AlbumModel>()
    private lateinit var albumsListAdapter: AlbumsListAdapter
    private var sortUpMenuItem: MenuItem? = null
    private var sortDownMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getAlbumsFromRemoteRepo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAlbumsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        val navController = Navigation.findNavController(view)

        binding?.let { layout ->
            initAlbumListAdapter(navController, layout)
            handleSearchViewClickAction(layout)
            handleSearchViewTextChange(layout)
            fetchAlbumsFromLocalRepo()
            viewModel.apiResponse().observe(viewLifecycleOwner, { response ->
                response?.let {
                    consumeResponse(it, null)
                }
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.albums_list_menu, menu)
        sortUpMenuItem = menu.findItem(R.id.sort_up)
        sortDownMenuItem = menu.findItem(R.id.sort_down)
        sortDownMenuItem?.isVisible = true
        sortUpMenuItem?.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_down -> {
                sort(SortType.DOWN)
            }
            R.id.sort_up -> {
                sort(SortType.UP)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sort(sortType: SortType) {
        viewLifecycleOwner.lifecycleScope.launch {
            if (sortType == SortType.UP) {
                sortDownMenuItem?.isVisible = true
                sortUpMenuItem?.isVisible = false
                listOfAlbums.sortBy { it.title }
            } else {
                sortDownMenuItem?.isVisible = false
                sortUpMenuItem?.isVisible = true
                listOfAlbums.sortByDescending { it.title }
            }
            albumsListAdapter.notifyDataSetChanged()
        }
    }

    private fun fetchAlbumsFromLocalRepo() {
        viewModel.getAlbumsFromLocalRepo().observe(viewLifecycleOwner, { models ->
            listOfAlbums.clear()
            models?.let { storedModels ->
                // if we found albums locally then hide progress bar
                if (storedModels.isNotEmpty()) {
                    showLoading(false)
                    //binding?.progressBar?.visibility = View.GONE
                }
                listOfAlbums.addAll(storedModels)
            }
            albumsListAdapter.notifyDataSetChanged()
        })
    }

    private fun handleSearchViewTextChange(layout: FragmentAlbumsListBinding) {
        layout.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // text is changed, apply filter
                albumsListAdapter.searchFilter(s)
            }
        })
    }

    private fun handleSearchViewClickAction(layout: FragmentAlbumsListBinding) {
        layout.cancel.setOnClickListener {
            // remove text from search box
            layout.searchView.setText("")
            // remove its focus
            layout.searchView.clearFocus()
            // reset adapter
            albumsListAdapter.searchFilter("")
            // hide keyboard
            hideKeyboard()
        }
    }

    private fun initAlbumListAdapter(
        navController: NavController,
        layout: FragmentAlbumsListBinding
    ) {
        albumsListAdapter = AlbumsListAdapter(
            listOfAlbums,
            object : AlbumsListAdapterClickEvents {
                override fun onClick(model: AlbumModel) {
                    val action = AlbumsListFragmentDirections
                        .actionAlbumsListFragmentToAlbumDetailFragment(
                            model.id,
                            model.userId,
                            model.title
                        )

                    // navigating user to Detail Fragment
                    navController.navigate(action)
                }
            })

        layout.albumListRecyclerView.let {
            it.layoutManager = LinearLayoutManager(requireActivity())
            it.adapter = albumsListAdapter
        }
    }

}