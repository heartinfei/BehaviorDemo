package com.apeman.rxlifedemo

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 *
 * @author Rango on 2019-06-28 wangqiang@smzdm.com
 */
class MyBehavior(ctx: Context, attrs: AttributeSet) : AppBarLayout.ScrollingViewBehavior(ctx, attrs) {

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        return super.onTouchEvent(parent, child, ev)
    }
}