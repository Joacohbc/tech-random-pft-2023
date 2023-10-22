package com.utec.proyectofinaltecnicatura.utils

import android.app.AlertDialog
import android.content.Context
fun showErrorDialog(context: Context, title: String, message: String) {
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setTitle(title)
    alertDialogBuilder.setMessage(message)
    alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert)

    alertDialogBuilder.setPositiveButton("Ok") { dialog, _ ->
        dialog.dismiss()
    }

    alertDialogBuilder.create().show()
}
fun showConfirmDialog(context: Context, title: String, message: String, onClick : (Boolean) -> Unit) {

    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setTitle(title)
    alertDialogBuilder.setMessage(message)

    alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
        onClick(true)
        dialog.dismiss()
    }

    alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
        onClick(false)
        dialog.dismiss()
    }

    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()

    alertDialog.setOnDismissListener {
        onClick(false)
    }
}