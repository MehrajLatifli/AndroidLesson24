package com.example.turkishmuseums.views.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.turkishmuseums.databinding.FragmentDetailBinding
import com.example.turkishmuseums.views.fragments.base.BaseFragment


class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {


    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.nametextView.text = args.museum.name
        binding.nametextView2.text = args.museum.address
        binding.nametextView3.text = args.museum.description

    }






}