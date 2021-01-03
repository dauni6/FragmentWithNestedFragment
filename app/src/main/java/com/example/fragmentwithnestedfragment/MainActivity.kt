package com.example.fragmentwithnestedfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.fragmentwithnestedfragment.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mFragmentManager: FragmentManager

    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mFragmentManager = supportFragmentManager
        initFirstFragment()
    }

    private fun initFirstFragment() {
        val parentFragment = mFragmentManager.findFragmentByTag(TAG_PARENT)
        if (parentFragment == null) {
            mFragmentManager.beginTransaction()
                .add(R.id.container, ParentFrgment.getInstance(), TAG_PARENT)
                .commit()
        }
    }

    override fun onBackPressed() {
        val parentFrgment = mFragmentManager.findFragmentByTag(TAG_PARENT)
        if (parentFrgment != null && parentFrgment.childFragmentManager.backStackEntryCount > 0) {
            parentFrgment.childFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")
    }

    companion object {
        private const val TAG_PARENT = "TAG_PARENT"
    }

}
