package org.kinscript.b4_libreria.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Carrito implements Serializable {

    private Map<Integer, Integer> items = new HashMap<>();

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void agregarLibro(Integer libroId, Integer cantidad) {
        // Si el libro ya está en el carrito, se le suma la nueva cantidad.
        // Si no, se agrega con la cantidad inicial.
        items.put(libroId, items.getOrDefault(libroId, 0) + cantidad);
    }

    public void eliminarLibro(Integer libroId) {
        items.remove(libroId);
    }

    public void vaciarCarrito() {
        items.clear();
    }
}