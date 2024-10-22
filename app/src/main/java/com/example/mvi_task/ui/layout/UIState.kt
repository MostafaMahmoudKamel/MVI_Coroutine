package com.example.mvvm_contactapp_t24.ui.layout

import com.example.mvi_task.model.Contact

sealed class UIState {
    data object Loading : UIState()
    data class Success(val contacts: List<Contact>, val message: String) : UIState()
    data class Error(val message: String) : UIState()
}