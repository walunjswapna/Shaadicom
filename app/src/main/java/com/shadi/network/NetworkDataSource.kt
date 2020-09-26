package com.shadi.network

import androidx.lifecycle.LiveData
import com.shadi.local_db.entity.CandidateListResponse


interface NetworkDataSource {


    val downloadedCandidate: LiveData<CandidateListResponse>




    suspend fun candidateList()


}