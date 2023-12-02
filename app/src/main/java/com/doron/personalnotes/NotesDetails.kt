package com.doron.personalnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.doron.personalnotes.databinding.ActivityMainBinding
import com.doron.personalnotes.databinding.ActivityNotesDetailsBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class NotesDetails : AppCompatActivity() {
    private lateinit var binding : ActivityNotesDetailsBinding

    lateinit var titleEt:EditText
    lateinit var contentEt:EditText
    lateinit var titletv: TextView
    lateinit var ettitle: String
    lateinit var etcontent: String
    lateinit var docId: String
    lateinit var delete_tv : TextView

    var isEdit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleEt = binding.titleTxt
        contentEt = binding.contentTxt
        titletv = binding.pgTitle

        delete_tv = binding.deleteTv

        //receive data
        ettitle = intent.getStringExtra("title").toString()
        etcontent = intent.getStringExtra("content").toString()
        docId = intent.getStringExtra("docId").toString()

        if(docId.isNotEmpty()){
            isEdit = true
        }

        titleEt.setText(ettitle)
        contentEt.setText(etcontent)

        if(isEdit){
            titletv.setText("Edit Your Note")
            delete_tv.visibility = View.VISIBLE
        }
        delete_tv.setOnClickListener{
            deleteNotes()
        }


        binding.donebtn.setOnClickListener {
            saveNotes()
        }
    }

    private fun deleteNotes() {
        var docref: DocumentReference
        docref = Utility.getCollectionRef().document(docId)
        docref.delete().addOnCompleteListener{
            if (it.isSuccessful()){
                Utility.showToast(this,"Notes Deleted")
                finish()
            }else{
                Utility.showToast(this,"Failed Deleting Note")
            }
        }
    }

    fun saveNotes() {
        val title_notes = titleEt.text.toString()
        val content_notes = contentEt.text.toString()

        if (title_notes.isEmpty()){
            titleEt.setError("Title not set")
            return
        }
        if (content_notes.isEmpty()){
            contentEt.setError("Content not Provided")
            return
        }
        val note = Notes()
        note.setTitle(title_notes)
        note.setContent(content_notes)
        note.setTime(Timestamp.now())

        saveNoteToFb(note)

    }
    fun saveNoteToFb(notes: Notes) {
        var docref: DocumentReference
        if(isEdit){
            docref = Utility.getCollectionRef().document(docId)
        }else{
            docref = Utility.getCollectionRef().document()
        }
        docref.set(notes).addOnCompleteListener{
            if (it.isSuccessful()){
                Utility.showToast(this,"Notes Saved")
                finish()
            }else{
                Utility.showToast(this,"Failed Adding Note")
            }
        }
    }
}