package com.example.githubreposearcher.repository

import com.example.githubreposearcher.model.GithubRepository
import com.example.githubreposearcher.model.User
import com.example.githubreposearcher.network.GithubAPI
import retrofit2.Response
import javax.inject.Inject

//Repository layer for the two endpoints to send to the viewodel
class UserRepository @Inject constructor(private val githubAPI: GithubAPI) {

    suspend fun getUser(userId: String): Response<User> {
        return githubAPI.getUser(userId)
    }

    suspend fun getUserRepos(userId: String): List<GithubRepository> {
        return githubAPI.getUserRepos(userId)
    }
}