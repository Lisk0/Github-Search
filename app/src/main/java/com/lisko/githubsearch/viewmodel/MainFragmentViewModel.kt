package com.lisko.githubsearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lisko.githubsearch.model.entity.RepoData
import com.lisko.githubsearch.model.utils.network.GithubService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainFragmentViewModel: ViewModel() {

    private val githubAPIService = GithubService()
    private val compositeDisposable= CompositeDisposable()
    private var username=""

    val loadGithub = MutableLiveData<Boolean>()
    val githubResponse= MutableLiveData<RepoData.Response>()
    val githubLoadingError= MutableLiveData<Boolean>()
    val errorMsg= MutableLiveData<String>()


    fun getDataFromAPI(){
        if(username!=""){
        loadGithub.value = true

            compositeDisposable.add(
                githubAPIService.getRepos(username)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<RepoData.Response>() {
                        override fun onSuccess(value: RepoData.Response) {
                            loadGithub.value = false
                            githubLoadingError.value = false
                            githubResponse.value = value
                            errorMsg.value= ""

                        }

                        override fun onError(e: Throwable) {
                            loadGithub.value = false
                            githubLoadingError.value = true
                            errorMsg.value= e.message!!
                            Log.e("greska", e.message!!)
                        }
                    })
            )
    }
    }

    fun setUsername(newUsername: String){
        username= newUsername
    }

    fun getUsername(): String{
        return username
    }

    fun showData(){

    }

}