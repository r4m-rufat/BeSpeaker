package com.codingwithrufat.bespeaker.common.utils

import android.text.TextUtils
import android.widget.EditText
import androidx.core.util.PatternsCompat

fun EditText.checkPassword(): Boolean {
    val password = this.text.trim().toString()
    if (password.length >= 6){
        return true
    }

    return false

}

fun EditText.checkEmail(): Boolean {
    val email = this.text.trim().toString()

    if (TextUtils.isEmpty(email)){
        this.error = "Email can not be null"
    }
    else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
        this.error = "Invalid email type"
        return false
    }

    return true

}

infix fun EditText.equal(confirmedPassword: EditText): Boolean {
    if (this.text.trim().toString() == confirmedPassword.text.trim().toString())
        return true

    return false
}