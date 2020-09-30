package com.hkay.mynotesapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_second.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "position"

class SecondFragment : Fragment() {
    private var param1: String? = null
    private var param2: Boolean? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getBoolean(ARG_PARAM2)
            position = it.getInt(ARG_PARAM3)
        }
        param1?.let { Log.i("param1 = ", it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setImageResource(R.drawable.ic_done_24)
        editText.setText(param1)
        fab?.setOnClickListener{
            param2?.let { it1 -> saveContent(editText.text.toString(), it1) }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                if (editText.text.toString().isNotEmpty()) param2?.let { it1 -> saveContent(editText.text.toString(), it1) }
                else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    private fun saveContent(textContent: String, existingText: Boolean) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.content_frame, FirstFragment.newInstance(textContent, existingText, position))
            ?.commit()
        Toast.makeText(activity, "Content saved", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        if (editText.text.toString().isNotEmpty()) param2?.let { it1 -> saveContent(editText.text.toString(), !it1) }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: Boolean, position: Int?) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putBoolean(ARG_PARAM2, param2)
                    position?.let { putInt(ARG_PARAM3, it) }
                }
            }
    }
}