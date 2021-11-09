package com.lisko.githubsearch.model.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lisko.githubsearch.databinding.AdapterRepoBinding
import com.lisko.githubsearch.model.entity.RepoData
import com.lisko.githubsearch.view.fragment.DetailsFragment
import com.lisko.githubsearch.view.fragment.MainFragmentDirections

class RepoAdapter(private val fragment: Fragment): RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private var mRepoList: List<RepoData.ResponseItem> = listOf()

    class ViewHolder (view: AdapterRepoBinding): RecyclerView.ViewHolder(view.root){
        val repoName= view.tvName
        val repoSize= view.tvSize
        val repoLanguage= view.tvLanguage
        val button= view.imageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterRepoBinding = AdapterRepoBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent, false)
        return RepoAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = mRepoList[position]
        holder.repoName.text = current.name
        holder.repoSize.text = current.size.toString()
        holder.repoLanguage.text= current.language
        holder.button.setOnClickListener {
            fragment.findNavController().navigate(MainFragmentDirections
                .actionMainFragmentToDetailsFragment(current))
        }


    }

    override fun getItemCount(): Int {
        return mRepoList.size
    }

    fun setRepos(list: List<RepoData.ResponseItem>){
        mRepoList= list
        for(item in mRepoList){
            Log.e("item", item.toString())
        }
        notifyDataSetChanged()
    }


}