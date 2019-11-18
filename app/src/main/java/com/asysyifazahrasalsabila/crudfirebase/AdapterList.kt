package com.asysyifazahrasalsabila.crudfirebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class AdapterList(val ctx: Context, val layoutResId: Int, val list: List<Model>): ArrayAdapter<Model>(ctx, layoutResId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val itemNama = view.findViewById<TextView>(R.id.itemNama)
        val itemKelas = view.findViewById<TextView>(R.id.itemKelas)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        val user = list[position]

        itemNama.text = user.nama
        itemKelas.text = user.kelas

        btnUpdate.setOnClickListener {
            updateData(user)
        }

        btnDelete.setOnClickListener {
            deleteData(user)
        }

        return view
    }

    private fun deleteData(user: Model) {
        var progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()

        val db = FirebaseDatabase.getInstance().getReference("users")
        db.child(user.id).removeValue()
        Toast.makeText(ctx, "Deleted", Toast.LENGTH_SHORT).show()

        val i = Intent(context, ShowActivity::class.java)
        context.startActivity(i)
        (context as Activity).finish()
    }

    private fun updateData(user: Model) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Update")
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.update, null)

        val updateNama = view.findViewById<EditText>(R.id.updateNama)
        val updateKelas = view.findViewById<EditText>(R.id.updateKelas)

        updateNama.setText(user.nama)
        updateKelas.setText(user.kelas)

        builder.setView(view)

        builder.setPositiveButton("Update"){
            dialog, which ->
            val db = FirebaseDatabase.getInstance().getReference("users")

            val nama = updateNama.text.toString()
            val kelas = updateKelas.text.toString()

            val user = Model(user.id, nama, kelas)

            db.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(ctx, "Updated", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel"){
            dialog, which ->
        }

        builder.create().show()
    }
}