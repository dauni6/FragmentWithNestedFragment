package com.example.fragmentwithnestedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fragmentwithnestedfragment.databinding.FragParentBinding
import timber.log.Timber

class ParentFrgment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragParentBinding
    private lateinit var mChildFragmentManager: FragmentManager

    private var mNumber = 0

    private val mListener = FragmentManager.OnBackStackChangedListener {
        var count = 0
        mChildFragmentManager.fragments.forEach { fragment ->
            fragment?.let {
                count++
            }
        }
        mNumber = count
        Timber.d("onBackStackChanged mNumber = $mNumber")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_parent, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated()")
        mChildFragmentManager = childFragmentManager

        initListeners()
        initFragment(savedInstanceState)

    }

    private fun initListeners() {
        binding.btnAdd.setOnClickListener(this)
        binding.btnRemove.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnAdd -> {
                clickAdd()
            }
            binding.btnRemove -> {
                clickRemove()
            }
        }
    }

    private fun clickAdd() {
        mChildFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ChildFragment.getInstance(mNumber))
            .addToBackStack(null)
            .commit()
    }

    private fun clickRemove() {
        if (mNumber == 0)
            return
        mChildFragmentManager.popBackStack()
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            mNumber = it.getInt(KEY_NUMBER, 0)
        } ?: kotlin.run {
            mChildFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ChildFragment.getInstance(mNumber), TAG_CHILD)
                .addToBackStack(null)
                .commit()
        }

        val fragment = mChildFragmentManager.findFragmentByTag(TAG_CHILD)
        Timber.d("onViewCreated() childFragment = $fragment, mNumber = $mNumber")

        mChildFragmentManager.addOnBackStackChangedListener(mListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState() ")
        outState.putInt(KEY_NUMBER, mNumber)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")
    }

    companion object {
        private const val TAG_CHILD = "TAG_CHILD"
        private const val KEY_NUMBER = "KEY_NUMBER"

        fun getInstance(): ParentFrgment {
            return ParentFrgment()
        }
    }



}
