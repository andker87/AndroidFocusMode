package com.androidfocusmode.app.service.contacts

import android.Manifest
import android.content.Context
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    /**
     * Get list of favorite/starred contacts
     */
    fun getFavoriteContacts(): List<ContactInfo> {
        if (!hasReadContactsPermission()) return emptyList()

        val favorites = mutableListOf<ContactInfo>()
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.STARRED
        )
        val selection = "${ContactsContract.Contacts.STARRED} = ?"
        val selectionArgs = arrayOf("1")

        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                
                val phones = getPhoneNumbersForContact(id)
                favorites.add(ContactInfo(name, phones))
            }
        }

        return favorites
    }

    /**
     * Get all phone numbers for a contact
     */
    private fun getPhoneNumbersForContact(contactId: String): List<String> {
        if (!hasReadContactsPermission()) return emptyList()

        val phones = mutableListOf<String>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
        val selectionArgs = arrayOf(contactId)

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val number = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                )
                phones.add(normalizePhoneNumber(number))
            }
        }

        return phones
    }

    /**
     * Check if a phone number is from a favorite contact
     */
    fun isFromFavorite(phoneNumber: String): Boolean {
        val normalizedInput = normalizePhoneNumber(phoneNumber)
        return getFavoriteContacts().any { contact ->
            contact.phoneNumbers.any { phone ->
                normalizePhoneNumber(phone) == normalizedInput
            }
        }
    }

    /**
     * Normalize phone number to compare
     */
    private fun normalizePhoneNumber(phone: String): String {
        return phone.filter { it.isDigit() }.takeLast(10)
    }

    private fun hasReadContactsPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
}

data class ContactInfo(
    val name: String,
    val phoneNumbers: List<String>
)
