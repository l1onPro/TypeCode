package com.sports.typecode.ui.photos.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sports.typecode.utils.ScreenUtils

class SizeItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        view.layoutParams.width = ScreenUtils.getScreenWidth() - ScreenUtils.dp(32)
    }
}