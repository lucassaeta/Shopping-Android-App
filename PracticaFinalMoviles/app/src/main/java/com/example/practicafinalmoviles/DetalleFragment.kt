package com.example.practicafinalmoviles

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.practicafinalmoviles.clases.Product
import com.example.practicafinalmoviles.databinding.FragmentDetailBinding

class DetalleFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private lateinit var product: Product
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        product = Product(arguments?.getString("productId")?.toInt() ?: 0, arguments?.getString("productName") ?: "", arguments?.getString("pDescription") ?: "",
            arguments?.getString("productPrice")?.toInt() ?: 0, arguments?.getString("productRating")?.toDouble() ?: 0.0,
            arguments?.getString("productStock")?.toInt() ?: 0, arguments?.getString("productBrand") ?: "", arguments?.getString("productImage") ?: "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productName.text = product.name
        binding.productDescription.text = product.description
        binding.productStock.text = "Stock: " + product.stock.toString()
        binding.productPrice.text = product.price.toString() + "$"

        Glide.with(requireContext()).load(product.image).into(binding.productImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}