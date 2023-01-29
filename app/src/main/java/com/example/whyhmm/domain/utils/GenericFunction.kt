package com.example.whyhmm.domain.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.NonCancellable.start


fun showImageLoader(context: Context)= CircularProgressDrawable(context).apply {
    strokeWidth = 5f
    centerRadius = 30f
    start()
}


