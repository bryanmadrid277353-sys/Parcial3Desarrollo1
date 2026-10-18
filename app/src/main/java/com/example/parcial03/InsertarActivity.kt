package com.example.parcial03

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityInsertarBinding
import com.example.parcial03.model.Zapato
import com.example.parcial03.repository.ZapatoRepository

/**
 * Formulario para registrar un nuevo zapato deportivo en el inventario.
 */
class InsertarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignación de oyentes de clic para los botones del formulario
        binding.btnGuardar.setOnClickListener {
            guardarZapato()
        }

        binding.btnLimpiar.setOnClickListener {
            limpiarCampos()
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    /**
     * Procesa la captura, validación y registro del nuevo producto.
     */
    private fun guardarZapato() {
        val codigo = binding.etCodigo.text.toString().trim()
        val marca = binding.etMarca.text.toString().trim()
        val modelo = binding.etModelo.text.toString().trim()
        val tallaStr = binding.etTalla.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()
        val cantidadStr = binding.etCantidad.text.toString().trim()
        val descuentoStr = binding.etDescuento.text.toString().trim()

        // 1. Validación de campos obligatorios no vacíos
        if (codigo.isEmpty()) {
            binding.etCodigo.error = getString(R.string.msg_campo_requerido)
            binding.etCodigo.requestFocus()
            return
        }

        if (marca.isEmpty()) {
            binding.etMarca.error = getString(R.string.msg_campo_requerido)
            binding.etMarca.requestFocus()
            return
        }

        if (modelo.isEmpty()) {
            binding.etModelo.error = getString(R.string.msg_campo_requerido)
            binding.etModelo.requestFocus()
            return
        }

        if (tallaStr.isEmpty()) {
            binding.etTalla.error = getString(R.string.msg_campo_requerido)
            binding.etTalla.requestFocus()
            return
        }

        if (precioStr.isEmpty()) {
            binding.etPrecio.error = getString(R.string.msg_campo_requerido)
            binding.etPrecio.requestFocus()
            return
        }

        if (cantidadStr.isEmpty()) {
            binding.etCantidad.error = getString(R.string.msg_campo_requerido)
            binding.etCantidad.requestFocus()
            return
        }

        if (descuentoStr.isEmpty()) {
            binding.etDescuento.error = getString(R.string.msg_campo_requerido)
            binding.etDescuento.requestFocus()
            return
        }

        // 2. Validación de código único en el repositorio
        if (ZapatoRepository.buscarPorCodigo(codigo) != null) {
            binding.etCodigo.error = getString(R.string.msg_codigo_existe)
            binding.etCodigo.requestFocus()
            Toast.makeText(this, R.string.msg_codigo_existe, Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Conversión numérica y validaciones de rango
        val talla = tallaStr.toDoubleOrNull()
        if (talla == null || talla < 20.0 || talla > 50.0) {
            binding.etTalla.error = getString(R.string.error_talla_invalida)
            binding.etTalla.requestFocus()
            return
        }

        val precio = precioStr.toDoubleOrNull()
        if (precio == null || precio <= 0) {
            binding.etPrecio.error = getString(R.string.error_precio_invalido)
            binding.etPrecio.requestFocus()
            return
        }

        val cantidad = cantidadStr.toIntOrNull()
        if (cantidad == null || cantidad < 0) {
            binding.etCantidad.error = getString(R.string.error_cantidad_invalida)
            binding.etCantidad.requestFocus()
            return
        }

        val descuento = descuentoStr.toDoubleOrNull()
        if (descuento == null || descuento < 0.0 || descuento > 100.0) {
            binding.etDescuento.error = getString(R.string.error_descuento_invalido)
            binding.etDescuento.requestFocus()
            return
        }

        // Creación del objeto Zapato
        val nuevoZapato = Zapato(
            codigo = codigo,
            marca = marca,
            modelo = modelo,
            talla = talla,
            precio = precio,
            cantidad = cantidad,
            descuento = descuento
        )

        // Inserción en el repositorio en memoria
        val insertado = ZapatoRepository.insertar(nuevoZapato)
        if (insertado) {
            AlertDialog.Builder(this)
                .setTitle("Registro Exitoso")
                .setMessage(R.string.msg_exito_insertar)
                .setPositiveButton("Aceptar") { _, _ ->
                    limpiarCampos()
                }
                .setIcon(android.R.drawable.ic_dialog_info)
                .show()
        } else {
            Toast.makeText(this, R.string.msg_codigo_existe, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private fun limpiarCampos() {
        binding.etCodigo.setText("")
        binding.etMarca.setText("")
        binding.etModelo.setText("")
        binding.etTalla.setText("")
        binding.etPrecio.setText("")
        binding.etCantidad.setText("")
        binding.etDescuento.setText("")
        binding.etCodigo.requestFocus()
    }
}
