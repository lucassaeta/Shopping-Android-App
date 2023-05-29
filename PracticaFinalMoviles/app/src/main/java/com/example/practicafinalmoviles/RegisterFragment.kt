package com.example.practicafinalmoviles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.practicafinalmoviles.clases.User
import com.example.practicafinalmoviles.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://utadlsz-default-rtdb.firebaseio.com/")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.register_to_login)
        }

        binding.registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.editEmail.text.toString(), binding.editPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        var reference : DatabaseReference = database.reference.child("Users").child(auth.uid!!).child("Data")

                        reference.setValue(
                            User(binding.editEmail.text.toString(),
                                binding.editName.text.toString(),
                                binding.editAge.text.toString().toInt(),
                                binding.editAddress.text.toString())
                        )

                        val intent = Intent(context, SecondActivity::class.java)
                        var bundle = Bundle()

                        bundle.putString("uid", auth.uid.toString())
                        intent.putExtras(bundle)
                        context?.startActivity(intent, bundle)

                        Snackbar.make(binding.root, "User created correctly!", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(binding.root, "Failed task", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}