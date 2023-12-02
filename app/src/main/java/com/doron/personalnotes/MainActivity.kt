package com.doron.personalnotes

import android.app.DownloadManager.Query
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.doron.personalnotes.Utility.getCollectionRef
import com.doron.personalnotes.databinding.ActivityMainBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.core.OrderBy.Direction

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var ntada: NoteAdapter
    lateinit var recycle: RecyclerView
    lateinit var logout: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recycle = binding.recycle
        logout = binding.logoutbtn

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,login::class.java))
            finish()
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, NotesDetails::class.java)
            startActivity(intent)

        }

        fun setup() {
            val query: com.google.firebase.firestore.Query

            query = getCollectionRef().orderBy(
                "time",
                com.google.firebase.firestore.Query.Direction.DESCENDING
            )

            val notes: FirestoreRecyclerOptions<Notes>
            notes =
                FirestoreRecyclerOptions.Builder<Notes>().setQuery(query, Notes::class.java).build()
            val layoutManager = LinearLayoutManager(this)
            recycle.layoutManager = layoutManager
            ntada = NoteAdapter(notes, MainActivity())
            recycle.adapter = ntada
        }

        fun onStart() {
            super.onStart()
            ntada.startListening()
        }

        fun onStop() {
            super.onStop()
            ntada.stopListening()
        }

        fun onResume() {
            super.onResume()
            ntada.notifyDataSetChanged()
        }
    }
}