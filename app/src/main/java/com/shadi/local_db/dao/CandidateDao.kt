package com.shadi.local_db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shadi.local_db.entity.ResultsItem


@Dao
interface CandidateListDao {

    @Query("SELECT * FROM candidate")
    fun getAllCategory(): List<ResultsItem>


    @Insert()
    fun insert(bannerEntityList: ResultsItem)

    @Query("UPDATE candidate SET isInterested=(:isInterested) WHERE id = :id")
    fun updatecandidateData(
        id: Int,
        isInterested: Int

    )
    @Update
    fun updateTodo(vararg candidate: ResultsItem)
}