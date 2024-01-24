package com.example.calculatelove

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Callback
import com.example.calculatelove.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        with(binding) {
            btnCalculate.setOnClickListener {
                RetrofitService().api.getCompatibility(
                    firstName.text.toString(), secondName.text.toString()
                ).enqueue(object : Callback<LoveModel> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<LoveModel>, response: Response<LoveModel>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val result =" ${it.firstName} \n${it.secondName} \n ${it.percentage}\n ${it.result}"
                                tvResult.text = result
                                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                                intent.putExtra("result", it)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoveModel>, t: Throwable) {
                        Log.e("ololo", "onFailure: ${t.message}")
                    }
                })
            }
        }
    }
}

