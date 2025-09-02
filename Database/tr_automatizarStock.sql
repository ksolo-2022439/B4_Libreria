DELIMITER //
	CREATE TRIGGER tr_despues_insertar_detalle_pedido
	AFTER INSERT ON DetallePedido
	FOR EACH ROW
		BEGIN
			UPDATE Libro
			SET stock = stock - NEW.cantidad
			WHERE idLibro = NEW.idLibro;
		END //
DELIMITER ;
 
DELIMITER //
	CREATE TRIGGER tr_despues_actualizar_detalle_pedido
	AFTER UPDATE ON DetallePedido
	FOR EACH ROW
		BEGIN
			IF NEW.cantidad <> OLD.cantidad THEN
				-- Si la cantidad ha aumentado, restar del stock
				IF NEW.cantidad > OLD.cantidad THEN
					UPDATE Libro
					SET stock = stock - (NEW.cantidad - OLD.cantidad)
					WHERE idLibro = NEW.idLibro;
				-- Si la cantidad ha disminuido, sumar al stock
				ELSE
					UPDATE Libro
					SET stock = stock + (OLD.cantidad - NEW.cantidad)
					WHERE idLibro = NEW.idLibro;
				END IF;
			END IF;
		END //
DELIMITER ;
 
DELIMITER //
	CREATE TRIGGER tr_despues_eliminar_detalle_pedido
	AFTER DELETE ON DetallePedido
	FOR EACH ROW
		BEGIN
			UPDATE Libro
			SET stock = stock + OLD.cantidad
			WHERE idLibro = OLD.idLibro;
		END //
DELIMITER ;