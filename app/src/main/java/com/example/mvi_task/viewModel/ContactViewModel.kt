package com.example.mvvm_contactapp_t24.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_task.model.Contact
import com.example.mvi_task.repository.ContactRepositoryImpl
import com.example.mvi_task.uimodel.SnackBarViewEffect
import com.example.mvi_task.uimodel.ViewIntent
import com.example.mvi_task.uimodel.ViewState
import com.example.mvvm_contactapp_t24.ui.layout.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    private val contactRepository = ContactRepositoryImpl()

    private var _viewState = MutableStateFlow(ViewState())
    var viewState: StateFlow<ViewState> = _viewState

    var _effectChannel= Channel<SnackBarViewEffect>()
    var effectFlow: Flow<SnackBarViewEffect> =_effectChannel.receiveAsFlow()

    fun handleIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.SetName -> {
                setName(intent.name)
            }

            is ViewIntent.SetEmail -> {
                setEmail(intent.email)
            }

            is ViewIntent.SetPhone -> {
                setPhone(intent.phone)
            }

            is ViewIntent.SetAddress -> {
                setAddress(intent.address)
            }

            is ViewIntent.SetIndex -> {
                setIndex(intent.index)
            }

            is ViewIntent.SetNameError -> {
                setNameError(intent.isError)
            }

            is ViewIntent.SetPhoneError -> {
                setPhoneError(intent.isError)
            }

            is ViewIntent.SetAddressError -> {
                setAddressError(intent.isError)
            }

            is ViewIntent.SetEmailError -> {
                setEmailError(intent.isError)
            }

            is ViewIntent.SetBottomAddSheed -> {
                setAddBottomVisible(intent.visibile)
            }

            is ViewIntent.SetBottomUpdateSheed -> {
                setUpdateBottomVisible(intent.visibile)
            }

            is ViewIntent.AddContact -> {
                addContact(intent.contact)
            }

            is ViewIntent.DeleteContact -> {
                deleteContact(intent.index)
            }

            is ViewIntent.UpdateContact -> {
                updateContact(intent.index, intent.newContact)
            }
        }
    }


    //    //setter
    private fun setName(name: String) {
        _viewState.value = _viewState.value.copy(name = name)
    }

    private fun setPhone(phone: String) {
        _viewState.value = _viewState.value.copy(phone = phone)
    }

    private fun setAddress(address: String) {
        _viewState.value = _viewState.value.copy(address = address)
    }

    private fun setEmail(email: String) {
        _viewState.value = _viewState.value.copy(email = email)
    }

    private fun setNameError(hasError: Boolean) {
        _viewState.value = _viewState.value.copy(nameError = hasError)
    }

    private fun setPhoneError(hasError: Boolean) {
        _viewState.value = _viewState.value.copy(phoneError = hasError)
    }

    private fun setAddressError(hasError: Boolean) {
        _viewState.value = _viewState.value.copy(addressError = hasError)
    }

    private fun setEmailError(hasError: Boolean) {
        _viewState.value = _viewState.value.copy(emailError = hasError)
    }

    private fun setSearch(search: String) {
        _viewState.value = _viewState.value.copy(search = search)
    }

    private fun setAddBottomVisible(isShow: Boolean) {
        _viewState.value = _viewState.value.copy(isAddBottomVisible = isShow)
    }

    private fun setUpdateBottomVisible(isShow: Boolean) {
        _viewState.value = _viewState.value.copy(isUdateBottomVisible = isShow)
    }

    private fun setIndex(index: Int) {
        _viewState.value = _viewState.value.copy(index = index)
    }

    private fun addContact(contact: Contact) {
//        _uiState.value = UIState.Loading
        _viewState.value = _viewState.value.copy(uiState = UIState.Loading)

        try {
            viewModelScope.launch {
                var newList = contactRepository.addContact(contact)
                _viewState.value = _viewState.value.copy(
                    contactList = newList,
                    uiState = UIState.Success(_viewState.value.contactList, "success")
                )

            }
        } catch (e: Exception) {
//            _uiState.value = UIState.Error("Error adding contact: ${e.message}")
        }
    }

    private fun updateContact(index: Int, newContact: Contact) {
        _viewState.value = _viewState.value.copy(uiState = UIState.Loading)


        try {
            viewModelScope.launch {
                var newList = contactRepository.updateContact(index, newContact)
                _viewState.value = _viewState.value.copy(
                    contactList = newList,
                    uiState = UIState.Success(_viewState.value.contactList, "success")
                )

            }
        } catch (e: Exception) {
            _viewState.value = _viewState.value.copy(uiState = UIState.Error("Error"))
        }

    }

    private fun deleteContact(index: Int) {
//        _uiState.value = UIState.Loading
        _viewState.value = _viewState.value.copy(uiState = UIState.Loading)

        try {
            viewModelScope.launch {

                var newList = contactRepository.deleteContact(index)
                _viewState.value = _viewState.value.copy(
                    contactList = newList,
                    uiState = UIState.Success(_viewState.value.contactList, "success")
                )

            }
        } catch (e: Exception) {
//            _uiState.value = UIState.Error("Error deleting  contact: ${e.message}")
        }
    }


}