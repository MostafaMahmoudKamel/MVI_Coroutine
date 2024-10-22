package com.example.mvi_task.uimodel

import com.example.mvi_task.model.Contact

sealed class ViewIntent {
    //    data object Loading :ViewIntent()
    data class SetName(var name: String) : ViewIntent()
    data class SetPhone(var phone: String) : ViewIntent()
    data class SetAddress(var address: String) : ViewIntent()
    data class SetEmail(var email: String) : ViewIntent()
    data class SetNameError(var isError: Boolean) : ViewIntent()
    data class SetPhoneError(var isError: Boolean) : ViewIntent()
    data class SetAddressError(var isError: Boolean) : ViewIntent()
    data class SetEmailError(var isError: Boolean) : ViewIntent()
    data class SetBottomAddSheed(var visibile: Boolean) : ViewIntent()
    data class SetBottomUpdateSheed(var visibile: Boolean) : ViewIntent()
    data class SetIndex(var index: Int) : ViewIntent()

    class AddContact(var contact: Contact) : ViewIntent()
    class UpdateContact(var index: Int, var newContact: Contact) : ViewIntent()
    class DeleteContact(var index: Int) : ViewIntent()


}