package com.shadi.repository

import android.os.Handler
import android.util.Log
import com.shadi.interfaces.GetHomeSyncListener
import com.shadi.local_db.dao.CandidateListDao
import com.shadi.local_db.entity.CandidateListResponse
import com.shadi.local_db.entity.ResultsItem
import com.shadi.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus


class RepositoryImpl(
    private val candidateListDao: CandidateListDao,
    private val networkDataSource: NetworkDataSource

) : Repository {
    init {
        networkDataSource.apply {
            downloadedCandidate.observeForever { newUpiAndRechargeData ->
                val data:CandidateListResponse=newUpiAndRechargeData
                Log.e("size",newUpiAndRechargeData.results?.size.toString()+"")
                for (candidateData in data.results!!) {
                    candidateData?.let { candidateListDao.insert(it) }

                }
                Handler().postDelayed({
                    Log.e("size",newUpiAndRechargeData.results?.size.toString()+"")
                    EventBus.getDefault().post(GetHomeSyncListener())},1000)
            }


        }
    }


    override suspend fun candidateList() {
        return withContext(Dispatchers.IO) {
            networkDataSource.candidateList()
        }
    }

    override fun getCandidateList(): List<ResultsItem> {
        return candidateListDao.getAllCategory()
    }

    override fun updateList(id:Int,status:Int) {
        candidateListDao.updatecandidateData(id,status)
    }


}
