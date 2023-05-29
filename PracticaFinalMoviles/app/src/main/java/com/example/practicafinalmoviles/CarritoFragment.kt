package com.example.practicafinalmoviles

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicafinalmoviles.adapter.CartAdapter
import com.example.practicafinalmoviles.clases.Product
import com.example.practicafinalmoviles.databinding.FragmentShoppingCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CarritoFragment : Fragment() {

    private lateinit var cartAdapter: CartAdapter;
    private var price: Int = 0
    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)

        cartAdapter = CartAdapter(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://utadlsz-default-rtdb.firebaseio.com/")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productosCarro.adapter = cartAdapter
        binding.productosCarro.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);

        var reference : DatabaseReference = database.reference.child("Users").child(auth.currentUser!!.uid).child("Cart")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( data in snapshot.children )
                {
                    val product = data.getValue(Product::class.java)
                    price += product!!.price!!
                    cartAdapter.addProductCart(product!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.buttonEnd.setOnClickListener {
            ConfirmPurchase(price).show(childFragmentManager, null)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}