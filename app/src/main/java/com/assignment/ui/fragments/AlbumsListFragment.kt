package com.assignment.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.R
import com.assignment.common.GenericResponse
import com.assignment.common.Logger
import com.assignment.common.SortType
import com.assignment.common.Store
import com.assignment.data.enums.Status
import com.assignment.data.models.AlbumModel
import com.assignment.databinding.FragmentAlbumsListBinding
import com.assignment.ui.adapters.AlbumsListAdapter
import com.assignment.ui.interfaces.AlbumsListAdapterClickEvents
import com.assignment.viewutils.Message
import com.assignment.ui.viewModels.AlbumsListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumsListFragment : Fragment() {

    // Binding is used to replace the findViewById
    private var binding: FragmentAlbumsListBinding? = null

    // viewModel - to access local/remote repositories functionality
    private val viewModel: AlbumsListFragmentViewModel by viewModels()

    // list of albums
    private val listOfAlbums = ArrayList<AlbumModel>()

    // adapter for recyclerview
    private lateinit var albumsListAdapter: AlbumsListAdapter

    private var sortUpMenuItem: MenuItem? = null
    private var sortDownMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.info("Albums list - onCreate called")
        setHasOptionsMenu(true)

        // getting albums from remote repo
        viewModel.getAlbumsFromRemoteRepo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.info("Albums list - onCreateView called")
        binding = FragmentAlbumsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.info("Albums list - onViewCreated called")

        val navController = Navigation.findNavController(view)

        binding?.let { layout ->

            // creating adapter instance
            albumsListAdapter = AlbumsListAdapter(
                listOfAlbums,
                object : AlbumsListAdapterClickEvents {

                    // user has clicked on album title
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

            // setting click listener on cancel view
            layout.cancel.setOnClickListener {
                // remove text from search box
                layout.searchBoxEdt.setText("")
                // remove its focus
                layout.searchBoxEdt.clearFocus()
                // reset adapter
                albumsListAdapter.searchFilter("")
                // hide keyboard
                hideKeyboard()
            }

            // text change listener for search box
            layout.searchBoxEdt.addTextChangedListener(object : TextWatcher {
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

            // attaching layout manager and adapter with recyclerview
            layout.recView.let {
                it.layoutManager = LinearLayoutManager(requireActivity())
                it.adapter = albumsListAdapter
            }

            // getting albums from local repo
            viewModel.getAlbumsFromLocalRepo().observe(viewLifecycleOwner, { models ->
                listOfAlbums.clear()
                models?.let { storedModels ->
                    // if we found albums locally then hide progress bar
                    if (storedModels.isNotEmpty()) {
                        binding?.progressBar?.visibility = View.GONE
                    }
                    listOfAlbums.addAll(storedModels)
                }
                albumsListAdapter.notifyDataSetChanged()
            })

            // observing or listening to changes in api response
            viewModel.apiResponse().observe(viewLifecycleOwner, { response ->
                response?.let {
                    consumeResponse(it)
                }
            })

        }
    }

    /**
     * This method handles the API response
     * Check the status of API ie loading, error, success
     *
     * @param genericResponse - This is the original response object returned by the API call
     */
    private fun consumeResponse(genericResponse: GenericResponse) {
        when (genericResponse.status) {

            // api is in loading state
            Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE

            // api request completed
            Status.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
            }

            // something went wrong while calling api
            Status.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                Message.showToast(
                    requireActivity(),
                    Store.getErrorMessage(genericResponse.error, requireContext())
                )

            }
        }
    }

    /**
     * Hide keyboard from user interface
     */
    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view?.windowToken,
            0
        )
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

}