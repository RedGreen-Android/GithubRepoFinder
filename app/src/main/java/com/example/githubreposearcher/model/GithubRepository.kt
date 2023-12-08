package com.example.githubreposearcher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepository(
    val name: String?,
    val description: String?,
    val updatedAt: String? = "null",
    val stargazersCount: Int,
    val forks: Int
) : Parcelable

