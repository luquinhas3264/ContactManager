// AddContactActivity.kt
package com.example.contactmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.contactmanager.database.ContactDatabaseHelper
import com.example.contactmanager.model.Contact

// Actividad para agregar un nuevo contacto
class AddContactActivity : AppCompatActivity() {

    private lateinit var dbHelper: ContactDatabaseHelper // Instancia del ayudante de base de datos
    private lateinit var editTextName: EditText // Campo de texto para el nombre
    private lateinit var editTextPhone: EditText // Campo de texto para el teléfono
    private lateinit var buttonSave: Button // Botón para guardar el contacto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact) // Establece el diseño de la actividad

        dbHelper = ContactDatabaseHelper(this) // Inicializa el ayudante de base de datos
        editTextName = findViewById(R.id.edittext_name) // Encuentra el campo de texto de nombre en el diseño
        editTextPhone = findViewById(R.id.edittext_phone) // Encuentra el campo de texto de teléfono en el diseño
        buttonSave = findViewById(R.id.button_save) // Encuentra el botón de guardar en el diseño

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString() // Obtiene el nombre ingresado
            val phone = editTextPhone.text.toString() // Obtiene el teléfono ingresado
            val contact = Contact(0, name, phone) // Crea un nuevo contacto (el ID se auto-generará)
            dbHelper.addContact(contact) // Agrega el contacto a la base de datos
            finish() // Finaliza la actividad
        }
    }
}