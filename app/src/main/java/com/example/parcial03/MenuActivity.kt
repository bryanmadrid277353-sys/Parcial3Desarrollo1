package com.example.parcial03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityMenuBinding

/**
 * Pantalla de Menú Principal que sirve como centro de navegación para las operaciones del inventario.
 */
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recepción del usuario desde el Intent de LoginActivity
        val nombreUsuario = intent.getStringExtra("EXTRA_USUARIO") ?: "Usuario"
        binding.tvBienvenida.text = getString(R.string.label_usuario_sesion, nombreUsuario)

        // Configuración de eventos de navegación para cada botón
        binding.btnInsertar.setOnClickListener {
            startActivity(Intent(this, InsertarActivity::class.java))
        }

        binding.btnListar.setOnClickListener {
            startActivity(Intent(this, ListarActivity::class.java))
        }

        binding.btnActualizar.setOnClickListener {
            startActivity(Intent(this, ActualizarActivity::class.java))
        }

        binding.btnEliminar.setOnClickListener {
            startActivity(Intent(this, EliminarActivity::class.java))
        }

        binding.btnEquipo.setOnClickListener {
            startActivity(Intent(this, EquipoActivity::class.java))
        }

        binding.btnEstudiante.setOnClickListener {
            startActivity(Intent(this, EstudianteActivity::class.java))
        }

        binding.btnCerrarSesion.setOnClickListener {
            confirmarCierreSesion()
        }
    }

    /**
     * Muestra diálogo de confirmación antes de cerrar la sesión y regresar a LoginActivity.
     */
    private fun confirmarCierreSesion() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_cerrar_sesion_titulo)
            .setMessage(R.string.dialog_cerrar_sesion_msg)
            .setPositiveButton(R.string.btn_confirmar) { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton(R.string.btn_cancelar, null)
            .show()
    }
}
