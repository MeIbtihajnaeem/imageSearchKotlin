package com.example.image_search_kotlin

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.image_search_kotlin.SearchResult
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var result: String? = null;
    var responseCode: Int? = null
    var responseMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TAG = "MainActivity"
        var editText: EditText = findViewById(R.id.editText) as EditText
        var button: Button = findViewById(R.id.button) as Button




        button.setOnClickListener(View.OnClickListener {
            Log.i("out","out")
             fun onClick(v:View):Unit{
                Log.i("Hello","holoe")
            if (editText.text.toString().isEmpty()) {
                editText.error = "Please enter something !!!"
            } else {
                var searchString:String = editText.text.toString()
                Log.d(TAG, "Searching for : $searchString")
                var inputManager :InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                Log.d(TAG,"I am here")
                // looking for
                val searchStringNoSpaces = searchString.replace(" ", "+")
                val urlString = getString(R.string.baseUrl) + searchStringNoSpaces + "&key=" + getString(R.string.key) + "&cx=" + getString(R.string.cx) + "&alt=" + getString(R.string.responsetype)
                var url: URL? = null
                try {
                    url = URL(urlString)
                } catch (e: MalformedURLException) {
                    Log.e(TAG, "ERROR converting String to URL $e")
                }
                Log.d(TAG, "Url = $urlString")
                // start AsyncTask


                var  searchTask  = GoogleSearchAsyncTask()
                //val searchTask: com.saurabh.gimagesearch.MainActivity.GoogleSearchAsyncTask = com.saurabh.gimagesearch.MainActivity.GoogleSearchAsyncTask()
                Log.d("MainActivity", "Checking internet connectivity")
                if (ConnectionCheck.isNetworkConnected(v.context)) {
                    if (searchTask != null) {
                        searchTask.execute(url)
                    }
                }

            }
        }
            onClick(it)
        })
    }


    inner class GoogleSearchAsyncTask : AsyncTask<URL?, Int?, String>() {

        override fun doInBackground(vararg urls: URL?): String? {

            val url:URL = urls[0]!!
            Log.d("MainActivity", "AsyncTask - doInBackground, url=$url")
            // Http connection
            // Http connection
            var httpURLConnection: HttpURLConnection? = null



            try {

                httpURLConnection = url.openConnection() as HttpURLConnection

            } catch (e: IOException) {
                Log.e("MainActivity", "Http connection ERROR $e")
            }

            try {

                responseCode = httpURLConnection!!.responseCode
                responseMessage = httpURLConnection.responseMessage

            } catch (e: IOException) {
                Log.e("MainActivity", "Http getting response code ERROR $e")
            }
            Log.d("MainActivity", "Http response code =$responseCode message=$responseMessage")

            try {
                if (responseCode == 200) { // response OK

                    val rd = BufferedReader(InputStreamReader(httpURLConnection!!.inputStream))
                    val sb = StringBuilder()
                    var count =0


                    for(line in rd.readLines()){

                        sb.append(line+"\n")

                    }



                    rd.close()

                    httpURLConnection.disconnect()

                    result = sb.toString()
                 //   Log.d("MainActivity", "result=" + result)
                    return result.toString()
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Http Response ERROR $e")
            }
            return null
        }
        override fun onProgressUpdate(vararg progress: Int?) {
            Log.i("Google_Search","onProgressUpdate")
            Log.d("MainActivity", "AsyncTask - onProgressUpdate, progress=$progress")
        }

        override fun onPostExecute(result: String) {
            Log.i("Google_Search","onPostExecute")

            Log.d("MainActivity", "AsyncTask - onPostExecute, result=$result")
          //  dialog?.dismiss()

            val intent = Intent(applicationContext, SearchResult::class.java).apply {

                putExtra("JSONResponse", result)

            }

            startActivity(intent)
        }
    }

}
