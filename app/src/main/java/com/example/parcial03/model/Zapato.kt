package com.example.parcial03.model

/**
 * Modelo de datos que representa un Zapato Deportivo en la tienda.
 *
 * @property codigo Identificador único del zapato (ej. "ZAP-001").
 * @property marca Nombre de la marca deportiva (ej. "Nike", "Adidas").
 * @property modelo Nombre del modelo (ej. "Air Zoom Pegasus").
 * @property talla Talla del calzado (ej. 41.5).
 * @property precio Precio unitario en dólares.
 * @property cantidad Cantidad de pares disponibles en inventario.
 * @property descuento Porcentaje de descuento aplicado (0 a 100).
 */
data class Zapato(
    var codigo: String,
    var marca: String,
    var modelo: String,
    var talla: Double,
    var precio: Double,
    var cantidad: Int,
    var descuento: Double
) {
    /**
     * Calcula el subtotal multiplicando el precio unitario por la cantidad de pares.
     * @return Subtotal = precio * cantidad
     */
    fun calcularSubtotal(): Double {
        return precio * cantidad
    }

    /**
     * Calcula el monto en dólares del descuento aplicado sobre el subtotal.
     * @return Monto de Descuento = subtotal * (porcentajeDescuento / 100)
     */
    fun calcularMontoDescuento(): Double {
        return calcularSubtotal() * (descuento / 100.0)
    }

    /**
     * Calcula el subtotal con el descuento ya restado.
     * @return SubtotalConDescuento = subtotal - montoDescuento
     */
    fun calcularSubtotalConDescuento(): Double {
        return calcularSubtotal() - calcularMontoDescuento()
    }

    /**
     * Calcula el IVA del 13% sobre el subtotal con descuento.
     * @return IVA (13%) = subtotalConDescuento * 0.13
     */
    fun calcularIVA(): Double {
        return calcularSubtotalConDescuento() * 0.13
    }

    /**
     * Calcula el total a pagar sumando el subtotal con descuento más el IVA.
     * @return Total a pagar = subtotalConDescuento + IVA
     */
    fun calcularTotal(): Double {
        return calcularSubtotalConDescuento() + calcularIVA()
    }
}
