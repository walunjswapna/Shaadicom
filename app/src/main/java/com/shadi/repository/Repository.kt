package com.shadi.repository

import com.shadi.local_db.entity.ResultsItem


interface Repository {

    suspend fun candidateList()

    fun getCandidateList(): List<ResultsItem>
    fun updateList(id:Int,status:Int)
}