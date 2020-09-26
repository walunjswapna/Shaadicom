package com.shadi.local_db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shadi.local_db.dao.CandidateListDao
import com.shadi.local_db.entity.ResultsItem


@Database(
    entities = [ResultsItem::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun candidateListDao(): CandidateListDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "shadi.db"
            ).allowMainThreadQueries().build()
    }
}