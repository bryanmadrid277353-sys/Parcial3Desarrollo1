package com.example.parcial03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityEquipoBinding

/**
 * Pantalla que despliega la información del equipo de trabajo del proyecto universitario.
 */
class EquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEquipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del botón de regresar al menú principal
        binding.btnRegresar.setOnClickListener {
            finish() // Cierra la pantalla actual y regresa a la anterior en el backstack
        }
    }
}
