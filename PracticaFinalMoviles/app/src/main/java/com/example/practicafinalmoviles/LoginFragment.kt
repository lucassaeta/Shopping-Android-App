package com.example.practicafinalmoviles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.practicafinalmoviles.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.login_to_register)
        }

        binding.loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.editEmail.text.toString(), binding.editPassword.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful) {
                        val intent = Intent(context, SecondActivity::class.java)
                        var bundle = Bundle()
                        bundle.putString("uid", auth.uid.toString())
                        intent.putExtras(bundle)
                        context?.startActivity(intent, bundle)
                    } else {
                        Snackbar.make(binding.root, "Login failed. Incorrect credentials!", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}