package com.example.parcial03

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial03.databinding.ActivityEliminarBinding
import com.example.parcial03.model.Zapato
import com.example.parcial03.repository.ZapatoRepository

/**
 * Pantalla para buscar y eliminar de forma segura un zapato del inventario previa confirmación.
 */
class EliminarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEliminarBinding
    private var zapatoEncontrado: Zapato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignación de oyentes de botones
        binding.btnBuscar.setOnClickListener {
            buscarZapato()
        }

        binding.btnEliminarConfirmar.setOnClickListener {
            confirmarEliminacion()
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    /**
     * Busca el zapato por el código digitado y despliega la información detallada si existe.
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
            // Mostrar los datos en la tarjeta de detalle
            binding.tvDetalleCodigo.text = "Código: ${z.codigo}"
            binding.tvDetalleMarcaModelo.text = "Producto: ${z.marca} ${z.modelo}"
            binding.tvDetalleTallaPrecio.text = "Talla: ${z.talla} | Precio Unitario: $${z.precio}"
            binding.tvDetalleCantidad.text = "Cantidad en Stock: ${z.cantidad} pares"

            binding.tvDetalleSubtotal.text = getString(R.string.label_subtotal, z.calcularSubtotal())
            binding.tvDetalleDescuento.text = getString(R.string.label_descuento_monto, z.descuento, z.calcularMontoDescuento())
            binding.tvDetalleIVA.text = getString(R.string.label_iva, z.calcularIVA())
            binding.tvDetalleTotal.text = getString(R.string.label_total, z.calcularTotal())

            binding.cvDetallesEliminar.visibility = View.VISIBLE
            binding.btnEliminarConfirmar.isEnabled = true
        } else {
            binding.cvDetallesEliminar.visibility = View.GONE
            binding.btnEliminarConfirmar.isEnabled = false
            AlertDialog.Builder(this)
                .setTitle("Búsqueda sin Resultados")
                .setMessage(R.string.msg_codigo_no_encontrado)
                .setPositiveButton("Aceptar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    /**
     * Solicita confirmación explícita mediante un AlertDialog antes de eliminar de la lista.
     */
    private fun confirmarEliminacion() {
        val z = zapatoEncontrado ?: return

        val mensaje = getString(
            R.string.dialog_eliminar_msg,
            z.codigo,
            z.marca,
            z.modelo
        )

        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_eliminar_titulo)
            .setMessage(mensaje)
            .setPositiveButton("Sí, Eliminar") { _, _ ->
                ejecutarEliminacion(z.codigo)
            }
            .setNegativeButton(R.string.btn_cancelar, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    /**
     * Ejecuta la eliminación en el repositorio y reinicia los controles.
     */
    private fun ejecutarEliminacion(codigo: String) {
        val eliminado = ZapatoRepository.eliminar(codigo)

        if (eliminado) {
            Toast.makeText(this, R.string.msg_exito_eliminar, Toast.LENGTH_LONG).show()
            binding.cvDetallesEliminar.visibility = View.GONE
            binding.btnEliminarConfirmar.isEnabled = false
            binding.etCodigoBuscar.setText("")
            zapatoEncontrado = null
        } else {
            Toast.makeText(this, "No se pudo eliminar el producto.", Toast.LENGTH_SHORT).show()
        }
    }
}
