package com.example.parcial03

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parcial03.adapter.ZapatoAdapter
import com.example.parcial03.databinding.ActivityListarBinding
import com.example.parcial03.repository.ZapatoRepository

/**
 * Pantalla que lista todos los zapatos registrados en el inventario y presenta el resumen general de los cálculos acumulados.
 */
class ListarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarBinding
    private lateinit var adapter: ZapatoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de RecyclerView y Adapter
        binding.rvZapatos.layoutManager = LinearLayoutManager(this)
        adapter = ZapatoAdapter(emptyList())
        binding.rvZapatos.adapter = adapter

        // Botón regresar
        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarDatos()
    }

    /**
     * Carga la lista de zapatos y actualiza los indicadores del resumen del inventario.
     */
    private fun cargarDatos() {
        val lista = ZapatoRepository.obtenerTodos()

        if (lista.isEmpty()) {
            binding.tvListaVacia.visibility = View.VISIBLE
            binding.rvZapatos.visibility = View.GONE
        } else {
            binding.tvListaVacia.visibility = View.GONE
            binding.rvZapatos.visibility = View.VISIBLE
            adapter.actualizarLista(lista)
        }

        // Cálculo y actualización del resumen global del inventario
        val totalPares = ZapatoRepository.obtenerTotalPares()
        val valorTotalInventario = ZapatoRepository.obtenerValorTotalInventario()

        binding.tvTotalPares.text = getString(R.string.label_total_pares, totalPares)
        binding.tvValorInventarioTotal.text = getString(R.string.label_valor_inventario, valorTotalInventario)
    }
}
