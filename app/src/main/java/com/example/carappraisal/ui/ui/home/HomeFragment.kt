package com.example.carappraisal.ui.ui.home

import android.content.Intent
import android.media.metrics.Event
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carappraisal.R
import com.example.carappraisal.adapter.HomeAdapter
import com.example.carappraisal.databinding.FragmentHomeBinding
import com.example.carappraisal.model.Brand
import com.example.carappraisal.ui.InsertActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rvBrand: RecyclerView
    private val list = ArrayList<Brand>()

   override fun onCreateView(
       inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       val homeViewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)

       _binding = FragmentHomeBinding.inflate(inflater, container, false)
       val root: View = binding.root

       return root
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBrand = binding.brandRv
        list.addAll(getBrand())
        showRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBrand(): ArrayList<Brand>{
        val brandName = resources.getStringArray(R.array.data_name)
        val photoBrand = resources.obtainTypedArray(R.array.data_photo)
        val listBrand = ArrayList<Brand>()
        for (i in brandName.indices){
            val brand = Brand(brandName[i], photoBrand.getResourceId(i, -1))
            listBrand.add(brand)
        }
        return listBrand
    }

    private fun showRecyclerView(){
        rvBrand.layoutManager = GridLayoutManager(requireContext(), 2)
        val listBrandAdapter = HomeAdapter(list)
        rvBrand.adapter = listBrandAdapter
        rvBrand.setHasFixedSize(true)

        listBrandAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener{
            override fun onItemClick(event: Brand) {
                val intent = Intent(requireContext(), InsertActivity::class.java)
                intent.putExtra("name", event.name)
                startActivity(intent)
            }


            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }
        })

    }
}