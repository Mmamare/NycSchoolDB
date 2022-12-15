package com.example.nycschooldb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nycschooldb.databinding.FragmentNycSatScoreBinding
import com.example.nycschooldb.model.remote.SchoolSatResponse
import com.example.nycschooldb.model.remote.UIState
import com.example.nycschooldb.viewmodel.NycSchoolViewModel

class NycSatScoreFragment: Fragment() {
    private var dbn = ""
    private var _binding: FragmentNycSatScoreBinding? = null
    private val binding: FragmentNycSatScoreBinding
    get() = _binding!!

    private val nycViewModel: NycSchoolViewModel by lazy {
        ViewModelProvider(requireActivity())[NycSchoolViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNycSatScoreBinding.inflate(layoutInflater)
        dbn = arguments?.getString(DBN).toString()
        nycViewModel.getSatScore(dbn)
        getObserver()
        return binding.root
    }

    private fun getObserver() {
        nycViewModel.satScore.observe(viewLifecycleOwner){state->
            when(state){
                is UIState.Loading->{
                    binding.apply {
                        tvSatLoadingText.visibility = View.VISIBLE
                    }
                    nycViewModel.getSatScore(dbn)
                }

                is UIState.Error->{
                    binding.apply {
                        tvSatLoadingText.text = state.errorMessage
                    }
                }
                is UIState.ResponseeSchoolSat->{
                    val satResponse = showSchoolByDBN(state.satData)
                    if (satResponse==null){
                        binding.apply {
                            tvSatLoadingText.text = "No sat info for school"
                        }
                    }else{
                        binding.apply {
                            tvSatLoadingText.visibility = View.GONE
                            tvSatSchoolName.apply {
                                text = satResponse.school_name
                                visibility = View.VISIBLE
                            }

                            tvAveragesTitle.visibility = View.VISIBLE
                            tvTakers.apply {
                                text = "Takers: ${satResponse.satTestTakers}"
                                visibility = View.VISIBLE
                            }

                            tvAvgReading.apply {
                                text = "Reading: ${satResponse.readingAvg}"
                                visibility = View.VISIBLE
                            }

                            tvAvgMath.apply {
                                text = "Math: ${satResponse.mathAvg}"
                                visibility = View.VISIBLE
                            }

                            tvAvgWriting.apply {
                                text = "Writing: ${satResponse.writingAvg}"
                                visibility = View.VISIBLE
                            }
                        }

                    }
                }
                else ->{ }
            }
        }
    }

    private fun showSchoolByDBN(satScores: List<SchoolSatResponse>): SchoolSatResponse?{
        return  satScores.singleOrNull { it.dbn == dbn }
    }

    companion object{
        private const val DBN = "dbn"
        fun newInstance(dbn: String): NycSatScoreFragment{
            val fragment= NycSatScoreFragment()
            val bundle = Bundle()
            bundle.putString(DBN, dbn)
            fragment.arguments = bundle
            return fragment
        }


    }
}