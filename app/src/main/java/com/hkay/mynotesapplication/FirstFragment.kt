package com.hkay.mynotesapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hkay.mynotesapplication.room.NotesEntity
import kotlinx.android.synthetic.main.fragment_first.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "position"


class FirstFragment : Fragment(), CustomAdapter.ClickListener {
    private var param1: String? = null
    private var param2: Boolean = false
    private var position: Int? = null
    private lateinit var noteViewModel: NotesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getBoolean(ARG_PARAM2)
            position = it.getInt(ARG_PARAM3)
        }
        val word = param1?.let { NotesEntity(it) }
        if (word != null) {
            noteViewModel.insert(word)
        }
        if (param2) {
            if (word != null) {
                noteViewModel.update(word)
            }
        } else {
            if (word != null) {
                noteViewModel.insert(word)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setImageResource(R.drawable.ic_add_24)
        fab?.setOnClickListener {
            goToSecondFragment("", false, null)
        }
        textView_first.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val adapter = CustomAdapter(this)
        textView_first.adapter = adapter
        noteViewModel.allWords.observe(viewLifecycleOwner, { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                adapter.setWords(it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: Boolean, position: Int?) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putBoolean(ARG_PARAM2, param2)
                    position?.let { putInt(ARG_PARAM3, it) }
                }
            }
    }

    private fun goToSecondFragment(value: String?, existingText: Boolean, position: Int?) {
        value?.let {
           activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.content_frame,
                SecondFragment.newInstance(it, existingText, position)
            )
                ?.addToBackStack("SecondFragment")
                ?.commit()
        }
    }

    override fun itemClickListener(textContent: String?, position: Int) {
        goToSecondFragment(textContent, true, position)
        val word = textContent?.let { NotesEntity(it) }
        if (word != null) {
            noteViewModel.delete(word)
        }
    }

    override fun itemLongClickListener(textContent: String?, position: Int) {
        val word = textContent?.let { NotesEntity(it) }
        val builder = AlertDialog.Builder(activity)
        //set title for alert dialog
        builder.setTitle("Alert!")
        //set message for alert dialog
        builder.setMessage("Do you want to delete this note?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { _, _ ->
            Toast.makeText(activity, "Deleted", Toast.LENGTH_LONG).show()
            if (word != null) {
                noteViewModel.delete(word)
            }
        }

        //performing negative action
        builder.setNegativeButton("No") { _, _ ->
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}