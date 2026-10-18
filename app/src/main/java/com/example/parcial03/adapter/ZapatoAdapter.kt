package com.example.parcial03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial03.R
import com.example.parcial03.databinding.ItemZapatoBinding
import com.example.parcial03.model.Zapato
import java.util.Locale

/**
 * Adaptador para mostrar la lista de zapatos deportivos en el RecyclerView de ListarActivity.
 */
class ZapatoAdapter(
    private var listaZapatos: List<Zapato>
) : RecyclerView.Adapter<ZapatoAdapter.ZapatoViewHolder>() {

    /**
     * ViewHolder que mantiene las referencias a la vista de cada elemento mediante ViewBinding.
     */
    inner class ZapatoViewHolder(val binding: ItemZapatoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZapatoViewHolder {
        val binding = ItemZapatoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ZapatoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZapatoViewHolder, position: Int) {
        val zapato = listaZapatos[position]
        val context = holder.itemView.context

        with(holder.binding) {
            tvItemCodigo.text = zapato.codigo
            tvItemMarcaModelo.text = "${zapato.marca} ${zapato.modelo}"
            tvItemTalla.text = "Talla: ${zapato.talla}"

            tvItemPrecioCantidad.text = String.format(
                Locale.US,
                "Precio: $%.2f | Stock: %d pares",
                zapato.precio,
                zapato.cantidad
            )

            // Asignación de desglose de cálculos por elemento
            tvItemSubtotal.text = context.getString(
                R.string.label_subtotal,
                zapato.calcularSubtotal()
            )

            tvItemDescuento.text = context.getString(
                R.string.label_descuento_monto,
                zapato.descuento,
                zapato.calcularMontoDescuento()
            )

            tvItemIVA.text = context.getString(
                R.string.label_iva,
                zapato.calcularIVA()
            )

            tvItemTotal.text = context.getString(
                R.string.label_total,
                zapato.calcularTotal()
            )
        }
    }

    override fun getItemCount(): Int = listaZapatos.size

    /**
     * Actualiza la lista de datos del adaptador y notifica al RecyclerView.
     */
    fun actualizarLista(nuevaLista: List<Zapato>) {
        this.listaZapatos = nuevaLista
        notifyDataSetChanged()
    }
}
