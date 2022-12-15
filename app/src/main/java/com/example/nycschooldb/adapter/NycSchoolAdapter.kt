package com.example.nycschooldb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschooldb.databinding.NycSchoolItemBinding
import com.example.nycschooldb.model.remote.SchoolListResponse

class NycSchoolAdapter(private val school: List<SchoolListResponse>,
                       private val openSat: (String)-> Unit):
RecyclerView.Adapter<NycSchoolAdapter.NycSchoolViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= NycSchoolViewHolder (
        NycSchoolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    )


    override fun onBindViewHolder(holder: NycSchoolViewHolder, position: Int) {
        holder.onBinding(school[position])
    }

    override fun getItemCount(): Int {
        return school.size
    }

    inner class NycSchoolViewHolder(private val binding: NycSchoolItemBinding):
    RecyclerView.ViewHolder(binding.root){
        fun onBinding(nycSchoolListResponse: SchoolListResponse){
            binding.apply {
                tvSchoolName.text = nycSchoolListResponse.school_name
                tvSchoolCityAndZipCode.text = String.format(nycSchoolListResponse.city+ "' "+
                nycSchoolListResponse.zip)

                btnSatScores.setOnClickListener {
                    openSat(nycSchoolListResponse.dbn)
                }
            }
        }

    }
}