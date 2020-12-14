package com.assignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.R
import com.assignment.common.Constants
import com.assignment.common.WebViewHandler
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.databinding.FragmentAlbumDetailBinding
import com.assignment.ui.adapters.AlbumPhotosAdapter
import com.assignment.ui.adapters.UserCommentsAdapter
import com.assignment.ui.viewModels.AlbumDetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailFragment : BaseFragment() {

    private var binding: FragmentAlbumDetailBinding? = null

    private val viewModel: AlbumDetailFragmentViewModel by viewModels()

    private var albumId = 0
    private var userId = 0
    private var userWebSiteLink = ""

    private val listOfAlbumPhotos = ArrayList<AlbumPhotosModel>()
    private lateinit var albumPhotosAdapter: AlbumPhotosAdapter
    private val listOfPosts = ArrayList<PostModel>()
    private lateinit var userCommentsAdapter: UserCommentsAdapter
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
            extractBundleValues()
            handleVisitWebsiteButton(layout, navController)
            initAlbumPhotos(layout)
            initUserCommentsSection(layout)
            handlePhotosSection(layout)
            handleCommentsSection(layout)

            //First check if the data exists locally
            //if available fetch that first and display
            fetchUserLocalRepoIfDataAvailable(layout)
            fetchPostIdFromLocalRepoIfAvaialble(layout)
            fetchAlbumPhotosFromLocalRepoIfAvailable()
            fetchPostsByUserIDFromLocalRepo()

            //Still get the updated data from the backend
            viewModel.getPostsByUserIdFromRemoteRepo(userId)
            viewModel.getPostByIdFromRemoteRepo(albumId)
            viewModel.getUserFromRemoteRepo(userId)
            viewModel.getAlbumPhotosFromRemoteRepo(albumId)

            // observing or listening to changes in api response
            viewModel.apiResponse().observe(viewLifecycleOwner, { response ->
                response?.let {
                    consumeResponse(it, binding?.detailsView)
                }
            })

        }
    }


    private fun fetchPostsByUserIDFromLocalRepo() {
        viewModel.getPostsByUserIdFromLocalDatabase(userId)
            .observe(viewLifecycleOwner, { models ->
                listOfPosts.clear()
                models?.let {
                    listOfPosts.addAll(it)
                }
                userCommentsAdapter.notifyDataSetChanged()
            })
    }

    private fun fetchAlbumPhotosFromLocalRepoIfAvailable() {
        viewModel.getAlbumPhotosFromLocalRepo(albumId).observe(viewLifecycleOwner, { models ->
            listOfAlbumPhotos.clear()
            models?.let {
                // if we found album's photos locally then hide progress bar and show details view
                if (it.isNotEmpty()) {
                    showLoading(false)
                    // binding?.progressBar?.visibility = View.GONE
                    binding?.detailsView?.visibility = View.VISIBLE
                }
                listOfAlbumPhotos.addAll(it)
            }
            albumPhotosAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchPostIdFromLocalRepoIfAvaialble(layout: FragmentAlbumDetailBinding) {
        viewModel.getPostByIdFromLocalDatabase(albumId).observe(viewLifecycleOwner, { user ->
            user?.let {
                // if we found user details locally then hide progress bar and show details view
                showLoading(false)
                // binding?.progressBar?.visibility = View.GONE
                binding?.detailsView?.visibility = View.VISIBLE
                layout.postModel = it
            }
        })
    }

    private fun fetchUserLocalRepoIfDataAvailable(layout: FragmentAlbumDetailBinding) {
        viewModel.getUserFromLocalRepo(userId).observe(viewLifecycleOwner, { user ->
            user?.let {
                // if we found user details locally then hide progress bar and show details view
                showLoading(false)
                binding?.detailsView?.visibility = View.VISIBLE
                userWebSiteLink = it.website
                layout.userModel = it
            }
        })
    }

    private fun handleCommentsSection(layout: FragmentAlbumDetailBinding) {
        layout.commentsManager.setOnClickListener {
            val visibility: Int
            val text: String
            if (layout.commentsRecView.visibility == View.VISIBLE) {
                visibility = View.GONE
                text = getString(R.string.show_comments)
            } else {
                visibility = View.VISIBLE
                text = getString(R.string.hide_comments)
            }

            layout.commentsRecView.visibility = visibility
            layout.commentsManager.text = text
        }
    }

    private fun handlePhotosSection(layout: FragmentAlbumDetailBinding) {
        layout.albumPhotosManager.setOnClickListener {

            val visibility: Int
            val displayText: String
            if (layout.albumPhotosRecView.visibility == View.VISIBLE) {
                visibility = View.GONE
                displayText = getString(R.string.show_album_photos)
            } else {
                visibility = View.VISIBLE
                displayText = getString(R.string.hide_album_photos)
            }

            layout.albumPhotosRecView.visibility = visibility
            layout.albumPhotosManager.text = displayText
        }
    }

    private fun initUserCommentsSection(layout: FragmentAlbumDetailBinding) {
        userCommentsAdapter = UserCommentsAdapter(listOfPosts)

        layout.commentsRecView.let {
            it.layoutManager = LinearLayoutManager(requireActivity())
            it.adapter = userCommentsAdapter
        }
    }

    private fun initAlbumPhotos(layout: FragmentAlbumDetailBinding) {
        albumPhotosAdapter = AlbumPhotosAdapter(listOfAlbumPhotos, imagesCountInSingleRow)

        // attaching layout manager and adapter with recyclerview
        layout.albumPhotosRecView.let {
            it.layoutManager = GridLayoutManager(
                requireActivity(), imagesCountInSingleRow, GridLayoutManager.VERTICAL, false
            )
            it.adapter = albumPhotosAdapter
        }
    }

    private fun extractBundleValues() {
        arguments?.let {
            val args = AlbumDetailFragmentArgs.fromBundle(it)
            albumId = args.albumId
            userId = args.userId
        }
    }

    private fun handleVisitWebsiteButton(
        layout: FragmentAlbumDetailBinding,
        navController: NavController
    ) {
        layout.visitWebsite.setOnClickListener {
            if (Constants.SHOULD_OPEN_URL_IN_BROWSER) {
                WebViewHandler.openInBrowser(requireContext(), userWebSiteLink)
            } else {
                val action = AlbumDetailFragmentDirections
                    .actionAlbumDetailFragmentToVisitWebsiteFragment(
                        userWebSiteLink
                    )
                // navigating user to Visit Website Fragment
                navController.navigate(action)
            }
        }
    }
}