package com.example.w1_d1_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOne.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainViewModel : ViewModel() {
    val repository: WikiRepository = WikiRepository()
    val query = MutableLiveData<String>()


    fun queryName(name: String) {
        Log.d("hitCountCheck", "queryName")
        query.value = name
    }

    /*val queryName = query.switchMap {
        liveData(Dispatchers.IO) { emit(repository.hitCountCheck("Trump")) }
    }*/
    val hitCount = query.switchMap {
        Log.d("hitCountCheck", "chekcing")
        liveData(Dispatchers.IO) { emit(repository.hitCountCheck(it)) }
    }

}


class FragmentOne : Fragment(R.layout.fragment_one) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textField = view.findViewById<TextView>(android.R.id.text1)
    }

    lateinit var viewModel: MainViewModel


    private val changeObserver =
        Observer<String> { value ->
            value?.let { viewModel.hitCount }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val rvitems = view.findViewById<RecyclerView>(R.id.idRecycleList);
        rvitems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.query.observe(viewLifecycleOwner, changeObserver)
        rvitems.adapter = object : RecyclerView.Adapter<ItemHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).inflate(
                        android.R.layout.simple_list_item_1, parent,
                        false
                    )
                )
            }

            override fun getItemCount(): Int {
                Log.d("dbg", "${GlobalModel.presidents.size}")
                return GlobalModel.presidents.size
            }

            override fun onBindViewHolder(holder: ItemHolder, position: Int) {

                holder.textField.text = GlobalModel.presidents[position].name
                holder.textField.setOnClickListener {
                    Log.d("USR", "Clicked $position")
                    viewModel.queryName("Trump")
                    Log.d("apiCall", viewModel.query.value.toString())
                    Log.d("apiCall", viewModel.hitCount.value.toString())

                    val bundle = bundleOf("pos" to position)

                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<FragmentTwo>(R.id.fragmentContainerView, args = bundle)
                        addToBackStack(null)
                    }


                }
            }
        }
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
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentOne.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentOne().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}