package com.example.pharmaecomapp.apiHelper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.pharmaecomapp.R


class ProgressView(var context: Context) {
    private val dialog: Dialog

    init {
        dialog = Dialog(context, R.style.DialogFragmentTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.loader_dialog)
        dialog.setCancelable(false)
    }

    fun showLoader() {
        if (!dialog.isShowing) {
            dialog.show()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } else {
            dialog.dismiss()
            dialog.show()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun hideLoader() {
        if (isShowing) {
            dialog.dismiss()
        }
    }

    val isShowing: Boolean
        get() = dialog.isShowing
}
