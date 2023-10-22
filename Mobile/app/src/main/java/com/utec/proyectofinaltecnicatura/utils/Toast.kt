package com.utec.proyectofinaltecnicatura.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.utec.proyectofinaltecnicatura.R

fun showInfoToast(context: Context, message: String) {
    val layoutInflater = LayoutInflater.from(context)
    val view = layoutInflater.inflate(R.layout.toast_custom, null)

    val messageView = view.findViewById<TextView>(R.id.toast_message)
    messageView.text = message

    val imageView = view.findViewById<ImageView>(R.id.toast_icon)
    imageView.setBackgroundResource(R.drawable.ico_toast_info)

    val toast = Toast(context)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = view
    toast.show()
}

fun showErrorToast(context: Context, message: String) {
    val layoutInflater = LayoutInflater.from(context)
    val view = layoutInflater.inflate(R.layout.toast_custom, null)

    val messageView = view.findViewById<TextView>(R.id.toast_message)
    messageView.text = message

    val imageView = view.findViewById<ImageView>(R.id.toast_icon)
    imageView.setBackgroundResource(R.drawable.ico_toast_error)

    val toast = Toast(context)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = view
    toast.show()
}