package com.example.image_search_kotlin


import android.R
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.app.AlertDialog


internal object ConnectionCheck {
    fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connected = cm.activeNetworkInfo != null
        if (!connected) {
            AlertDialog.Builder(context)
                .setTitle("NO INTERNET CONNECTION")
                .setMessage("Oops !!! It seems that you are not connected to the Internet.")
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
        return connected
    }
}