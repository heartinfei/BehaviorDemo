package com.apeman.rxlifedemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {

    }

    private val lifecycleSubject = BehaviorSubject.create<ReleaseEvent>()

    @SuppressWarnings("all")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        }){
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
