package com.example.githubreposearcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubreposearcher.databinding.UserRepoItemBinding
import com.example.githubreposearcher.model.GithubRepository

class UserRepoAdapter(private val repositories: ArrayList<GithubRepository>,  val myCallBack: MyClickListener) :
    RecyclerView.Adapter<UserRepoAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserRepoItemBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(binding, myCallBack)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    class UserViewHolder(private val binding: UserRepoItemBinding, val mycallback: MyClickListener)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(repositories: GithubRepository) {
            binding.repo = repositories
            binding.carditem.setOnClickListener {
                mycallback.myItemClick(adapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = repositories.size

    fun addUsers(repositories: List<GithubRepository>) {
        this.repositories.apply {
            clear()
            addAll(repositories)
        }
    }
}

interface MyClickListener{
    fun myItemClick(position: Int)

}