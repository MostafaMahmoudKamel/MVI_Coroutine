package com.example.mvi_task.uimodel


sealed class SnackBarViewEffect {
    data class ShowSnackbarView(val message: String,val actionLabel:String?=null):SnackBarViewEffect()
}