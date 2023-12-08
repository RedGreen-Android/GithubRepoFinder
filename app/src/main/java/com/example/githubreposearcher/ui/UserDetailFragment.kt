package com.example.githubreposearcher.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubreposearcher.R
import com.example.githubreposearcher.databinding.FragmentUserDetailBinding
import com.example.githubreposearcher.model.GithubRepository
import com.example.githubreposearcher.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

//Second screen when an item from the repository is selected
@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
        getRepoDetails()
    }

    private fun getRepoDetails(){
        val repo = arguments?.getParcelable<GithubRepository>("key")
        repo?.let {
            binding.repo = it
            Log.d("UserDetails", "Time updated: ${it.updatedAt}")
        }
    }

    private fun observeViewModel(){
        viewModel.user.observe(viewLifecycleOwner) {
            it.data?.name?.let { it1 -> viewModel.getRepos(it1) }
        }
        viewModel.totalForks.observe(viewLifecycleOwner) { totalForks ->
            // Update UI with totalForks
            Log.d("UserDetails", "Total number: $totalForks")
            binding.tvTotalFork.text = getString(R.string.total_fork_format, totalForks.toString())

        }
    }

}