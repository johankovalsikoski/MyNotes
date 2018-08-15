package com.kovalsikoski.johan.mynotes

import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_base.*

class ActivityBase : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun setContentView(layoutResID: Int) {

        mDrawerLayout = View.inflate(this, R.layout.activity_base, null) as DrawerLayout
        val activityContainer = mDrawerLayout.findViewById<View>(R.id.activity_content) as FrameLayout

        layoutInflater.inflate(layoutResID, activityContainer, true)
        setContentView(mDrawerLayout)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        title = null
        toolbar_title.text = "Minhas Notas :)"
    }
}
