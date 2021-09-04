package com.example.w1_d1_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_two.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTwo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTwo : Fragment(R.layout.fragment_two) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var  viewModel: MainViewModel


    private val changeObserver =
        Observer<String> {
                value -> value?.let { viewModel.hitCount }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt("pos")

        val president = GlobalModel.presidents[position]

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.query.observe(viewLifecycleOwner, changeObserver)

        viewModel.queryName(president.name)
        Log.d("apiCall", viewModel.query.value.toString())
        Log.d("apiCall", viewModel.hitCount.value.toString())
        viewModel.hitCount.observe(
            viewLifecycleOwner
        ) {
            val hitCountString = it.query.searchinfo.totalhits.toString()
            PresidentHits.text = "hits: ${hitCountString}"
        }

        Log.d("USR", "Fragment 2, president $position")
        PresidentName.text = president.name
        PresidentDescription.text = president.description
        PresidentStartTime.text = president.start.toString()
        PresidentEndTime.text = president.end.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTwo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTwo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}