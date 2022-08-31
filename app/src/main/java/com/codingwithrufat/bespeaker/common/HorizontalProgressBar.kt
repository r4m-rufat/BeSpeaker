package com.codingwithrufat.bespeaker.common

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView

fun View.showHorizontalProgress(progressBar: ProgressBar, titleTextView: TextView, percentTextView: TextView, uploadingPercent: Int, title: String) {
    this.visibility = VISIBLE
    titleTextView.text = title
    percentTextView.text = "$uploadingPercent%"
    progressBar.progress = uploadingPercent
}

fun View.showHorizontalProgress(titleTextView: TextView, percentTextView: TextView, title: String) {
    this.visibility = VISIBLE
    titleTextView.text = title
    percentTextView.visibility = GONE
}

fun View.hideHorizontalProgress() {
    this.visibility = GONE
}