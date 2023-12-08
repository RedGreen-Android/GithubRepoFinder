package com.example.githubreposearcher.network

import com.example.githubreposearcher.model.GithubRepository
import com.example.githubreposearcher.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<User>

    @GET("users/{userId}/repos")
    suspend fun getUserRepos(@Path("userId") userId: String): List<GithubRepository>
}
