package com.lisko.githubsearch.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lisko.githubsearch.databinding.DetailsFragmentBinding
import com.lisko.githubsearch.model.entity.RepoData
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment()  {

    private lateinit var binding: DetailsFragmentBinding
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var details: RepoData.ResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        details= args.repoDetails

        binding.tvNameDetails.text= details.name
        binding.tvOpenIssues.text= details.open_issues_count.toString()
        binding.tvOwner.text= details.owner.login
        binding.tvSizeDetails.text= details.size.toString()
        binding.tvStars.text= details.stargazers_count.toString()
        binding.tvWatchers.text= details.watchers_count.toString()

        Picasso.get().load(details.owner.avatar_url)
            .into(binding.ivUser)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}