package com.wizion.bookmyshow.interfaces

import com.wizion.bookmyshow.data.local.entity.Movie

public interface ItemClickListener {
    fun onItemClick(item : Movie)
    fun onItemClick(position: Int)
}