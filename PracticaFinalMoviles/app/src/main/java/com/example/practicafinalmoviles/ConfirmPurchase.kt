package com.example.practicafinalmoviles

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfirmPurchase(var finalPrice: Int): DialogFragment() {
    
    private lateinit var view : View
    private lateinit var payButton : Button
    private lateinit var priceText : TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://utadlsz-default-rtdb.firebaseio.com/")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        view = LayoutInflater.from(requireContext()).inflate(R.layout.confirm_purchase, null)
        builder.setView(view)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        payButton = view.findViewById(R.id.pay_button)
        priceText = view.findViewById(R.id.price_text)

        priceText.text = "Total: " + finalPrice.toString() + "$"

        val reference: DatabaseReference = database.reference.child("Users")
            .child(auth.currentUser!!.uid)
            .child("Cart")

        payButton.setOnClickListener {
            reference.removeValue()
            dismiss()
            findNavController().popBackStack()
        }
    }
}