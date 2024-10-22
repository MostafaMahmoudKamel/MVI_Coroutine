package com.example.mvi_task.uimodel

import com.example.mvi_task.model.Contact
import com.example.mvvm_contactapp_t24.ui.layout.UIState

data class ViewState(
    var uiState: UIState = UIState.Loading,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var email: String = "",
    var nameError: Boolean = false,
    var phoneError: Boolean = false,
    var addressError: Boolean = false,
    var emailError: Boolean = false,
    var contactList: List<Contact> = emptyList(),
    var index: Int = -1,
    var search: String = "",
    var isAddBottomVisible: Boolean = false,
    var isUdateBottomVisible: Boolean = false,


    )

