package com.example.parcial03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityEstudianteBinding

/**
 * Pantalla que presenta los datos personales y académicos del estudiante responsable del proyecto.
 */
class EstudianteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstudianteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstudianteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignación de acción al botón de regresar
        binding.btnRegresar.setOnClickListener {
            finish() // Retorna al menú principal
        }
    }
}
