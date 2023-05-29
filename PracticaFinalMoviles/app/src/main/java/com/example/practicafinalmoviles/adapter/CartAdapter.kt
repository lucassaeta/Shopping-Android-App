package com.example.practicafinalmoviles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinalmoviles.R
import com.example.practicafinalmoviles.clases.Product

class CartAdapter(var context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var products: ArrayList<Product> = ArrayList()

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cartName: TextView = itemView.findViewById(R.id.cart_name)
        var cartPrice: TextView = itemView.findViewById(R.id.cart_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.recyclerview_detail, parent, false)
        return CartAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = products[position]

        holder.cartName.text = product.name
        holder.cartPrice.text = product.price.toString() + "$"
    }

    fun addProductCart(product: Product) {
        this.products.add(product)
        notifyItemInserted(products.size - 1)
    }
}