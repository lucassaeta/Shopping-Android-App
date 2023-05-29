package com.example.practicamoviles.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicafinalmoviles.R
import com.example.practicafinalmoviles.clases.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProductAdapter(var context: Context, private val navController: NavController) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var products: ArrayList<Product> = ArrayList()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://utadlsz-default-rtdb.firebaseio.com/")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.item_image)
        var nameTextView: TextView = itemView.findViewById(R.id.item_name)
        var priceTextView: TextView = itemView.findViewById(R.id.item_price)
        var buyButton: Button = itemView.findViewById(R.id.button_buy)
        var detailButton: Button = itemView.findViewById(R.id.button_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = products[position]
        var bundle = Bundle()

        bundle.putString("productId", product.productId.toString())
        bundle.putString("productName", product.name.toString())
        bundle.putString("productDescription", product.description.toString())
        bundle.putString("productPrice", product.price.toString())
        bundle.putString("productRating", product.rating.toString())
        bundle.putString("productStock", product.stock.toString())
        bundle.putString("productBrand", product.brand.toString())
        bundle.putString("productImage", product.image.toString())

        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price.toString() + "$"
        Glide.with(holder.itemView.context).load(product.image).into(holder.imageView)

        holder.buyButton.setOnClickListener {

            var reference : DatabaseReference = database.reference.child("Users").child(auth.currentUser!!.uid).child("Cart").push()
            reference.setValue(Product(product.productId, product.name, product.description, product.price, product.rating, product.stock, product.brand, product.image))
            Snackbar.make(holder.itemView, "Product added to shopping cart!", Snackbar.LENGTH_SHORT).show()
        }

        holder.detailButton.setOnClickListener {
            navController?.navigate(R.id.action_ProductosFragment_to_detalleFragment, bundle)
        }
    }

    fun addProduct(product: Product) {
        this.products.add(product)
        notifyItemInserted(products.size - 1)
    }
}