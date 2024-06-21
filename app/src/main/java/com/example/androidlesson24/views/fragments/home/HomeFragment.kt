package com.example.androidlesson24.views.fragments.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlesson24.R
import com.example.androidlesson24.databinding.FragmentHomeBinding
import com.example.androidlesson24.utilities.gone
import com.example.androidlesson24.utilities.visible
import com.example.androidlesson24.viewmodels.HomeViewModel
import com.example.androidlesson24.views.fragments.base.BaseFragment
import com.example.androidlesson24.views.adapters.CityAdapter
import com.example.androidlesson24.views.adapters.DistrictAdapter
import com.example.androidlesson24.views.adapters.MuseumAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val cityAdapter = CityAdapter()
    private val districtAdapter = DistrictAdapter()
    private val museumAdapter = MuseumAdapter()

    var citydata=""
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        viewModel.getAllCities()


        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recycleViewCity.layoutManager = linearLayoutManager
        binding.recycleViewCity.adapter = cityAdapter

        val linearLayoutManager2 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recycleViewRegion.layoutManager = linearLayoutManager2
        binding.recycleViewRegion.adapter = districtAdapter


        cityAdapter.onClickItem = { city ->



            citydata =city
            viewModel.getDistricts(city)



            with(binding.editText) {
                clearFocus()
                clearComposingText()
                text?.clear()
            }


        }

        districtAdapter.onClickItem = { district ->

            viewModel.getAllMuseumByCurrentData(citydata,district)




            with(binding.editText) {
                clearFocus()
                clearComposingText()
                text?.clear()
            }


        }

        museumAdapter.onClickItem= { mu ->

            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(mu)
            findNavController().navigate(action)

        }


        val spanCount = if (isTablet()) 2 else 1
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        binding.recycleViewMuseums.layoutManager = gridLayoutManager
        binding.recycleViewMuseums.adapter = museumAdapter


        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                searchJob?.cancel()
                searchJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(100)
                    viewModel.search(searchText)

                    if (searchText.isNotBlank()) {
                        updateSearchDrawable(true)
                    } else {
                        updateSearchDrawable(false)
                    }
                }
            }
        })



    }

    private fun observeData() {
        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            cityAdapter.updateList(cities)
        }

        viewModel.districts.observe(viewLifecycleOwner) { regions ->
            districtAdapter.updateList(regions)
        }

        viewModel.museums.observe(viewLifecycleOwner) { museums ->
            museumAdapter.updateList(museums)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBarContainer.progressBar2.visible()
            } else {
                binding.progressBarContainer.progressBar2.gone()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun isTablet(): Boolean {
        val configuration = resources.configuration
        return configuration.smallestScreenWidthDp >= 600
    }

    private fun updateSearchDrawable(isSearchActive: Boolean) {
        val drawableId = if (isSearchActive) R.drawable.search else R.drawable.search
        val tintColorId = if (isSearchActive) R.color.burntsienna else R.color.darkgray
        binding.editText.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        binding.editText.compoundDrawables[0].setTint(ContextCompat.getColor(requireContext(), tintColorId))
    }
}
