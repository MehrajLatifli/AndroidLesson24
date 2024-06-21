package com.example.androidlesson24.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.androidlesson24.R
import com.example.androidlesson24.databinding.FragmentDetailBinding
import com.example.androidlesson24.views.fragments.base.BaseFragment


class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {


    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.nametextView.text = args.museum.name
        binding.nametextView2.text = args.museum.address
        binding.nametextView3.text = args.museum.description

    }






}