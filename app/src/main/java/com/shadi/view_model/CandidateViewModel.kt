package com.shadi.view_model


import androidx.lifecycle.ViewModel
import com.shadi.local_db.entity.ResultsItem
import com.shadi.repository.Repository


class CandidateViewModel(private val repository: Repository) : ViewModel() {
    fun displaycandidateList(): List<ResultsItem>{
        return repository.getCandidateList()
    }
    fun updateList(id:Int,status:Int){
        return repository.updateList(id,status)
    }

}