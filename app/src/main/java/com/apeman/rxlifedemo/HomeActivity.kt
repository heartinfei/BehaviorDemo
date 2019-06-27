package com.apeman.rxlifedemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.github.heartinfei.slogger.Configuration
import io.github.heartinfei.slogger.S
import io.github.heartinfei.slogger.plan.DebugPlan
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_user_home.*
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {
    private val lifecycleSubject = BehaviorSubject.create<ReleaseEvent>()

    @SuppressWarnings("all")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
        setSupportActionBar(findViewById(R.id.toolbar))
        S.init(Configuration()).addPlans(DebugPlan())
        S.i(nsvUserInfo.measuredHeight.toString())
        nsvUserInfo.post {
            S.i("ScrollView 高度：" + nsvUserInfo.measuredHeight.toString())
            S.i("最大滚动距离：" + nsvUserInfo.maxScrollAmount)
            S.i("collapsingToolbar高度：" + collapsingToolbar.measuredHeight.toString())
            S.i("coordinator 高度：" + coordinator.measuredHeight)
            S.i("appBarLayout 高度：" + appBarLayout.measuredHeight)
            collapsingToolbar.layoutParams.height = coordinator.measuredHeight - tabLayout.measuredHeight
            appBarLayout.layoutParams.height = coordinator.measuredHeight
        }
    }

    private fun rxTest() {
        Log.i("wangqiang", "onCreate")
        Observable.interval(1, TimeUnit.SECONDS).map {
            throw RuntimeException("Error")
            it.toString()
        }.compose {
            applySchedulersAndLifecycle(it)
        }.doOnComplete {
            Log.i("wangqiang", "onComplete")
        }.doOnDispose {
            Log.i("wangqiang", "onDisposed")
        }.subscribe({
            Log.i("wangqiang", "onNext:$it")
        }) {
            Log.i("wangqiang", it.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("wangqiang", "onDestory")
        lifecycleSubject.onNext(ReleaseEvent.RELEASE)
    }

    fun <T> applySchedulersAndLifecycle(observable: Observable<T>): Observable<T> {
        return observable.takeUntil(lifecycleSubject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun takeUntilRelease(lifecycle: Observable<ReleaseEvent>): Observable<Boolean> {
        return lifecycle.take(1).map {
            it == ReleaseEvent.RELEASE
        }
    }

    enum class ReleaseEvent {
        RELEASE
    }
}
