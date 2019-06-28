package com.apeman.rxlifedemo

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import io.github.heartinfei.slogger.S

/**
 *
 * @author Rango on 2019-06-28 wangqiang@smzdm.com
 */
class HeaderBehavior(ctx: Context, attrs: AttributeSet) : AppBarLayout.Behavior(ctx, attrs) {



    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        val res = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
        S.i("$res" + type.toString())
        return res
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }


}