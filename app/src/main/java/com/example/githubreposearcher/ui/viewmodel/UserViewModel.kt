package com.example.githubreposearcher.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubreposearcher.model.GithubRepository
import com.example.githubreposearcher.model.User
import com.example.githubreposearcher.repository.UserRepository
import com.example.githubreposearcher.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _user : MutableLiveData<Resource<User>> = MutableLiveData()
    val user: LiveData<Resource<User>> = _user

    private val _repos : MutableLiveData<List<GithubRepository>> = MutableLiveData()
    val repos: LiveData<List<GithubRepository>> = _repos

    private val _totalForks : MutableLiveData<Int> = MutableLiveData<Int>()
    val totalForks: LiveData<Int> = _totalForks

    private fun getUserResponse(response: Response<User>): Resource<User>? {
        return if (response.isSuccessful) Resource.success(response.body())
        else Resource.error(data = null, "Error: ${response.errorBody()}")
    }

    fun getUser(userId: String) {
        viewModelScope.launch {
            _user.postValue(Resource.loading(null))
            try {
                val response = repository.getUser(userId)
                _user.postValue(getUserResponse(response))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _user.postValue(
                        Resource.error(
                            data = null,
                            message = "Error: Network Failure"
                        )
                    )

                    else -> _user.postValue(
                        Resource.error(
                            data = null,
                            message = t.message ?: "Error: "
                        )
                    )
                }
            }
        }
    }

    fun getRepos (userId: String) {
        viewModelScope.launch {
            _repos.value = repository.getUserRepos(userId)

            val allRepo = repository.getUserRepos(userId)
            // Calculate total forks
            val forks = allRepo.sumBy { it.forks }
            _totalForks.value = forks
            Log.d("UserViewModel", "Total Forks: ${_totalForks.value}")
        }
    }
}
