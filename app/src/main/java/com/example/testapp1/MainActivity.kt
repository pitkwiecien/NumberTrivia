package com.example.testapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testapp1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.deleteDatabase("History")

        binding.getFacts.setOnClickListener{
            val text = binding.numberInput.text.toString()
            if(text == ""){
                Log.e("testing_logger", "invalid input")
                return@setOnClickListener
            }
            val chosenNumber = text.toInt()
            val url = "${Constants.API_URL}$chosenNumber/"
            Log.d("testing_logger", chosenNumber.toString())
            getDataFromApi(url)
        }

        binding.getRandom.setOnClickListener{
            getDataFromApi(Constants.RANDOM_URL)
        }
    }

    fun getDataFromApi(fromUrl: String){
        Log.d("testing_logger", fromUrl.toString())
        val req = StringRequest(
            Request.Method.GET, fromUrl, {
                    response -> Log.d("testing_logger", response)

                val spaceAt = getFirstChar(response, ' ')
                val num = response.substring(0, spaceAt).toInt()
                Log.d("testing_logger", "num: $num")
                Log.d("testing_logger", "fact: $response")

                val dbHelper = DBHelper(this)
                dbHelper.insert("history", arrayListOf(
                    mapOf(
                        "number" to "_$num",
                        "fact" to response
                    )
                ))
                Log.d("testing_logger", dbHelper.getContent().toString())
                dbHelper.close()

                Globals.currentNumber = num
                Globals.currentFact = response

                val intent = Intent(this, FactActivity::class.java)
                startActivity(intent)

                updateRecycler()
            }, {
                    response -> Log.e("testing_logger", response.toString())
            }
        )
        val queue = Volley.newRequestQueue(this)
        queue.add(req)
    }

    private fun getFirstChar(st: String, char: Char): Int{
        for(i in 0..st.length){
            if(st[i] == char){
                return i
            }
        }
        return -1
    }

    private fun updateRecycler(){
        val resultList: ArrayList<ArrayList<String>> = ArrayList()
        val dbHelper = DBHelper(this)
        val dbContent = dbHelper.getContent()
        Log.d("testing_logger", dbContent.toString())

        if(dbContent.isNotEmpty()) {
            for (i in 0 until dbContent.count()) {
                val tmpList: ArrayList<String> = ArrayList()
                val colonPos = getFirstChar(dbContent[i], ':')
                val number = dbContent[i].substring(0, colonPos)
                val fact = dbContent[i].substring(colonPos + 1)
                tmpList.add(number)
                tmpList.add(fact)
                resultList.add(tmpList)
            }
        }
        binding.recycler.adapter = Adapter(resultList, this)

    }
}