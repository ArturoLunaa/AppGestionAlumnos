package Modelo

import Entidades.Usuario
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import java.lang.Exception

class UsuariosDataSource(context: Context) {
    private val openHelper:BdOpenHelper = BdOpenHelper(context)
    private val database: SQLiteDatabase
    object ColumnEventos {
        var ID_USUARIO = BaseColumns._ID
        var NOMBRE = "nombre"
        var USUARIO = "usuario"
        var PASSWORD = "contrasenia"
        var ESCOLARIDAD = "escolaridad"
        var EDO_CIVIL = "edo_civil"
        var HABILIDADES = "habilidades"

    }

    init{
        database = openHelper.writableDatabase
    }

    fun consultarUsuarios(): Cursor {
        return database.rawQuery("select _id,nombre,usuario,contrasenia,escolaridad, edo_civil, habilidades from $USUARIOS_TABLE_NAME", null)
    }

    fun checkUser(username:String): Boolean{
        val cursor: Cursor = database.rawQuery("select * from $USUARIOS_TABLE_NAME where usuario=?", arrayOf(username))
        return cursor.count > 0
    }

    fun checkUserAndPassword(username:String, password:String): Boolean{
        val cursor: Cursor = database.rawQuery("select * from $USUARIOS_TABLE_NAME where usuario=? and contrasenia=?", arrayOf(username, password))
        return cursor.count > 0
    }


    fun guardarUsuario(nombre: String, usuario:String, contrasenia:String, escolaridad:String, edo_civil:String, habilidades:String){
        val values = ContentValues()
        values.put(ColumnEventos.NOMBRE, nombre)
        values.put(ColumnEventos.USUARIO, usuario)
        values.put(ColumnEventos.PASSWORD, contrasenia)
        values.put(ColumnEventos.ESCOLARIDAD, escolaridad)
        values.put(ColumnEventos.EDO_CIVIL, edo_civil)
        values.put(ColumnEventos.HABILIDADES, habilidades)
        database.insert(USUARIOS_TABLE_NAME, null, values)
    }


    fun eliminarUsuario(IdUsuario:Int): Boolean {
        val whereArgs = arrayOf("" + IdUsuario)
        try
        {
            database.delete(USUARIOS_TABLE_NAME,"_id=?", whereArgs)
            return true
        }
        catch (ex: Exception){
            return false
        }
        finally {
            database.close()
        }
    }

    companion object {
        val USUARIOS_TABLE_NAME = "Usuarios"
        val STRING_TYPE = "text"
        val INT_TYPE = "integer"
        val CREATE_USUARIOS_SCRIPT = (
                "create table " + USUARIOS_TABLE_NAME + "(" +
                        ColumnEventos.ID_USUARIO + " " + INT_TYPE + " primary key autoincrement," +
                        ColumnEventos.NOMBRE + " " + STRING_TYPE + " not null," +
                        ColumnEventos.USUARIO + " " + STRING_TYPE + " not null," +
                        ColumnEventos.PASSWORD + " " + STRING_TYPE + " not null," +
                        ColumnEventos.ESCOLARIDAD + " " + STRING_TYPE + " not null," +
                        ColumnEventos.EDO_CIVIL + " " + STRING_TYPE + " not null," +
                        ColumnEventos.HABILIDADES + " " + STRING_TYPE + " not null)")
        val INSERT_USUARIOS_SCRIPT = (
                "insert into " + USUARIOS_TABLE_NAME + " values " +
                        "(null, 'Arturo','arturo10','123','Licenciatura','Soltero','Java')")

    }
}