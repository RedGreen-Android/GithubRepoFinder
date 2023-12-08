package com.example.githubreposearcher.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubreposearcher.R
import com.example.githubreposearcher.databinding.FragmentUserRepoScreenBinding
import com.example.githubreposearcher.model.GithubRepository
import com.example.githubreposearcher.ui.adapter.MyClickListener
import com.example.githubreposearcher.ui.adapter.UserRepoAdapter
import com.example.githubreposearcher.ui.viewmodel.UserViewModel
import com.example.githubreposearcher.utils.GlideApp
import com.example.githubreposearcher.utils.Status
import dagger.hilt.android.AndroidEntryPoint

//First screen
@AndroidEntryPoint
class UserRepoScreen : Fragment(R.layout.fragment_user_repo_screen), MyClickListener {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUserRepoScreenBinding
    lateinit var adapter: UserRepoAdapter
    val repositoryList: ArrayList<GithubRepository> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserRepoScreenBinding.bind(view)
        observeViewModel()
        setupUI()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveList(users: List<GithubRepository>) {
        repositoryList.addAll(users)
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }

    private fun observeViewModel() {
        binding.btnSearch.setOnClickListener {
            val userId = binding.etSearchInput.text?.toString()
            userId?.let {
                viewModel.getUser(it)
                viewModel.getRepos(it)
                viewModel.repos.observe(viewLifecycleOwner) { resources ->
                    retrieveList(resources)
                }
            }

            viewModel.user.observe(viewLifecycleOwner) { users ->
                users.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> resource.data?.let { user ->
                            binding.user = user
                            Log.d("UserRepoScreen", "Avatar URL: ${user.nodeId}")
//                            if (!user.avatarUrl.isNullOrBlank()) {
                            Glide.with(binding.root).load(user.avatarUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .dontAnimate()
                                .into(binding.ivUserIcon)
//                            }
                        }

                        Status.LOADING -> {//Implement a Progress bar}
                        }

                        Status.ERROR -> {

                            Toast.makeText(activity, resource.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = UserRepoAdapter(arrayListOf(), this)
        binding.recyclerView.adapter = adapter
    }

    override fun myItemClick(position: Int) {
        if (repositoryList.size > position) {
            val model = repositoryList[position]
            val bundle = Bundle()
            bundle.putParcelable("key", model)
            val navController = findNavController()
            navController.navigate(R.id.action_userRepoScreen2_to_userDetailFragment, bundle)
        }
    }
}