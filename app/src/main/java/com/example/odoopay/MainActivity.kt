package com.example.odoopay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.style.UpdateLayout
import android.view.inputmethod.InputBinding
import android.widget.TextView
import com.example.odoopay.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        fechCurencyData().start()

    }
    private fun fechCurencyData():Thread
    {
        return Thread{
            val url = URL("https://open.er-api.com/v6/latest/aud")
            val connection = url.openConnection() as HttpURLConnection
            if( connection.responseCode==200)
            {
                val inputSystem = connection.inputStream
              val inputStreamReader= InputStreamReader(inputSystem , "UTF8")
                val request=Gson().fromJson(inputStreamReader,Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }else
            {
                binding.UAD.text="faild"
            }
        }
    }

    private fun updateUI(request: Request) {
        runOnUiThread{
            kotlin.run {
                val UpdateText=findViewById<TextView>(R.id.UpdateText)
                val NZD=findViewById<TextView>(R.id.NZD)
                val USD=findViewById<TextView>(R.id.USD)
                val UAD=findViewById<TextView>(R.id.UAD)
                val GBP=findViewById<TextView>(R.id.GBP)
                UpdateText.text=request.time_last_update_utc
                NZD.text= String.format("%.2fNZD", request.rates.NZD)


  USD.text = String.format("%.2fNZD", request.rates.USD)

    GBP.text = String.format("NZD%.2f", request.rates.GBP)
}


        }

    }
}