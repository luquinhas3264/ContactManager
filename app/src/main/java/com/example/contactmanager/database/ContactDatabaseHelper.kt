package com.example.contactmanager.database

// Importa clases necesarias para trabajar con bases de datos y contextos en Android
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.contactmanager.model.Contact

// Define una clase que extiende SQLiteOpenHelper para manejar la creación y actualización de la base de datos
class ContactDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion para constantes estáticas
    companion object {
        private const val DATABASE_NAME = "contacts.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos
        private const val TABLE_CONTACTS = "contacts" // Nombre de la tabla
        private const val COLUMN_ID = "id" // Nombre de la columna ID
        private const val COLUMN_NAME = "name" // Nombre de la columna para el nombre
        private const val COLUMN_PHONE = "phone" // Nombre de la columna para el teléfono

        // Sentencia SQL para crear la tabla de contactos
        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_CONTACTS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_PHONE TEXT)"
    }

    // Método que se llama al crear la base de datos
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE) // Ejecuta la sentencia SQL para crear la tabla
    }

    // Método que se llama cuando la base de datos necesita ser actualizada
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS") // Elimina la tabla existente
        onCreate(db) // Llama a onCreate para recrear la tabla
    }

    // Función para agregar un contacto a la base de datos
    fun addContact(contact: Contact): Long {
        val db = this.writableDatabase // Obtiene una instancia de la base de datos en modo escritura
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name) // Inserta el nombre del contacto en ContentValues
            put(COLUMN_PHONE, contact.phone) // Inserta el teléfono del contacto en ContentValues
        }
        return db.insert(TABLE_CONTACTS, null, values) // Inserta el contacto en la tabla y retorna el ID de la fila insertada
    }

    // Función para obtener todos los contactos de la base de datos
    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>() // Crea una lista mutable para almacenar los contactos
        val db = this.readableDatabase // Obtiene una instancia de la base de datos en modo lectura
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACTS", null) // Ejecuta una consulta SQL para obtener todos los contactos
        if (cursor.moveToFirst()) { // Mueve el cursor al primer resultado
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)) // Obtiene el ID del contacto
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)) // Obtiene el nombre del contacto
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)) // Obtiene el teléfono del contacto
                contacts.add(Contact(id, name, phone)) // Añade el contacto a la lista
            } while (cursor.moveToNext()) // Continúa moviéndose al siguiente resultado hasta que no haya más
        }
        cursor.close() // Cierra el cursor
        return contacts // Retorna la lista de contactos
    }
}