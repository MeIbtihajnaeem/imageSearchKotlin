package com.example.image_search_kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule


class SearchResult: AppCompatActivity() {
    var listOfImageDetails: List<ImageDetails>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Test","Testing")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        try {
            val jsonResponse: String? = getIntent().getStringExtra("JSONResponse")
            Log.d("ImageDetails Extracting", "Size :")
            listOfImageDetails = getImageDetailsFromJson(jsonResponse)
            Log.d("ImageDetails Success", "Size :" + listOfImageDetails!!.size)
            ConnectionCheck.isNetworkConnected(
                this
            )
            initRecyclerView()
        } catch (e: Exception) {
            Log.d(TAG, "Exception caught : " + e.message)
        }
    }

    private fun initRecyclerView() {
        Log.d(TAG, "Starting RecyclerView....")
        val recyclerView: RecyclerView =
            findViewById<RecyclerView>(R.id.searchResults_recycleView)

        Log.i("S view ","1234")
        val recycleViewAdapter: RecycleViewAdapter? = listOfImageDetails?.let { RecycleViewAdapter(it,this) }
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(recycleViewAdapter)
        Log.i("S view ","2444")
    }

   // @Throws(JSONException::class, IOException::class)
   private fun getImageDetailsFromJson(json: String?): List<ImageDetails>? {
            val mapper = ObjectMapper().registerModule(KotlinModule())
       val dataHolder:ResultItemsJsonDataHolder = mapper.readValue(json,ResultItemsJsonDataHolder::class.java)
       println("Hello World")
       println(dataHolder.resultItems)
        Log.i("111","111")
        return dataHolder.resultItems
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private class ResultItemsJsonDataHolder {
        @JsonProperty("items")
        var resultItems: List<ImageDetails>? = null
    }

    companion object {
        private const val TAG = "SearchResults"
    }
}