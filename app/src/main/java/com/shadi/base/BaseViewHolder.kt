package com.shadi.base
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shadi.base.BaseRecyclerListener

abstract class BaseViewHolder <T,L : BaseRecyclerListener>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: T, listener: L?)
}