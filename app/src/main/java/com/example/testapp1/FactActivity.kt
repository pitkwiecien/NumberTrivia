package com.example.testapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp1.databinding.ActivityFactBinding

class FactActivity : AppCompatActivity() {
    lateinit var binding: ActivityFactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact)

        binding = ActivityFactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.number.text = Globals.currentNumber.toString()
        binding.fact.text = Globals.currentFact
    }
}