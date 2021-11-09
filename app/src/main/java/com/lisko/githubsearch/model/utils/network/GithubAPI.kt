package com.lisko.githubsearch.model.utils.network

import com.lisko.githubsearch.model.entity.RepoData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {

    @GET(Constants.API_ENDPOINT)
    fun getRepos(
        @Path(Constants.USERNAME) username: String
    ): Single<RepoData.Response>

}