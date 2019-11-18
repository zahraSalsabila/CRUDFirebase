package com.asysyifazahrasalsabila.crudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("users")

        btnSave.setOnClickListener {
            saveData()
            val intent = Intent(this, ShowActivity::class.java)
            startActivity(intent)
        }

        btnListData.setOnClickListener {
            val i = Intent(this, ShowActivity::class.java)
            startActivity(i)
        }
    }

    private fun saveData() {
        val nama = txtNama.text.toString()
        val kelas = txtKelas.text.toString()

        val userID = ref.push().key.toString()
        val user = Model(userID, nama, kelas)

        ref.child(userID).setValue(user).addOnCompleteListener{
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            txtNama.setText("")
            txtKelas.setText("")
        }
    }
}
