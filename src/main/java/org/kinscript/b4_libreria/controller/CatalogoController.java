package org.kinscript.b4_libreria.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.kinscript.b4_libreria.entity.Libro;
import org.kinscript.b4_libreria.entity.Carrito;
import org.kinscript.b4_libreria.service.CarritoService;
import org.kinscript.b4_libreria.service.CompraService;
import org.kinscript.b4_libreria.repository.LibrosRepository;
import org.kinscript.b4_libreria.repository.UsuarioRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;

@Named("catalogoBean")
@ViewScoped
public class CatalogoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LibrosRepository librosRepository;

    @Inject
    private CarritoService carritoService;

    @Inject
    private CompraService compraService;

    @Inject
    private UsuarioRepository usuarioRepository;

    private List<Libro> libros;
    private Carrito carrito;

    // Propiedades para el formulario de Checkout
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String instrucciones;

    private static final BigDecimal IVA_RATE = new BigDecimal("0.12");
    private static final BigDecimal COSTO_ENVIO = new BigDecimal("25.00");

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        this.carrito = carritoService.getCarrito(session);
        this.libros = librosRepository.findAll();
    }

    public String agregarAlCarrito(Libro libro) {
        if (libro.getStock() > 0) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            carritoService.agregarLibro(session, libro.getIdLibro(), 1);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Libro agregado al carrito", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay stock disponible para este libro", ""));
        }
        return null;
    }

    public String comprar(Libro libro) {
        if (libro.getStock() > 0) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            carritoService.agregarLibro(session, libro.getIdLibro(), 1);
            return "carrito.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay stock disponible para este libro", ""));
            return null;
        }
    }

    public void eliminarLibro(Libro libro) {
        this.carrito.getItems().remove(libro.getIdLibro());
    }

    public void aumentarCantidad(Libro libro) {
        Integer cantidadActual = this.carrito.getItems().getOrDefault(libro.getIdLibro(), 0);
        if (cantidadActual < libro.getStock()) {
            this.carrito.getItems().put(libro.getIdLibro(), cantidadActual + 1);
        }
    }

    public void disminuirCantidad(Libro libro) {
        Integer cantidadActual = this.carrito.getItems().getOrDefault(libro.getIdLibro(), 0);
        if (cantidadActual > 1) {
            this.carrito.getItems().put(libro.getIdLibro(), cantidadActual - 1);
        } else if (cantidadActual == 1) {
            eliminarLibro(libro);
        }
    }

    public String procesarCompra() {
        try {
            Integer usuarioId = 1;
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            compraService.procesarCompra(session, usuarioId);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra realizada con éxito!", ""));

            carrito.getItems().clear();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al procesar la compra", e.getMessage()));
        }
        return "catalogo.xhtml?faces-redirect=true";
    }

    // Método de navegación
    public String irACheckout() {
        return "checkout.xhtml?faces-redirect=true";
    }

    // Métodos para limpiar el formulario después de una compra
    public void limpiarFormulario() {
        this.nombre = null;
        this.apellido = null;
        this.email = null;
        this.telefono = null;
        this.direccion = null;
        this.ciudad = null;
        this.departamento = null;
        this.instrucciones = null;
    }

    // Getters y setters para el formulario de Checkout
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }

    // Getters para el cálculo del carrito
    public List<Libro> getLibros() { return libros; }
    public Carrito getCarrito() { return carrito; }

    public List<Libro> getLibrosEnCarrito() {
        List<Libro> librosEnCarrito = new ArrayList<>();
        for (Integer libroId : carrito.getItems().keySet()) {
            Optional<Libro> libroOpt = librosRepository.findById(libroId);
            libroOpt.ifPresent(librosEnCarrito::add);
        }
        return librosEnCarrito;
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Integer, Integer> item : carrito.getItems().entrySet()) {
            Optional<Libro> libroOpt = librosRepository.findById(item.getKey());
            if (libroOpt.isPresent()) {
                BigDecimal precio = libroOpt.get().getPrecio();
                BigDecimal cantidad = new BigDecimal(item.getValue());
                subtotal = subtotal.add(precio.multiply(cantidad));
            }
        }
        return subtotal;
    }

    public BigDecimal getIva() {
        return getSubtotal().multiply(IVA_RATE);
    }

    public BigDecimal getCostoEnvio() {
        return COSTO_ENVIO;
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getIva()).add(COSTO_ENVIO);
    }
}
