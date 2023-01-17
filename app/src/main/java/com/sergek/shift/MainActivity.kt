 package com.sergek.shift

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import com.sergek.shift.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

 class MainActivity : AppCompatActivity() {
     lateinit var request_text: String
    lateinit var textInput: EditText
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        request_text = "https://lookup.binlist.net/45717360"
        textInput = findViewById(R.id.TextInput)
        if (request_text.length > 30){
            fetchData(request_text).start()
        }
    }

     private fun fetchData(request_text: String?): Thread{
         return Thread{
             val url = URL(request_text)
             val connection = url.openConnection() as HttpURLConnection
             if (connection.responseCode == 200){
                 val inputSystem = connection.inputStream
                 val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                 val request = Gson().fromJson(inputStreamReader, Request::class.java)
                 updateUI(request)
                 inputStreamReader.close()
                 inputSystem.close()
             } else {
                 binding.scheme.text = "Failed Connection"
             }
         }
     }

     private fun updateUI(request: Request) {
        runOnUiThread{
            kotlin.run {
                binding.length.text = request.number.length.toString()
                if (request.number.luhn == true){
                    binding.luhn.text = "Yes"
                } else {
                    binding.luhn.text = "No"
                }

                binding.scheme.text = request.scheme
                binding.type.text = request.type
                binding.brand.text = request.brand
                if (request.prepaid == true) {
                    binding.prepaid.text = "Yes"
                } else {
                    binding.prepaid.text = "No"
                }

                binding.country.text = request.country.name
                binding.currency.text = request.country.currency
                binding.latitude.text = request.country.latitude.toString()
                binding.longitude.text = request.country.longitude.toString()

                binding.bank.text = request.bank.name
                binding.city.text = request.bank.city
                binding.url.text = request.bank.url
                binding.phone.text = request.bank.phone
            }
        }
     }

     fun buttonClick(view: View?) {
         request_text = "https://lookup.binlist.net/"
         request_text+=textInput.text
        println(textInput.text)
         if (request_text.length > 30){
             fetchData(request_text).start()
         }
    }
}