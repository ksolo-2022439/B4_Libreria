DROP DATABASE IF EXISTS B4_Libreria;
CREATE DATABASE B4_Libreria;
USE B4_Libreria;
 
CREATE TABLE Libro (
    idLibro INT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    categoria VARCHAR(32) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    CONSTRAINT pk_libro PRIMARY KEY (idLibro)
);
 
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT NOT NULL,
    nombreUsuario VARCHAR(64) NOT NULL,
    correoElectronico VARCHAR(64) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('CLIENTE', 'ADMINISTRADOR') DEFAULT 'CLIENTE',
    CONSTRAINT pk_usuario PRIMARY KEY (idUsuario)
);
 
CREATE TABLE Pedido (
    idPedido INT AUTO_INCREMENT NOT NULL,
    idUsuario INT NOT NULL,
    fechaPedido DATETIME NOT NULL,
    estado ENUM('CREADO', 'PAGADO', 'ENVIADO', 'ENTREGADO', 'CANCELADO') DEFAULT 'CREADO',
    CONSTRAINT pk_pedido PRIMARY KEY (idPedido),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);
 
CREATE TABLE DetallePedido (
    idDetallePedido INT AUTO_INCREMENT NOT NULL,
    idPedido INT NOT NULL,
    idLibro INT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10, 2) NOT NULL,
    CONSTRAINT pk_detallepedido PRIMARY KEY (idDetallePedido),
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (idPedido) REFERENCES Pedido(idPedido),
    CONSTRAINT fk_detalle_libro FOREIGN KEY (idLibro) REFERENCES Libro(idLibro)
);
 
CREATE TABLE CookieAuth (
    idCookie INT AUTO_INCREMENT NOT NULL,
    idUsuario INT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    fechaExpiracion DATETIME NOT NULL,
    CONSTRAINT pk_cookie PRIMARY KEY (idCookie),
    CONSTRAINT fk_sesion_usuario FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);
 
CREATE INDEX idx_pedido_usuario ON Pedido(idUsuario);
CREATE INDEX idx_detalle_pedido_libro ON DetallePedido(idLibro);
CREATE INDEX idx_cookie_usuario ON CookieAuth(idUsuario);