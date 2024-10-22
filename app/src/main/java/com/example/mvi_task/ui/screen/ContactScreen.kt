package com.example.mvi_task.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvi_task.model.Contact
import com.example.mvi_task.ui.layout.ContactCard
import com.example.mvi_task.uimodel.SnackBarViewEffect
import com.example.mvi_task.uimodel.ViewIntent
import com.example.mvvm_contactapp_t24.ui.layout.UIState
import com.example.mvvm_contactapp_t24.utils.isEmailValid
import com.example.mvvm_contactapp_t24.utils.isNameValid
import com.example.mvvm_contactapp_t24.utils.isPhoneValid
import com.example.mvvm_contactapp_t24.viewModel.ContactViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen() {
    var context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    //viewModel
    val contactViewModel: ContactViewModel = viewModel()

    val viewState by contactViewModel.viewState.collectAsState()

    LaunchedEffect(contactViewModel) {
        contactViewModel.effectFlow.collect{effect->
                when(effect){
                    is SnackBarViewEffect.ShowSnackbarView->{
                        val res=snackbarHostState.showSnackbar(
                            message = effect.message,
                            withDismissAction = true,
                            actionLabel = effect.actionLabel
                        )
                        if(res ==SnackbarResult.ActionPerformed&&effect.actionLabel=="undo"){
                            //handleUndo Intent
                        }
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Magenta)

            )

        },
        snackbarHost = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { contactViewModel.handleIntent(ViewIntent.SetBottomAddSheed(true)) },
                containerColor = Color.Magenta
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = null) }
        }

    ) { padding ->


        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp)
        ) {
            //start
            when (viewState.uiState) {
                is UIState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF6200EE))
                        }
                    }

                }

                is UIState.Success -> {
                    itemsIndexed(
                        viewState.contactList,
                        key = { index, _ -> index }) { index, contact ->
                        ContactCard(contact) {
                            contactViewModel.handleIntent(ViewIntent.SetBottomUpdateSheed(visibile = true))
                            contactViewModel.handleIntent(ViewIntent.SetIndex(index = index))
                            // Initialize text fields with the selected contact's data only when the card is clicked
//
                            contactViewModel.handleIntent(ViewIntent.SetName(viewState.contactList[contactViewModel.viewState.value.index].name))
                            contactViewModel.handleIntent(ViewIntent.SetPhone(viewState.contactList[contactViewModel.viewState.value.index].phone))
                            contactViewModel.handleIntent(ViewIntent.SetAddress(viewState.contactList[contactViewModel.viewState.value.index].address))
                            contactViewModel.handleIntent(ViewIntent.SetEmail(viewState.contactList[contactViewModel.viewState.value.index].email))
                        }
                    }
                }

                is UIState.Error -> {
                }
            }

        }



        if (viewState.isAddBottomVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    contactViewModel.handleIntent(ViewIntent.SetBottomAddSheed(false))
                },
                content = {
                    Column {
                        Text(
                            "Add Contact",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        OutlinedTextField(
                            value = viewState.name,
                            onValueChange = {
                                contactViewModel.handleIntent(ViewIntent.SetName(it)) //it
                                if (viewState.name.isNameValid()) contactViewModel.handleIntent(
                                    ViewIntent.SetNameError(false)
                                ) else contactViewModel.handleIntent(ViewIntent.SetNameError(true))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("name") },
                            isError = viewState.nameError
                        )
                        OutlinedTextField(
                            value = viewState.phone,
                            onValueChange = {
                                contactViewModel.handleIntent(ViewIntent.SetPhone(it))
                                if (viewState.phone.isPhoneValid()) contactViewModel.handleIntent(
                                    ViewIntent.SetPhoneError(false)
                                ) else contactViewModel.handleIntent(ViewIntent.SetPhoneError(true))

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("phone") },
                            isError = viewState.phoneError

                        )
                        OutlinedTextField(
                            value = viewState.address,
                            onValueChange = {
                                contactViewModel.handleIntent(ViewIntent.SetAddress(it))

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("address") },
                            isError = viewState.addressError

                        )
                        OutlinedTextField(
                            value = viewState.email,
                            onValueChange = {
                                contactViewModel.handleIntent(ViewIntent.SetEmail(it))
                                if (viewState.email.isEmailValid()) contactViewModel.handleIntent(
                                    ViewIntent.SetEmailError(false)
                                ) else contactViewModel.handleIntent(ViewIntent.SetEmailError(true))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("email") },
                            isError = viewState.emailError

                        )
                        Row(horizontalArrangement = Arrangement.SpaceAround) {
                            Button(
                                onClick = {
                                    contactViewModel.handleIntent(
                                        ViewIntent.AddContact(
                                            Contact(
                                                viewState.name,
                                                viewState.phone,
                                                viewState.address,
                                                viewState.email,
                                                "",
                                                ""
                                            )
                                        )
                                    )
                                    contactViewModel.handleIntent(ViewIntent.SetBottomAddSheed(false))                                    //empty inputData in viewModel
                                    contactViewModel.handleIntent(ViewIntent.SetName(""))
                                    contactViewModel.handleIntent(ViewIntent.SetAddress(""))
                                    contactViewModel.handleIntent(ViewIntent.SetPhone(""))
                                    contactViewModel.handleIntent(ViewIntent.SetEmail(""))

                                }
                            ) {
                                Text("Confirm")
                            }
                            Button(onClick = {
                                contactViewModel.handleIntent(
                                    ViewIntent.SetBottomAddSheed(
                                        false
                                    )
                                )
                            }) {
                                Text(
                                    "Cancel"
                                )
                            }
                        }
                    }
                }
            )
        }

        if (viewState.isUdateBottomVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    contactViewModel.handleIntent(ViewIntent.SetBottomUpdateSheed(visibile = false))
                },
                content = {
                    Column {
                        Text(
                            "Update Contact",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )

                        OutlinedTextField(
                            value = viewState.name,
                            onValueChange = { contactViewModel.handleIntent(ViewIntent.SetName(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("name") }
                        )
                        OutlinedTextField(
                            value = viewState.phone,
                            onValueChange = { contactViewModel.handleIntent(ViewIntent.SetPhone(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("phone") }

                        )
                        OutlinedTextField(
                            value = viewState.address,
                            onValueChange = { contactViewModel.handleIntent(ViewIntent.SetAddress(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("address") }
                        )
                        OutlinedTextField(
                            value = viewState.email,
                            onValueChange = { contactViewModel.handleIntent(ViewIntent.SetEmail(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .wrapContentHeight(),
                            label = { Text("email") }
                        )
                        Row(horizontalArrangement = Arrangement.SpaceAround) {
                            Button(onClick = {
                                contactViewModel.handleIntent(
                                    ViewIntent.UpdateContact(
//                                    index = contactViewModel.index.value,
                                        index = viewState.index,
                                        newContact = Contact(
                                            viewState.name,
                                            viewState.phone,
                                            viewState.address,
                                            viewState.email,
                                            "",
                                            ""
                                        )
                                    )
                                )


                                contactViewModel.handleIntent(
                                    ViewIntent.SetBottomUpdateSheed(
                                        visibile = false
                                    )
                                )

                            }) {
                                Text("Confirm")
                            }
                            Button(onClick = {
                                contactViewModel.handleIntent(
                                    ViewIntent.SetBottomUpdateSheed(
                                        visibile = false
                                    )
                                )
                                //empty inputData in viewModel
                                contactViewModel.handleIntent(ViewIntent.SetName(""))
                                contactViewModel.handleIntent(ViewIntent.SetAddress(""))
                                contactViewModel.handleIntent(ViewIntent.SetPhone(""))
                                contactViewModel.handleIntent(ViewIntent.SetEmail(""))
                            }) {
                                Text(
                                    "Cancel"
                                )
                            }
                            IconButton(onClick = {
                                contactViewModel.handleIntent(ViewIntent.DeleteContact(index = viewState.index))
                                contactViewModel.handleIntent(
                                    ViewIntent.SetBottomUpdateSheed(
                                        visibile = false
                                    )
                                )
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    }

                }


            )
        }
    }
}

@Preview
@Composable
fun previewContact() {
    ContactScreen()
}