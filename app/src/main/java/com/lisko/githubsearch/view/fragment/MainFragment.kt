package com.lisko.githubsearch.view.fragment

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisko.githubsearch.R
import com.lisko.githubsearch.databinding.MainFragmentBinding
import com.lisko.githubsearch.model.adapter.RepoAdapter
import com.lisko.githubsearch.viewmodel.MainFragmentViewModel


class MainFragment : Fragment() {

    private val adapter= RepoAdapter(this@MainFragment)
    private lateinit var dialog: AlertDialog.Builder

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MainFragmentBinding.inflate(inflater, container, false)
        binding.svUsername.clearFocus()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        binding.svUsername.isSubmitButtonEnabled= true
        viewModelObservers()

        binding.svUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    if(query.length<3) {
                        setDialog(R.string.error_length)
                    }
                    else{
                        viewModel.setUsername(query)
                        viewModel.getDataFromAPI()
                    }

                }
                else{
                    setDialog(R.string.error_empty_username)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })



        }

    private fun viewModelObservers(){
        viewModel.githubResponse.observe(viewLifecycleOwner){
            binding.rvRepos.layoutManager= LinearLayoutManager(context,
                RecyclerView.VERTICAL, false)
            adapter.setRepos(it.sortedWith(compareBy({it.name}, {it.updated_at})) )
            binding.rvRepos.adapter= adapter


        }

        /*
        viewModel.githubLoadingError.observe(viewLifecycleOwner, {
                error -> error?.let {
            Log.e("github loading error", error.toString())
            if(error){
                    setDialog(R.string.error_loading)
                }

        }
        })
        */

        viewModel.loadGithub.observe(viewLifecycleOwner, {
                loading -> loading?.let {
            Log.i("github loading", "{$loading}")

        }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, {
            msg -> msg?.let {
                if(msg.contains("404")){
                    setDialog(R.string.error_username_doesnt_exist)
                    binding.rvRepos.adapter = RepoAdapter(this@MainFragment)
                    binding.svUsername.setQuery("", false);
                    binding.svUsername.clearFocus();
                }
                 else if(msg!=""){
                    setDialog(R.string.error_loading)
                 }
        }
        })

    }

    fun setDialog(string: Int){

        dialog= AlertDialog.Builder(context).setTitle(getString(
            R.string.error))
            .setMessage(getString(string))
            .setPositiveButton(
                "Ok"
            ) { _, _ ->
            }
        dialog.show()
    }

    }