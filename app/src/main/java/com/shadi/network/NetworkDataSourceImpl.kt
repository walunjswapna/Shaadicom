package com.shadi.network

import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shadi.R
import com.shadi.local_db.entity.CandidateListResponse
import com.shadi.local_db.entity.ResultsItem
import com.shadi.service.ApiService
import com.shadi.service.NoConnectivityException
import retrofit2.HttpException


class NetworkDataSourceImpl(
    private val apiService: ApiService,
    val context: Context
): NetworkDataSource {


    private val _downloadedCandidateList = MutableLiveData<CandidateListResponse>()


    override val downloadedCandidate: LiveData<CandidateListResponse>
        get() = _downloadedCandidateList

    override suspend fun candidateList() {
        try {
            val fetchedMasterData = apiService.getCandidateList()
                .await()
            _downloadedCandidateList.postValue(fetchedMasterData)
        } catch (e: HttpException) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: NoConnectivityException) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_internate_connection),
                    Toast.LENGTH_SHORT
                ).show()


            }
        }

    }
}
