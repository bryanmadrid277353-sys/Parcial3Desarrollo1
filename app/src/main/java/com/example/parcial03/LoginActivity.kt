package com.example.parcial03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityLoginBinding
import com.example.parcial03.repository.ZapatoRepository

/**
 * Pantalla de Inicio de Sesión que autentica al usuario mediante las credenciales definidas en código.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignación de evento de clic al botón de Iniciar Sesión
        binding.btnLogin.setOnClickListener {
            validarEIniciarSesion()
        }
    }

    /**
     * Valida que los campos no estén vacíos y comprueba si las credenciales son válidas.
     */
    private fun validarEIniciarSesion() {
        val usuario = binding.etUsuario.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        // Validación de campo usuario vacío
        if (usuario.isEmpty()) {
            binding.etUsuario.error = getString(R.string.msg_campo_requerido)
            binding.etUsuario.requestFocus()
            return
        }

        // Validación de campo contraseña vacío
        if (password.isEmpty()) {
            binding.etPassword.error = getString(R.string.msg_campo_requerido)
            binding.etPassword.requestFocus()
            return
        }

        // Verificación de credenciales en el repositorio en memoria
        val usuarioAutenticado = ZapatoRepository.validarUsuario(usuario, password)

        if (usuarioAutenticado != null) {
            // Credenciales válidas: Navegar al Menú Principal pasando el nombre del usuario mediante Intent
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("EXTRA_USUARIO", usuarioAutenticado.nombreCompleto)
            }
            startActivity(intent)
            finish() // Finaliza LoginActivity para evitar regresar con el botón atrás
        } else {
            // Credenciales inválidas: Muestra diálogo de error
            mostrarDialogoError()
        }
    }

    /**
     * Muestra un cuadro de diálogo AlertDialog cuando la autenticación falla.
     */
    private fun mostrarDialogoError() {
        AlertDialog.Builder(this)
            .setTitle("Error de Autenticación")
            .setMessage(R.string.msg_error_login)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}
