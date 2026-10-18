package com.example.parcial03.model

/**
 * Modelo de datos para las credenciales de usuario del sistema.
 *
 * @property usuario Nombre de usuario para iniciar sesión.
 * @property password Contraseña asociada a la cuenta.
 * @property nombreCompleto Nombre completo del usuario para mostrar en el saludo.
 */
data class Usuario(
    val usuario: String,
    val password: String,
    val nombreCompleto: String
)
