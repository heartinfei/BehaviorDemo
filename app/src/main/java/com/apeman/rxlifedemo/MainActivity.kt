package com.apeman.rxlifedemo


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        startActivity(Intent(this, HomeActivity::class.java))
    }


    @SuppressWarnings("all")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tl)
        Intent(this,HomeActivity::class.java).apply {
            startActivity(this)
        }
    }
}
