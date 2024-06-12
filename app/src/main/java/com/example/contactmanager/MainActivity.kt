// MainActivity.kt
package com.example.contactmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.contactmanager.database.ContactDatabaseHelper
import com.example.contactmanager.model.Contact

// Clase principal de la actividad
class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: ContactDatabaseHelper // Instancia del ayudante de base de datos
    private lateinit var listViewContacts: ListView // ListView para mostrar los contactos
    private lateinit var buttonAddContact: Button // Botón para agregar contactos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Establece el diseño de la actividad

        dbHelper = ContactDatabaseHelper(this) // Inicializa el ayudante de base de datos
        listViewContacts = findViewById(R.id.listview_contacts) // Encuentra el ListView en el diseño
        buttonAddContact = findViewById(R.id.button_add_contact) // Encuentra el botón en el diseño

        buttonAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java) // Crea un Intent para iniciar la actividad AddContactActivity
            startActivity(intent) // Inicia la actividad
        }
    }

    override fun onResume() {
        super.onResume()
        loadContacts() // Carga los contactos cuando la actividad se reanuda
    }

    private fun loadContacts() {
        val contacts = dbHelper.getAllContacts() // Obtiene todos los contactos de la base de datos
        val contactNames = contacts.map { "${it.name} - ${it.phone}" } // Crea una lista de nombres y teléfonos
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactNames) // Crea un adaptador para el ListView
        listViewContacts.adapter = adapter // Establece el adaptador en el ListView
    }
}