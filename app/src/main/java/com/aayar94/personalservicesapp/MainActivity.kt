package com.aayar94.personalservicesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.aayar94.personalservicesapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

/**
 * uygulamanın acılıs ekranında hizmet veren kisi listesi bunulacak
 * isim soyisim ve hizmet konusu gösterilecek
 * uygulamayı acan kullanıcı hizmet vermek istiyorsa giris yapması gerekecek
 * giriş yap butonu bulunacak ve tıklandıgında giris ekranı acılacak
 * giris yapan kullanıcıdan ad soyad ve hizmet alanı bilgisi alınacak
 * giriş işlemi sonrası ana ekrana dönülecek ve giriş yap butonu kaybolacak
 * uygulama acıldıgında kullnıcı daha once giris yapmış ise tekrar giris yapmacak,
 * giriş yapmıs olacaktır
 * */

class MainActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    override fun onStart() {
        super.onStart()

        auth.currentUser?.let {
            binding.btnGirisYap.visibility=View.GONE
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        binding.btnGirisYap.setOnClickListener {
            startActivity(Intent(this,Activity_giris::class.java))
        }
    }
    fun getData(){
        val userRef = Firebase.database.getReference().database.getReference("Kullanicilar")

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = ArrayList<User>()
                var strList = ArrayList<String>()
                for (ks in snapshot.children){
                    val k= ks.getValue<User>()
                    userList.add(k!!)
                    strList.add("${k.name} ${k.lastName} ${k.serviceType}")

                }
                binding.listView.adapter=ArrayAdapter<String>(this@MainActivity,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,strList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}