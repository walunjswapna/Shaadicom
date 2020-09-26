package com.shadi.interfaces

import com.shadi.base.BaseRecyclerListener


interface OnRecyclerItemClickListener<T>: BaseRecyclerListener {
    fun onItemClickListener(item:T, flag:Int)
}