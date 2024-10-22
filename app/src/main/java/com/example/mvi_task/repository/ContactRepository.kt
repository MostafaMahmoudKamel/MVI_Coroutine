package com.example.mvi_task.repository

import com.example.mvi_task.model.Contact

interface ContactRepository {
    suspend fun addContact(contact: Contact): List<Contact>
    suspend fun getAllContact(): List<Contact>
    suspend fun updateContact(index: Int, newContact: Contact): List<Contact>
    suspend fun deleteContact(index: Int): List<Contact>
    suspend fun searchContact(search: String): List<Contact>
}