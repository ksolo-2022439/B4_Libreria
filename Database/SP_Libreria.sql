use B4_Libreria;

DELIMITER $$
CREATE PROCEDURE sp_InsertarUsuarioAdmin(
    IN p_nombreUsuario VARCHAR(64),
    IN p_correoElectronico VARCHAR(64),
    IN p_contrasenaPlana VARCHAR(255)
)
BEGIN
    DECLARE v_hashedContrasena VARCHAR(255);
    DECLARE v_email_exists INT DEFAULT 0;
    SELECT COUNT(*) INTO v_email_exists FROM Usuario WHERE correoElectronico = p_correoElectronico;
    IF v_email_exists = 0 THEN
        SET v_hashedContrasena = SHA2(p_contrasenaPlana, 256);
        INSERT INTO Usuario (
            nombreUsuario,
            correoElectronico,
            contrasena,
            rol
        ) VALUES (
            p_nombreUsuario,
            p_correoElectronico,
            v_hashedContrasena,
            'ADMINISTRADOR'
        );
    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: El correo electrónico ya se encuentra registrado.';
    END IF;
END$$
DELIMITER ;

CALL sp_InsertarUsuarioAdmin(
    'Admin', 
    'admin@kinalhub.com', 
    'password'
);


select * from Usuario;
select * from CookieAuth;