package com.example.fragmentwithnestedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fragmentwithnestedfragment.databinding.FragChildBinding

class ChildFragment : Fragment() {
    private lateinit var binding: FragChildBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_child, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val no = arguments?.getInt(ARG_NO, 0)
        val text = "${no}번째 자식 프래그먼트"
        binding.tvResult.text = text
    }

    companion object {
      private const val ARG_NO = "ARG_NO"

        fun getInstance(no: Int): ChildFragment {
            val fragment = ChildFragment()
            val args = Bundle()
            args.putInt(ARG_NO, no)
            fragment.arguments = args
            return fragment
        }
    }

}
