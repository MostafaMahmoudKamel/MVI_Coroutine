package com.example.mvvm_contactapp_t24.utils

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isNameValid(): Boolean {
    return this.length > 4
}

fun String.isEmailValid(): Boolean {
    val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return this.matches(regex)
}

fun String.isPhoneValid(): Boolean {
    val phoneRegex = Regex("^\\+?[1-9]\\d{1,14}$")
    return this.matches(phoneRegex)
}

@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerState.convertDateToString(): String {
    val selectedDateString = this.selectedDateMillis?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.format(Date(it))
    } ?: "No Date Selected"
    return selectedDateString
}