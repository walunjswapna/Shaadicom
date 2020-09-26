package com.shadi

import android.app.Application
import android.os.Build
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import com.realmepay.payments.internal.lazyDeferred
import com.shadi.local_db.database.AppDatabase
import com.shadi.network.ConnectivityInterceptor
import com.shadi.network.ConnectivityInterceptorImpl
import com.shadi.network.NetworkDataSource
import com.shadi.network.NetworkDataSourceImpl
import com.shadi.repository.Repository
import com.shadi.repository.RepositoryImpl
import com.shadi.service.ApiService
import com.shadi.view_model.CandidateVMFactory
import com.shadi.view_model.CandidateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ShadiApplication : Application(), KodeinAware {

    private val repository: Repository by instance()


    override val kodein = Kodein.lazy {
        import(androidXModule(this@ShadiApplication))
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().candidateListDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<NetworkDataSource>() with singleton {
          NetworkDataSourceImpl(
                instance(),
                applicationContext
            )
        }

        bind() from singleton { ApiService(instance()) }

        bind<Repository>() with singleton {
            RepositoryImpl(
                instance(),
                instance()
            )
        }
        bind() from provider { CandidateVMFactory(instance()) }
        bind() from provider { CandidateViewModel(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        callApi()



    }
    fun callApi()
    {
        val masterSyncData by lazyDeferred {
            repository.candidateList()
        }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                masterSyncData.await()
            } catch (e: Exception) {

            }
        }
    }

}









