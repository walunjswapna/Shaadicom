package com.shadi.activity


import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadi.R
import com.shadi.adapter.CategoryAdapter
import com.shadi.base.BaseActivity
import com.shadi.interfaces.GetHomeSyncListener
import com.shadi.interfaces.OnRecyclerItemClickListener
import com.shadi.local_db.entity.ResultsItem
import com.shadi.view_model.CandidateVMFactory
import com.shadi.view_model.CandidateViewModel
import kotlinx.android.synthetic.main.activity_candidate_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class CandidateListActivity() : BaseActivity() , KodeinAware,
    OnRecyclerItemClickListener<ResultsItem> {

    override val kodein by closestKodein()
    private val candidateVMFactory: CandidateVMFactory by instance()
    private var candidateList: List<ResultsItem>? = null
    private lateinit var candidateViewModel: CandidateViewModel
    var mAdapter: CategoryAdapter? = null

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)
        candidateViewModel =
            ViewModelProviders.of(this, candidateVMFactory).get(CandidateViewModel::class.java)

        setUpRecyclerView(rv_candidatelist, LinearLayoutManager(this))


        candidateList=candidateViewModel.displaycandidateList()
        if(candidateList!=null && !candidateList.isNullOrEmpty()) {
            initAdapter(candidateList)
        }

    }
    fun initAdapter(data: List<ResultsItem>?) {
        mAdapter = CategoryAdapter(applicationContext)
        mAdapter!!.setItems(data)
        mAdapter!!.setListener(this)
        rv_candidatelist.adapter = mAdapter
    }

    override fun onItemClickListener(item: ResultsItem, flag: Int) {
        candidateViewModel.updateList(item.ID,flag)
        item.isInterested=flag
        mAdapter?.notifyDataSetChanged()

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun GetHomeSyncListener(event: GetHomeSyncListener) {
        candidateList=candidateViewModel.displaycandidateList()
        if(candidateList!=null && !candidateList.isNullOrEmpty()) {
            initAdapter(candidateList)
        }
    }
}