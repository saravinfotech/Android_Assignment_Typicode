package com.assignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.R
import com.assignment.common.Constants
import com.assignment.common.GenericResponse
import com.assignment.common.Store
import com.assignment.data.enums.Status
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.databinding.FragmentAlbumDetailBinding
import com.assignment.ui.adapters.AlbumPhotosAdapter
import com.assignment.ui.adapters.UserCommentsAdapter
import com.assignment.viewutils.Message.showToast
import com.assignment.ui.viewModels.AlbumDetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

    // Binding is used to replace the findViewById
    private var binding: FragmentAlbumDetailBinding? = null

    // viewModel - to access local/remote repositories functionality
    private val viewModel: AlbumDetailFragmentViewModel by viewModels()

    private var albumId = 0
    private var userId = 0
    private var userWebSiteLink = ""

    // list of albums
    private val listOfAlbumPhotos = ArrayList<AlbumPhotosModel>()

    // adapter
    private lateinit var albumPhotosAdapter: AlbumPhotosAdapter

    // list of posts
    private val listOfPosts = ArrayList<PostModel>()

    // comment adapter
    private lateinit var userCommentsAdapter: UserCommentsAdapter

    // number of images to show in 1 row
    private val imagesCountInSingleRow = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding?.let { layout ->

            /**
             * Take the data from arguments
             */
            arguments?.let {
                val args = AlbumDetailFragmentArgs.fromBundle(it)
                albumId = args.albumId
                userId = args.userId
            }

            // setting click listener for visit website view
            layout.visitWebsite.setOnClickListener {
                if(Constants.SHOULD_OPEN_URL_IN_BROWSER){
                    Store.openURL(requireContext(),userWebSiteLink)
                }else{
                    val action = AlbumDetailFragmentDirections
                        .actionAlbumDetailFragmentToVisitWebsiteFragment(
                            userWebSiteLink
                        )
                    // navigating user to Visit Website Fragment
                    navController.navigate(action)
                }
            }

            // creating adapter
            albumPhotosAdapter = AlbumPhotosAdapter(listOfAlbumPhotos, imagesCountInSingleRow)

            // attaching layout manager and adapter with recyclerview
            layout.albumPhotosRecView.let {
                it.layoutManager = GridLayoutManager(
                    requireActivity(), imagesCountInSingleRow, GridLayoutManager.VERTICAL, false
                )
                it.adapter = albumPhotosAdapter
            }

            // creating adapter
            userCommentsAdapter = UserCommentsAdapter(listOfPosts)

            // attaching layout manager and adapter with recyclerview
            layout.commentsRecView.let {
                it.layoutManager = LinearLayoutManager(requireActivity())
                it.adapter = userCommentsAdapter
            }

            layout.albumPhotosManager.setOnClickListener {
                if (layout.albumPhotosRecView.visibility == View.VISIBLE) {
                    layout.albumPhotosRecView.visibility = View.GONE
                    layout.albumPhotosManager.text = getString(R.string.show_album_photos)
                } else {
                    layout.albumPhotosRecView.visibility = View.VISIBLE
                    layout.albumPhotosManager.text = getString(R.string.hide_album_photos)
                }
            }

            layout.commentsManager.setOnClickListener {
                if (layout.commentsRecView.visibility == View.VISIBLE) {
                    layout.commentsRecView.visibility = View.GONE
                    layout.commentsManager.text = getString(R.string.show_comments)
                } else {
                    layout.commentsRecView.visibility = View.VISIBLE
                    layout.commentsManager.text = getString(R.string.hide_comments)
                }
            }

            // loading user details from local repo
            viewModel.getUserFromLocalRepo(userId).observe(viewLifecycleOwner, { user ->
                user?.let {
                    // if we found user details locally then hide progress bar and show details view
                    binding?.progressBar?.visibility = View.GONE
                    binding?.detailsView?.visibility = View.VISIBLE
                    userWebSiteLink = it.website
                    layout.userModel = it
                }
            })

            // loading user details from local repo
            viewModel.getPostByIdFromLocalDatabase(albumId).observe(viewLifecycleOwner, { user ->
                user?.let {
                    // if we found user details locally then hide progress bar and show details view
                    binding?.progressBar?.visibility = View.GONE
                    binding?.detailsView?.visibility = View.VISIBLE
                    layout.postModel = it
                }
            })

            // loading photos from local repo
            viewModel.getAlbumPhotosFromLocalRepo(albumId).observe(viewLifecycleOwner, { models ->
                listOfAlbumPhotos.clear()
                models?.let {
                    // if we found album's photos locally then hide progress bar and show details view
                    if (it.isNotEmpty()) {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.detailsView?.visibility = View.VISIBLE
                    }
                    listOfAlbumPhotos.addAll(it)
                }
                albumPhotosAdapter.notifyDataSetChanged()
            })

            // loading post models from local repo for user id
            viewModel.getPostsByUserIdFromLocalDatabase(userId)
                .observe(viewLifecycleOwner, { models ->
                    listOfPosts.clear()
                    models?.let {
                        listOfPosts.addAll(it)
                    }
                    userCommentsAdapter.notifyDataSetChanged()
                })

            // load post models from remote repo
            viewModel.getPostsByUserIdFromRemoteRepo(userId)

            // load post model from remote repo
            viewModel.getPostByIdFromRemoteRepo(albumId)

            // load user data from remote repo
            viewModel.getUserFromRemoteRepo(userId)

            // load photos from remote repo
            viewModel.getAlbumPhotosFromRemoteRepo(albumId)

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
            Status.LOADING -> {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.detailsView?.visibility = View.GONE
            }

            // api request completed
            Status.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.detailsView?.visibility = View.VISIBLE
            }

            // something went wrong while calling api
            Status.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.detailsView?.visibility = View.VISIBLE
                showToast(
                    requireActivity(),
                    Store.getErrorMessage(genericResponse.error, requireContext())
                )
            }
        }
    }
}