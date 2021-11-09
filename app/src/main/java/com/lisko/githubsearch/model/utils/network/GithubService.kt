package com.lisko.githubsearch.model.utils.network

import com.lisko.githubsearch.model.entity.RepoData
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GithubService {
    private val api= Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(GithubAPI::class.java)

    fun getRepos(username: String): Single<RepoData.Response> {
        return api.getRepos(
            username
        )
    }

}