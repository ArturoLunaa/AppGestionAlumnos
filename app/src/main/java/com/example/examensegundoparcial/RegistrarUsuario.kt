package com.example.examensegundoparcial

import Modelo.UsuariosDataSource
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.registrar_view.*
import java.util.*

class RegistrarUsuario: AppCompatActivity() {

    private lateinit var datasource:UsuariosDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_view)

        datasource = UsuariosDataSource(this)
    }

    fun GuardarUsuario(view: View){
        var edoCivil = obtenerLetraEdoCivil()
        var habilities = obtenerHabilidades()

        if(validationEmptyFields() || (!justLetters(txtEscolaridad.text.toString()) && !justLetters(txtName.text.toString())) || (!justLetters(txtEscolaridad.text.toString()) || !justLetters(txtName.text.toString()))){
            val toast = Toast.makeText(applicationContext, "Es necesario ingresar todos los campos con el tipo de dato correcto", Toast.LENGTH_SHORT)
            toast.show()
        }else{
            datasource.guardarUsuario(txtName.text.toString(), txtUser.text.toString(),txtPassword.text.toString(), txtEscolaridad.text.toString(), edoCivil, habilities)
            val toast = Toast.makeText(applicationContext, "Se registró correctamente al usuario", Toast.LENGTH_SHORT)
            toast.show()
            //Mostrar interfaz de Lista
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    fun validationEmptyFields() : Boolean{
        var flag = false
        var rbCheck = false
        var cbCheck = false
        if (!rbCasado.isChecked && !rbSoltero.isChecked){
        rbCheck = true
        }
        if(!cbJava.isChecked && !cbCsh.isChecked && !cbCpp.isChecked){
            cbCheck = true
        }
        if((TextUtils.isEmpty(txtName.text.toString()) || TextUtils.isEmpty(txtUser.text.toString()) || TextUtils.isEmpty(txtPassword.text.toString()) || TextUtils.isEmpty(txtEscolaridad.text.toString()) || rbCheck || cbCheck) ||
            (TextUtils.isEmpty(txtName.text.toString()) && TextUtils.isEmpty(txtUser.text.toString()) && TextUtils.isEmpty(txtPassword.text.toString()) && TextUtils.isEmpty(txtEscolaridad.text.toString()) && rbCheck && cbCheck)) {
            flag = true
        }
        return flag
    }

    fun justLetters(cadena: String): Boolean {
        for (i in 0 until cadena.length) {
            val caracter = cadena.uppercase(Locale.getDefault())[i]
            val valorASCII = caracter.code
            if (valorASCII != 165 && (valorASCII < 65 || valorASCII > 90)){
                return false
            }

        }
        return true
    }

    fun obtenerHabilidades() : String {
        var selectedOptions = ""
        if(cbJava.isChecked && cbCpp.isChecked && cbCsh.isChecked){
            selectedOptions = "Java, C# y C++"
        }
        if(cbJava.isChecked && cbCpp.isChecked && !cbCsh.isChecked){
            selectedOptions = "Java y C++"
        }
        if(cbJava.isChecked && cbCsh.isChecked && !cbCpp.isChecked){
            selectedOptions = "Java y C#"
        }
        if(cbCpp.isChecked && cbCsh.isChecked && !cbJava.isChecked){
            selectedOptions = "C# y C++"
        }
        if(cbJava.isChecked && !cbCpp.isChecked && !cbCsh.isChecked){
            selectedOptions = "Java"
        }
        if(cbCpp.isChecked && !cbJava.isChecked && !cbCsh.isChecked){
            selectedOptions = "C++"
        }
        if(cbCsh.isChecked && !cbCpp.isChecked && !cbJava.isChecked){
            selectedOptions = "C#"
        }

        return selectedOptions
    }

    fun obtenerLetraEdoCivil() : String {
        var status: String
        if (rbCasado.isChecked) {
            status = rbCasado.text.toString()
        } else {
            status = rbSoltero.text.toString()
        }
        return status
    }

}