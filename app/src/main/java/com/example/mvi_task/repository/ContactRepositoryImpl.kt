package com.example.mvi_task.repository

import com.example.mvi_task.model.Contact
import kotlinx.coroutines.delay

class ContactRepositoryImpl : ContactRepository {
    private val contactList: MutableList<Contact> = mutableListOf()

    override suspend fun addContact(contact: Contact): List<Contact> {
//        Thread.sleep(2000) //pause Ui
        delay(1000)//not pause Ui
        contactList.add(contact)
        return contactList.toList()

    }

    override suspend fun getAllContact(): List<Contact> {
        delay(1000)
        return contactList.toList()
    }

    override suspend fun updateContact(index: Int, newContact: Contact): List<Contact> {
        delay(1000)
        contactList[index] = newContact
        return contactList.toList()
    }

    override suspend fun deleteContact(index: Int): List<Contact> {
        delay(1000)
        contactList.removeAt(index)
        return contactList.toList()
    }

    override suspend fun searchContact(search: String): List<Contact> {
        delay(1000)
        return contactList.filter { it.name.startsWith(search, ignoreCase = true) }
    }
}