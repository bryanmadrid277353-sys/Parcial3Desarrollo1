package com.example.parcial03.repository

import com.example.parcial03.model.Usuario
import com.example.parcial03.model.Zapato

/**
 * Repositorio en memoria que actúa como fuente de datos centralizada (emulación de base de datos).
 * Mantiene la lista activa de zapatos deportivos y la lista de usuarios autorizados.
 */
object ZapatoRepository {

    // Lista de al menos 5 usuarios autorizados para iniciar sesión
    private val listaUsuarios = listOf(
        Usuario("admin", "admin123", "Administrador del Sistema"),
        Usuario("bmadrid", "bryan2026", "Bryan Antonio Madrid Cortez"),
        Usuario("docente", "etps12026", "Catedrático ETPS1"),
        Usuario("estudiante", "pass1234", "Estudiante UFG"),
        Usuario("supervisor", "super123", "Supervisor de Inventario")
    )

    // Lista en memoria precargada con 5 zapatos de ejemplo
    private val listaZapatos: MutableList<Zapato> = mutableListOf(
        Zapato("ZAP-001", "Nike", "Air Zoom Pegasus 40", 41.5, 120.00, 15, 10.0),
        Zapato("ZAP-002", "Adidas", "Ultraboost Light", 42.5, 145.00, 8, 15.0),
        Zapato("ZAP-003", "Puma", "Velocity Nitro 2", 40.0, 95.00, 20, 5.0),
        Zapato("ZAP-004", "Asics", "Gel-Nimbus 25", 43.0, 160.00, 10, 0.0),
        Zapato("ZAP-005", "Under Armour", "HOVR Phantom 3", 42.0, 110.00, 12, 20.0)
    )

    /**
     * Valida las credenciales de un usuario contra la lista en memoria.
     * @return El objeto [Usuario] si coincide, o `null` si no es válido.
     */
    fun validarUsuario(user: String, pass: String): Usuario? {
        return listaUsuarios.find { it.usuario.equals(user, ignoreCase = true) && it.password == pass }
    }

    /**
     * Devuelve una copia de la lista completa de zapatos en inventario.
     */
    fun obtenerTodos(): List<Zapato> {
        return listaZapatos.toList()
    }

    /**
     * Busca un zapato por su código único.
     * @return El [Zapato] encontrado o `null` si no existe.
     */
    fun buscarPorCodigo(codigo: String): Zapato? {
        return listaZapatos.find { it.codigo.equals(codigo, ignoreCase = true) }
    }

    /**
     * Inserta un nuevo zapato en el inventario previa verificación de código único.
     * @return `true` si se insertó con éxito, `false` si el código ya existía.
     */
    fun insertar(zapato: Zapato): Boolean {
        if (buscarPorCodigo(zapato.codigo) != null) {
            return false // Código duplicado
        }
        return listaZapatos.add(zapato)
    }

    /**
     * Actualiza los datos de un zapato existente en el inventario.
     * @return `true` si se encontró y actualizó, `false` de lo contrario.
     */
    fun actualizar(zapatoActualizado: Zapato): Boolean {
        val indice = listaZapatos.indexOfFirst { it.codigo.equals(zapatoActualizado.codigo, ignoreCase = true) }
        if (indice != -1) {
            listaZapatos[indice] = zapatoActualizado
            return true
        }
        return false
    }

    /**
     * Elimina un zapato del inventario buscando por su código.
     * @return `true` si se eliminó con éxito, `false` si no existía.
     */
    fun eliminar(codigo: String): Boolean {
        val zapato = buscarPorCodigo(codigo)
        return if (zapato != null) {
            listaZapatos.remove(zapato)
        } else {
            false
        }
    }

    /**
     * Calcula la cantidad total de pares de zapatos almacenados en el inventario.
     * @return Suma total de las cantidades de todos los registros.
     */
    fun obtenerTotalPares(): Int {
        return listaZapatos.sumOf { it.cantidad }
    }

    /**
     * Calcula el valor monetario total del inventario acumulando el valor total de cada zapato.
     * @return Suma de los totales a pagar de todos los productos en inventario.
     */
    fun obtenerValorTotalInventario(): Double {
        return listaZapatos.sumOf { it.calcularTotal() }
    }
}
