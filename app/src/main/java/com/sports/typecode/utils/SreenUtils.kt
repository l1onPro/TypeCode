package com.sports.typecode.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.ceil

object ScreenUtils {
    private val displayMetrics: DisplayMetrics
        get() = Resources.getSystem().displayMetrics

    fun dp(dp: Int): Int = dp(dp.toFloat())
    fun dp(dp: Float): Int = ceil(dp * displayMetrics.density).toInt()

    fun getScreenHeight(): Int = displayMetrics.heightPixels
    fun getScreenWidth(): Int = displayMetrics.widthPixels
}