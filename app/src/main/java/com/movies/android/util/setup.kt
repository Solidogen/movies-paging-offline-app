package com.movies.android.util

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.*

fun <T : RecyclerView.Adapter<*>> RecyclerView.setup(
    adapter: T,
    @DrawableRes dividerResId: Int? = null,
    isHorizontal: Boolean = false,
    gridSpan: Int? = null,
    shouldDisableAnimations: Boolean = false
) {
    val orientation = if (isHorizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
    layoutManager = if (gridSpan == null) {
        LinearLayoutManager(context, orientation, false)
    } else {
        GridLayoutManager(context, gridSpan, orientation, false)
    }
    itemAnimator = if (shouldDisableAnimations) null else DefaultItemAnimator()
    if (adapter !is ConcatAdapter) {
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
    dividerResId?.let {
//        val decoration = DividerItemDecoration(context, orientation)
//        val dividerDrawable = context.getDrawableCompat(dividerResId)
//        dividerDrawable?.let { decoration.setDrawable(it) }
//        addItemDecoration(decoration)
    }
    this.adapter = adapter
}