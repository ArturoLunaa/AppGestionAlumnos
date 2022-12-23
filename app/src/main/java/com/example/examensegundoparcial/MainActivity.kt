package com.example.examensegundoparcial

import Entidades.Usuario
import Modelo.BdOpenHelper
import Modelo.UsuariosDataSource
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var datasource:UsuariosDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datasource = UsuariosDataSource(this)
        logica()
        btnRegistrar.setOnClickListener{
            val intent = Intent(this, RegistrarUsuario::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun logica() {

        btnIngresar.setOnClickListener {
            var user = txtUser.text.toString()
            var passwd = txtInp_password.text.toString()
            var checkRegister:Boolean
            checkRegister = datasource.checkUserAndPassword(user,passwd)
            if((TextUtils.isEmpty(user) || TextUtils.isEmpty(passwd)) || (TextUtils.isEmpty(user) && TextUtils.isEmpty(passwd))){
                val toast = Toast.makeText(applicationContext, "Ingrese todos los campos", Toast.LENGTH_SHORT)
                toast.show()
            }else{
                if(checkRegister){
                    val toast = Toast.makeText(applicationContext, "Acceso correcto", Toast.LENGTH_SHORT)
                    toast.show()
                    val intent = Intent(this, ListaUsuarios::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val toast = Toast.makeText(applicationContext, "Usuario o contraseña incorrectos, intente de nuevo o regístrese", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

        }
    }
}