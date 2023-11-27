package com.doron.personalnotes

import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.doron.personalnotes.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var fbAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()

        binding.signToLgn.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        binding.signBtn.setOnClickListener {
            val mail = binding.signMail.text.toString()
            val password = binding.signPass.text.toString()
            val repass = binding.signRepass.text.toString()

            if (mail.isNotEmpty() && password.isNotEmpty() && repass.isNotEmpty()) {
                if (password == repass) {
                    fbAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, login::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                    binding.signPass.text.clear()
                    binding.signRepass.text.clear()
                }
            } else {
                Toast.makeText(this, "Please check all the fields", Toast.LENGTH_LONG).show()
                binding.signPass.text.clear()
                binding.signRepass.text.clear()
            }
        }
    }
}