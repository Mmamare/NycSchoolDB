package com.example.nycschooldb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschooldb.R
import com.example.nycschooldb.adapter.NycSchoolAdapter
import com.example.nycschooldb.databinding.FragmentNycSchoolBinding
import com.example.nycschooldb.model.local.NYCSDatabase.Companion.newInstance
import com.example.nycschooldb.model.remote.UIState
import com.example.nycschooldb.viewmodel.NycSchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NycSchoolFragment: Fragment() {
    private var _binding: FragmentNycSchoolBinding? = null
    private val binding: FragmentNycSchoolBinding
    get() = _binding!!

    private val nycViewModel: NycSchoolViewModel by lazy {
        ViewModelProvider(requireActivity())[NycSchoolViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNycSchoolBinding.inflate(layoutInflater)
        getObserver()
        return binding.root
    }

    private fun getObserver() {
        nycViewModel.schoolInfo.observe(viewLifecycleOwner){state->
            when(state){
                is UIState.Loading ->{
                    binding.apply {
                        tvLoadingText.visibility = View.VISIBLE
                        rvSchools.visibility = View.GONE
                    }
                }
                is UIState.Error->{
                    binding.apply {
                        tvLoadingText.text = state.errorMessage
                    }
                }
                is UIState.ResponseListSchool->{
                    binding.apply {
                        tvLoadingText.visibility = View.GONE
                        rvSchools.visibility = View.VISIBLE
                        rvSchools.apply {
                            adapter = NycSchoolAdapter(state.schoolData, ::openSat)
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                            false)
                        }
                    }
                }
                else -> {}
            }

        }
    }

    private fun openSat(schoolName: String){
        nycViewModel.setScoreLoading()
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, NycSatScoreFragment.newInstance(schoolName))
    }

}