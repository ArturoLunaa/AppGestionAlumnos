package com.example.examensegundoparcial

import Modelo.UsuariosDataSource
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.detalle_usuario.*

class DetalleUsuario: AppCompatActivity() {

    private lateinit var datasource:UsuariosDataSource

    private var id = 0
    private var nombre = ""
    private var usuario = ""
    private var escolaridad = ""
    private var edo_civil = ""
    private var habilidades = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_usuario)

        datasource = UsuariosDataSource(this)

        val b = this.intent.extras
        if(b != null)
        {
            id = b.getInt("id")
            nombre = b.getString("nombre").toString()
            usuario = b.getString("usuario").toString()
            escolaridad = b.getString("escolaridad").toString()
            edo_civil = b.getString("edo_civil").toString()
            habilidades = b.getString("habilidades").toString()

            txtNombre.setText("Nombre: " + nombre)
            txtUsuario.setText("Usuario: " + usuario)
            txtEsco.setText("Escolaridad: " + escolaridad)
            txtEdoCivil.setText("Estado civil: " + edo_civil)
            txtHabilities.setText(habilidades)
        }
    }

    fun eliminar(view: View){
        if(datasource.eliminarUsuario(id)){
            val toast = Toast.makeText(applicationContext, "Se eliminó correctamente", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, ListaUsuarios::class.java)
            startActivity(intent)

        }
    }


}