package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import java.lang.Exception

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValueField: TextView? = null
    private var maxValueField: TextView? = null

    private var listener: FirstListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FirstListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
//        val min =
        minValueField = view.findViewById(R.id.min_value)

        // TODO: val max = ...
//        val max =
        maxValueField = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            try {
                val max = maxValueField?.text.toString().toInt()
                val min = minValueField?.text.toString().toInt()
                if (max > min) {
                    listener?.tapGenerateButton(min, max)
                } else {
                    showError()
                }
            } catch (e: Exception) {
                showError()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener?.closeApp()
            }
        })
    }

    private fun showError() {
        Toast.makeText(activity, "Enter correct data for Min and Max", Toast.LENGTH_SHORT).show()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface FirstListener {
        fun tapGenerateButton(min: Int, max: Int)
        fun closeApp()
    }
}