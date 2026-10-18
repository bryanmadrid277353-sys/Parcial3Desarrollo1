package com.example.parcial03

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityActualizarBinding
import com.example.parcial03.model.Zapato
import com.example.parcial03.repository.ZapatoRepository

/**
 * Pantalla para buscar un zapato por código y actualizar sus datos en el inventario.
 */
class ActualizarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActualizarBinding
    private var zapatoEncontrado: Zapato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Eventos de botones
        binding.btnBuscar.setOnClickListener {
            buscarZapato()
        }

        binding.btnActualizarGuardar.setOnClickListener {
            actualizarZapato()
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }

        // Oyente de cambios de texto para actualización dinámica de vista previa de cálculos
        agregarTextWatchers()
    }

    /**
     * Busca un zapato por el código ingresado.
     */
    private fun buscarZapato() {
        val codigo = binding.etCodigoBuscar.text.toString().trim()

        if (codigo.isEmpty()) {
            binding.etCodigoBuscar.error = getString(R.string.msg_campo_requerido)
            binding.etCodigoBuscar.requestFocus()
            return
        }

        zapatoEncontrado = ZapatoRepository.buscarPorCodigo(codigo)

        if (zapatoEncontrado != null) {
            val z = zapatoEncontrado!!
            // Carga de datos en el formulario
            binding.etMarca.setText(z.marca)
            binding.etModelo.setText(z.modelo)
            binding.etTalla.setText(z.talla.toString())
            binding.etPrecio.setText(z.precio.toString())
            binding.etCantidad.setText(z.cantidad.toString())
            binding.etDescuento.setText(z.descuento.toString())

            // Habilita los campos para edición
            habilitarFormulario(true)
            actualizarVistaPreviaCalculos()
            Toast.makeText(this, "Zapato encontrado. Puede modificar los datos.", Toast.LENGTH_SHORT).show()
        } else {
            habilitarFormulario(false)
            limpiarFormulario()
            AlertDialog.Builder(this)
                .setTitle("Búsqueda sin Resultados")
                .setMessage(R.string.msg_codigo_no_encontrado)
                .setPositiveButton("Aceptar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    /**
     * Valida y guarda los datos actualizados del zapato en el repositorio.
     */
    private fun actualizarZapato() {
        val zOriginal = zapatoEncontrado ?: return

        val marca = binding.etMarca.text.toString().trim()
        val modelo = binding.etModelo.text.toString().trim()
        val tallaStr = binding.etTalla.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()
        val cantidadStr = binding.etCantidad.text.toString().trim()
        val descuentoStr = binding.etDescuento.text.toString().trim()

        // Validaciones de campos vacíos
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

        // Modificación del objeto existente
        val zapatoModificado = Zapato(
            codigo = zOriginal.codigo, // Mantiene el código original
            marca = marca,
            modelo = modelo,
            talla = talla,
            precio = precio,
            cantidad = cantidad,
            descuento = descuento
        )

        val exito = ZapatoRepository.actualizar(zapatoModificado)

        if (exito) {
            AlertDialog.Builder(this)
                .setTitle("Actualización Exitosa")
                .setMessage(R.string.msg_exito_actualizar)
                .setPositiveButton("Aceptar") { _, _ ->
                    limpiarFormulario()
                    habilitarFormulario(false)
                    binding.etCodigoBuscar.setText("")
                }
                .setIcon(android.R.drawable.ic_dialog_info)
                .show()
        } else {
            Toast.makeText(this, "Error al actualizar el registro", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Habilita o deshabilita los controles del formulario según el estado de la búsqueda.
     */
    private fun habilitarFormulario(habilitar: Boolean) {
        binding.etMarca.isEnabled = habilitar
        binding.etModelo.isEnabled = habilitar
        binding.etTalla.isEnabled = habilitar
        binding.etPrecio.isEnabled = habilitar
        binding.etCantidad.isEnabled = habilitar
        binding.etDescuento.isEnabled = habilitar
        binding.btnActualizarGuardar.isEnabled = habilitar
        binding.llVistaPreviaCalculos.visibility = if (habilitar) View.VISIBLE else View.GONE
    }

    /**
     * Limpia las cajas de texto del formulario de edición.
     */
    private fun limpiarFormulario() {
        binding.etMarca.setText("")
        binding.etModelo.setText("")
        binding.etTalla.setText("")
        binding.etPrecio.setText("")
        binding.etCantidad.setText("")
        binding.etDescuento.setText("")
        binding.llVistaPreviaCalculos.visibility = View.GONE
        zapatoEncontrado = null
    }

    /**
     * Asigna vigilantes de cambio de texto a precio, cantidad y descuento para recalcular en tiempo real.
     */
    private fun agregarTextWatchers() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                actualizarVistaPreviaCalculos()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etPrecio.addTextChangedListener(watcher)
        binding.etCantidad.addTextChangedListener(watcher)
        binding.etDescuento.addTextChangedListener(watcher)
    }

    /**
     * Recalcula en tiempo real los valores de Subtotal, Descuento, IVA y Total.
     */
    private fun actualizarVistaPreviaCalculos() {
        val precio = binding.etPrecio.text.toString().toDoubleOrNull() ?: 0.0
        val cantidad = binding.etCantidad.text.toString().toIntOrNull() ?: 0
        val descuento = binding.etDescuento.text.toString().toDoubleOrNull() ?: 0.0

        val tempZapato = Zapato("TMP", "", "", 0.0, precio, cantidad, descuento)

        binding.tvSubtotalPreview.text = getString(R.string.label_subtotal, tempZapato.calcularSubtotal())
        binding.tvDescuentoPreview.text = getString(R.string.label_descuento_monto, descuento, tempZapato.calcularMontoDescuento())
        binding.tvIVAPreview.text = getString(R.string.label_iva, tempZapato.calcularIVA())
        binding.tvTotalPreview.text = getString(R.string.label_total, tempZapato.calcularTotal())
    }
}
