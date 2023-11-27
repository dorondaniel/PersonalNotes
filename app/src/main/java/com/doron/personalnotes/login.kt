package com.doron.personalnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.doron.personalnotes.databinding.ActivityLoginBinding
import com.doron.personalnotes.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var fbAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()

        binding.lgnToSign.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        binding.lgnBtn.setOnClickListener {
            val mail = binding.lgnMail.text.toString()
            val password = binding.lgnPass.text.toString()

            if (mail.isNotEmpty() && password.isNotEmpty()) {
                fbAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please check all the fields", Toast.LENGTH_LONG).show()
                binding.lgnPass.text.clear()
            }
        }
    }
}