package com.example.whyhmm.domain.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.SystemClock
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.example.whyhmm.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar


fun View.snackbar(context: Context, flag : Int, message: String, action: (() -> Unit)? = null) {
    val snackbar : Snackbar = when (flag) {
        0 -> { // Infinite
            Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        }
        1 -> { //Length
            Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        }
        else -> { //short
            Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        }
    }
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.apply {
        setBackgroundTint(ContextCompat.getColor(context, R.color.btn_blue))
        setTextColor(Color.WHITE)
        setAnchorView(R.id.bottomNavView);
        setActionTextColor(ContextCompat.getColor(context, R.color.snackaction))
        animationMode = ANIMATION_MODE_FADE
        show()
    }
}

fun Fragment.superNavigate(action: NavDirections){
    try {
        this.findNavController().navigate(action)
    }catch (e : Exception){
        showlog(e.toString())
    }
}

fun Navigation.superNavigate(view : View, action: NavDirections){
    try {
        view.findNavController().navigate(action)
    }catch (e : Exception){
        showlog(e.toString())
    }
}

private val clickTag = "__click__"
fun View.singleClickListener(debounceTime: Long = 1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val timeNow = SystemClock.elapsedRealtime()
            val elapsedTimeSinceLastClick = timeNow - lastClickTime
            showlog( """
                        DebounceTime: $debounceTime
                        Time Elapsed: $elapsedTimeSinceLastClick
                        Is within debounce time: ${elapsedTimeSinceLastClick < debounceTime}
                    """.trimIndent())

            if (elapsedTimeSinceLastClick < debounceTime) {
                showlog("Double click shielded")
                return
            } else {
                showlog("Click happened")
                action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun showlog(str : String)  {
    Log.d("LogTag", str)
}

fun View.clickable(status : Boolean){
    this.isClickable = status
}

fun View.enable(status : Boolean){
    this.isEnabled = status
}
fun View.enableWithAlpha(status : Boolean){
    this.isEnabled = status
    if (status) this.alpha = 1f
    else this.alpha = 0.5f
}

fun String.removeWhitespaces() = replace("\\s".toRegex(), "")

fun String.underLine(): SpannableString {
    val spanStr = SpannableString(this)
    spanStr.setSpan(UnderlineSpan(), 0, spanStr.length, 0)
    return spanStr
}

fun ImageView.Circleload(context: Context, url: Any, type: Transformation, image: Int? = null ){
    this.load(url) {
        crossfade(true)
        placeholder(showImageLoader(context))
        transformations(type)
        diskCachePolicy(CachePolicy.ENABLED)
        error(image?: R.drawable.avatar)
    }
}

fun TextView.appendtext(string: String?, @ColorRes color: Int) {
    if (string == null || string.isEmpty()) {
        return
    }

    val spannable: Spannable = SpannableString(string)
    spannable.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, color)),
        0,
        spannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    append(spannable)
}


