package com.aayar94.personalservicesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.aayar94.personalservicesapp.databinding.ActivityGirisBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.core.Tag
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Activity_giris : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityGirisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth


        binding.button.setOnClickListener {
            var eposta=binding.etEposta.text.toString()
            var sifre=binding.etSifre.text.toString()

            auth.createUserWithEmailAndPassword(eposta,sifre)
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful){

                       HizmetKaydet()
                    }else{
                        Toast.makeText(baseContext,task.exception.toString(),Toast.LENGTH_LONG).show()

                    }
                }

        }
    }

    private fun HizmetKaydet() {
        var user = User()
        user.name = binding.etAd.text.toString()
        user.lastName = binding.etSoyad.text.toString()
        user.serviceType=binding.etHizmet.text.toString()

        val dbRef = Firebase.database.getReference()
        dbRef.child("Kullanicilar").child(auth.uid.toString()).setValue(user)
    }

}