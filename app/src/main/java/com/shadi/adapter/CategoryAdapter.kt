package com.shadi.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.shadi.R
import com.shadi.base.BaseRecyclerAdapter
import com.shadi.base.BaseViewHolder
import com.shadi.interfaces.OnRecyclerItemClickListener
import com.shadi.local_db.entity.ResultsItem

class CategoryAdapter(context: Context) :
    BaseRecyclerAdapter<ResultsItem, OnRecyclerItemClickListener<ResultsItem>, CategoryAdapter.CategoryVH>(
        context
    ) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoryVH {
        return CategoryVH(inflate(R.layout.item_candidatelist, viewGroup))
    }

    class CategoryVH(itemView: View) :
        BaseViewHolder<ResultsItem, OnRecyclerItemClickListener<ResultsItem>>(itemView) {

        var tvCategoryName: TextView
        var tv_mobile: TextView
        var iv_image: ImageView
        var ll_button: LinearLayout
        var text_accept: TextView
        var text_decline: TextView
        var tv_status: TextView

        init {
            tvCategoryName = itemView.findViewById(R.id.tv_name)
            tv_mobile = itemView.findViewById(R.id.tv_mobile)
            iv_image = itemView.findViewById(R.id.iv_image)
            text_accept = itemView.findViewById(R.id.text_accept)
            text_decline = itemView.findViewById(R.id.text_decline)
            tv_status = itemView.findViewById(R.id.tv_status)
            ll_button = itemView.findViewById(R.id.ll_button)
        }

        override fun onBind(item: ResultsItem, listener: OnRecyclerItemClickListener<ResultsItem>?) {
            tvCategoryName.text =item.name?.title+" " +item.name?.first+" "+item.name?.last
            tv_mobile.text=item.phone
            Glide.with(itemView)
                .load(item.picture?.thumbnail) // image url
                .placeholder(R.drawable.ic_launcher_foreground) // any placeholder to load at start
                .error(R.drawable.ic_launcher_foreground)
            .into(iv_image);

            if(item.isInterested==0)
            {
                ll_button.visibility=View.VISIBLE
                tv_status.visibility=View.GONE
            }else{
                ll_button.visibility=View.GONE
                tv_status.visibility=View.VISIBLE
                if(item.isInterested==1)
                {
                    tv_status.text="Status Acccepted"
                }else{
                    tv_status.text="Status Declined"
                }
            }



            text_decline.setOnClickListener {
                listener?.onItemClickListener(item, 2)
                ll_button.visibility=View.GONE
                tv_status.visibility=View.VISIBLE
                tv_status.text="Status Declined"

            }
            text_accept.setOnClickListener {
                listener?.onItemClickListener(item, 1)
                ll_button.visibility=View.GONE
                tv_status.visibility=View.VISIBLE
                tv_status.text="Status Accepted"
            }
        }

    }

}