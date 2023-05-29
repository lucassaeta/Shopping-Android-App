package com.example.practicafinalmoviles

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practicafinalmoviles.clases.Product
import com.example.practicafinalmoviles.databinding.FragmentProductsBinding
import com.example.practicamoviles.adapter.ProductAdapter
import org.json.JSONArray

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProductosFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private lateinit var productAdapter: ProductAdapter;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        productAdapter = ProductAdapter(context, findNavController())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemList.adapter = productAdapter
        binding.itemList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        val apiURL = "https://dummyjson.com/products"
        var petition: JsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, apiURL, null, {
                val products: JSONArray = it.getJSONArray("products")
                for (i in 0 until products.length()) {
                    val products = products.getJSONObject(i)
                    val product = Product(products.getString("id").toInt(), products.getString("title"), products.getString("description"), products.getString("price").toInt(), products.getString("rating").toDouble(), products.getString("stock").toInt(),  products.getString("brand"), products.getString("thumbnail"))
                    productAdapter.addProduct(product)
                }
            }, {
                Log.v("response", "Error_petition_failed")
            })
        Volley.newRequestQueue(context).add(petition)
        binding.cartButton.setOnClickListener {
            findNavController().navigate(R.id.action_ProductosFragment_to_CarritoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}