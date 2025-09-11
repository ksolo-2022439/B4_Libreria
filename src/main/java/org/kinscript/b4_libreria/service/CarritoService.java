package org.kinscript.b4_libreria.service;

import org.kinscript.b4_libreria.entity.Carrito;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class CarritoService {

    private static final String CART_SESSION_KEY = "carrito";

    public Carrito getCarrito(HttpSession session) {

        Carrito carrito = (Carrito) session.getAttribute(CART_SESSION_KEY);
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute(CART_SESSION_KEY, carrito);
        }
        return carrito;
    }

    public void agregarLibro(HttpSession session, Integer libroId, Integer cantidad) {
        Carrito carrito = getCarrito(session);
        carrito.agregarLibro(libroId, cantidad);
    }

    public void vaciarCarrito(HttpSession session) {
        getCarrito(session).vaciarCarrito();
    }

}