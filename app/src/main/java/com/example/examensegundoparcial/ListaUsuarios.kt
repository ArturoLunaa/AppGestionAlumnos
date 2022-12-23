package com.example.examensegundoparcial

import Entidades.Usuario
import Modelo.UsuariosDataSource
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.lista_usuarios.view.*
import kotlinx.android.synthetic.main.lista_view.*

class ListaUsuarios : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_view)

        LlenarInformacion()
    }

    fun LlenarInformacion() {
        val datasource = UsuariosDataSource(this)
        val registros = ArrayList<Usuario>()
        // SE ESTA LLAMANDO AL METODO PARA TRAERNOS TODA LA INFORMACIÓN DE LA BD
        val cursor = datasource.consultarUsuarios()

        while (cursor.moveToNext()) {
            val columnas = Usuario(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
            )
            registros.add(columnas)
        }


        //ArrayAdapter‹Stringy adapter - new ArrayAdapter‹String› (this, android.R.layout. simple list_
        val adaptador = AdaptadorUsuarios(this, registros)

        listausuarios.adapter = adaptador

        listausuarios.setOnItemClickListener { parent, view, position, id ->

            val item = parent.getItemAtPosition(position) as Usuario

            val intent = Intent(this, DetalleUsuario::class.java)
            intent.putExtra("id", item.id_usuario)
            intent.putExtra("nombre", item.nombre)
            intent.putExtra("usuario", item.usuario)
            intent.putExtra("contrasenia", item.password)
            intent.putExtra("escolaridad", item.escolaridad)
            intent.putExtra("edo_civil", item.edo_civil)
            intent.putExtra("habilidades", item.habilidades)
            startActivity(intent)
        }
    }

    fun VisualizarUsuario(view: View) {
        val intent = Intent(this, DetalleUsuario::class.java)
        startActivity(intent)
    }


    override fun onPause() {
        super.onPause()
        this.LlenarInformacion()
    }

    override fun onResume() {
        super.onResume()
        this.LlenarInformacion()
    }

    internal class AdaptadorUsuarios(context: Context, datos: List<Usuario>) :
        ArrayAdapter<Usuario>(context, R.layout.lista_usuarios, datos) {

        var _datos: List<Usuario>

        init {
            _datos = datos
        }

        @NonNull
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.lista_usuarios, parent, false)

            val currentEntity = getItem(position)

            if (currentEntity != null) {
                inflater.LblTitulo.text = currentEntity.nombre
                inflater.LblEscolaridad.text = currentEntity.escolaridad
            }

            return inflater
        }


    }
}