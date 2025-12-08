-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql
-- Tiempo de generación: 08-12-2025 a las 21:23:56
-- Versión del servidor: 8.4.7
-- Versión de PHP: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gimnasio`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`%` PROCEDURE `actualizar_clase` (IN `p_idClase` INT, IN `p_tipoClase` ENUM('Yoga','Crossfit','Zumba','Plates','Cardio'), IN `p_descripcion` TEXT, IN `p_precio` DECIMAL(10,2), IN `p_ubicacion` VARCHAR(100), IN `p_horario` DATETIME, IN `p_capacidadMax` INT, IN `p_personasInscritas` INT, IN `p_idEntrenador` INT)   BEGIN
    START TRANSACTION;
    
    UPDATE clases SET
        tipoClase = p_tipoClase,
        descripcion = p_descripcion COLLATE utf8mb4_general_ci,
        precio = p_precio,
        ubicacion = p_ubicacion COLLATE utf8mb4_general_ci,
        horario = p_horario,
        capacidadMax = p_capacidadMax,
        personasInscritas = p_personasInscritas,
        idEntrenador = p_idEntrenador
    WHERE idClase = p_idClase;
    
    COMMIT;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `actualizar_cliente` (IN `p_cedula` VARCHAR(11), IN `p_nombre` VARCHAR(30), IN `p_primerApellido` VARCHAR(20), IN `p_segundoApellido` VARCHAR(30), IN `p_fechaNacimiento` DATE, IN `p_telefono` VARCHAR(8), IN `p_correo` VARCHAR(70), IN `p_tipoMembresia` INT)   BEGIN
    UPDATE clientes 
    SET nombre = p_nombre,
        primerApellido = p_primerApellido,
        segundoApellido = p_segundoApellido,
        fechaNacimiento = p_fechaNacimiento,
        telefono = p_telefono,
        correo = p_correo,
        tipoMembresia = p_tipoMembresia
    WHERE cedula COLLATE utf8mb4_general_ci = p_cedula COLLATE utf8mb4_general_ci;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_entrenador` (IN `p_id` INT, IN `p_nombre` VARCHAR(30), IN `p_apellido` VARCHAR(30), IN `p_segApellido` VARCHAR(30), IN `p_telefono` VARCHAR(8), IN `p_correo` VARCHAR(70), IN `p_especialidades` VARCHAR(50))   BEGIN
    UPDATE entrenadores
    SET nombre = p_nombre,
        primerApellido = p_apellido,
        segundoApellido = p_segApellido,
        telefono = p_telefono,
        correo = p_correo,
        especialidades = p_especialidades
    WHERE idEntrenador = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_membresia` (IN `p_id` INT, IN `p_nombre` VARCHAR(50), IN `p_precio` DECIMAL(10,2), IN `p_duracion` INT, IN `p_beneficios` TEXT)   BEGIN
    UPDATE membresias
    SET nombre = p_nombre,
        precio = p_precio,
        duracionDias = p_duracion,
        beneficios = p_beneficios
    WHERE idMembresia = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_pago` (IN `p_idPago` INT, IN `p_monto` DECIMAL(10,2), IN `p_metodo` VARCHAR(50), IN `p_descripcion` VARCHAR(200))   BEGIN
    IF NOT EXISTS (SELECT 1 FROM pagos WHERE idPago = p_idPago) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El pago no existe.';
    END IF;

    UPDATE pagos
    SET monto = p_monto,
        metodoPago = p_metodo,
        descripcion = p_descripcion
    WHERE idPago = p_idPago;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_rol` (IN `p_id` INT, IN `p_nombre` VARCHAR(50))   BEGIN
    UPDATE roles SET nombre = p_nombre WHERE idRol = p_id;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `actualizar_usuario` (IN `p_idUsuario` INT, IN `p_usuario` VARCHAR(30), IN `p_contra` VARBINARY(255), IN `p_idRol` INT)   BEGIN
    UPDATE usuarios 
    SET usuario = p_usuario, 
        contrasena = p_contra, 
        idRol = p_idRol
    WHERE idUsuario = p_idUsuario;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `agregar_clase` (IN `p_tipo` VARCHAR(50), IN `p_descripcion` TEXT, IN `p_precio` DECIMAL(10,2), IN `p_ubicacion` VARCHAR(100), IN `p_horario` DATETIME, IN `p_capacidad` INT, IN `p_idEntrenador` INT)   BEGIN
    START TRANSACTION;
    
    INSERT INTO clases(
        tipoClase, 
        descripcion, 
        precio, 
        ubicacion, 
        horario, 
        capacidadMax, 
        personasInscritas, 
        idEntrenador
    )
    VALUES (
        p_tipo COLLATE utf8mb4_general_ci,
        p_descripcion COLLATE utf8mb4_general_ci,
        p_precio,
        p_ubicacion COLLATE utf8mb4_general_ci,
        p_horario,
        p_capacidad,
        0,
        p_idEntrenador
    );
    
    COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agregar_cliente` (IN `p_cedula` VARCHAR(11), IN `p_nombre` VARCHAR(30), IN `p_apellido` VARCHAR(30), IN `p_segApellido` VARCHAR(30), IN `p_fecha` DATE, IN `p_telefono` VARCHAR(8), IN `p_correo` VARCHAR(70), IN `p_membresia` INT)   BEGIN
    INSERT INTO clientes(cedula, nombre, primerApellido, segundoApellido, fechaNacimiento, telefono, correo, tipoMembresia)
    VALUES (p_cedula, p_nombre, p_apellido, p_segApellido,  p_fecha, p_telefono, p_correo, p_membresia);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agregar_cliente_clase` (IN `p_cedula` VARCHAR(11), IN `p_idClase` INT)   BEGIN
    INSERT INTO clienteclase(cedulaCliente, idClase)
    VALUES (p_cedula, p_idClase);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agregar_entrenador` (IN `p_nombre` VARCHAR(30), IN `p_apellido` VARCHAR(30), IN `p_segApellido` VARCHAR(30), IN `p_telefono` VARCHAR(8), IN `p_correo` VARCHAR(70), IN `p_especialidades` VARCHAR(50))   BEGIN
    INSERT INTO entrenadores(nombre, primerApellido, segundoApellido, telefono, correo, especialidades)
    VALUES (p_nombre, p_apellido, p_segApellido, p_telefono, p_correo, p_especialidades);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agregar_membresia` (IN `p_nombre` VARCHAR(50), IN `p_precio` DECIMAL(10,2), IN `p_duracion` INT, IN `p_beneficios` TEXT)   BEGIN
    INSERT INTO membresias(nombre, precio, duracionDias, beneficios)
    VALUES (p_nombre, p_precio, p_duracion, p_beneficios);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agregar_rol` (IN `p_nombre` VARCHAR(50))   BEGIN
    INSERT INTO roles(nombre) VALUES (p_nombre);
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `agregar_usuario` (IN `p_usuario` VARCHAR(30), IN `p_contra` VARBINARY(255), IN `p_idRol` INT)   BEGIN
    INSERT INTO usuarios (usuario, contrasena, idRol) 
    VALUES (p_usuario, p_contra, p_idRol);
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `buscar_clase` (IN `p_idClase` INT)   BEGIN
    SELECT 
        idClase,
        tipoClase,
        descripcion,
        precio,
        ubicacion,
        horario,
        capacidadMax,
        personasInscritas,
        idEntrenador
    FROM clases
    WHERE idClase = p_idClase;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `cambiar_contrasena` (IN `p_idUsuario` INT, IN `p_nuevaContra` VARBINARY(255))   BEGIN
    UPDATE usuarios 
    SET contrasena = p_nuevaContra
    WHERE idUsuario = p_idUsuario;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_clase` (IN `p_id` INT)   BEGIN
    DELETE FROM clases WHERE idClase = p_id;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `eliminar_cliente` (IN `p_cedula` VARCHAR(11))   BEGIN
    START TRANSACTION;
    
    -- Forzar collation en las comparaciones
    DELETE FROM clienteclase 
    WHERE cedulaCliente COLLATE utf8mb4_general_ci = p_cedula COLLATE utf8mb4_general_ci;
    
    DELETE FROM historialpagos 
    WHERE cedulaCliente COLLATE utf8mb4_general_ci = p_cedula COLLATE utf8mb4_general_ci;
    
    DELETE FROM clientes 
    WHERE cedula COLLATE utf8mb4_general_ci = p_cedula COLLATE utf8mb4_general_ci;
    
    COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_cliente_clase` (IN `p_cedula` VARCHAR(11), IN `p_idClase` INT)   BEGIN
    DELETE FROM clienteclase
    WHERE cedulaCliente = p_cedula
      AND idClase = p_idClase;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `eliminar_entrenador` (IN `p_idEntrenador` INT)   BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Primero eliminar las clases del entrenador
    DELETE FROM clases WHERE idEntrenador = p_idEntrenador;
    
    -- Luego eliminar el entrenador
    DELETE FROM entrenadores WHERE idEntrenador = p_idEntrenador;
    
    COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_membresia` (IN `p_id` INT)   BEGIN
    DELETE FROM membresias WHERE idMembresia = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_pago` (IN `p_idPago` INT)   BEGIN
    IF NOT EXISTS (SELECT 1 FROM pagos WHERE idPago = p_idPago) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El pago no existe.';
    END IF;

    DELETE FROM pagos WHERE idPago = p_idPago;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_rol` (IN `p_id` INT)   BEGIN
    DELETE FROM roles WHERE idRol = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_usuario` (IN `p_id` INT)   BEGIN
    DELETE FROM usuarios WHERE idUsuario = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login_usuario` (IN `p_usuario` VARCHAR(30), IN `p_contrasena` VARCHAR(50))   BEGIN
    SELECT idUsuario, usuario, idRol
    FROM usuarios
    WHERE usuario = p_usuario
      AND contrasena = SHA2(p_contrasena, 256)
    LIMIT 1;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `registrar_clase` (IN `p_tipoClase` ENUM('Yoga','Crossfit','Zumba','Plates','Cardio'), IN `p_descripcion` TEXT, IN `p_precio` DECIMAL(10,2), IN `p_ubicacion` VARCHAR(100), IN `p_horario` DATETIME, IN `p_capacidadMax` INT, IN `p_idEntrenador` INT)   BEGIN
    START TRANSACTION;
    
    INSERT INTO clases(
        tipoClase, 
        descripcion, 
        precio, 
        ubicacion, 
        horario, 
        capacidadMax, 
        personasInscritas, 
        idEntrenador
    )
    VALUES (
        p_tipoClase,
        p_descripcion,
        p_precio,
        p_ubicacion,
        p_horario,
        p_capacidadMax,
        0,  -- personasInscritas siempre empieza en 0
        p_idEntrenador
    );
    
    COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registrar_pago` (IN `p_cedula` VARCHAR(11), IN `p_monto` DECIMAL(10,2), IN `p_metodo` VARCHAR(50), IN `p_descripcion` VARCHAR(200))   BEGIN

    IF NOT EXISTS (SELECT 1 FROM clientes WHERE cedula = p_cedula) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El cliente no existe.';
    END IF;

    INSERT INTO pagos(cedulaCliente, fechaPago, monto, metodoPago, descripcion)
    VALUES (p_cedula, NOW(), p_monto, p_metodo, p_descripcion);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_clases` ()   BEGIN
    SELECT * FROM clases;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_clases_entrenador` (IN `p_idEntrenador` INT)   BEGIN
    SELECT * 
    FROM clases
    WHERE idEntrenador = p_idEntrenador;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_clases_inscrito_cliente` (IN `p_cedula` VARCHAR(11))   BEGIN
    SELECT c.*
    FROM clases c
    JOIN clienteclase cc ON cc.idClase = c.idClase
    WHERE cc.cedulaCliente = p_cedula;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_clase_por_tipo` (IN `p_tipo` ENUM('Yoga','Crossfit','Zumba','Pilates','Cardio'))   BEGIN
    SELECT * FROM clases WHERE tipoClase = p_tipo;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `ver_cliente` (IN `p_cedula` VARCHAR(11))   BEGIN
    SELECT * FROM clientes 
    WHERE cedula COLLATE utf8mb4_general_ci = p_cedula COLLATE utf8mb4_general_ci;
END$$

CREATE DEFINER=`root`@`%` PROCEDURE `ver_clientes` ()   BEGIN
    SELECT * FROM clientes ORDER BY nombre, primerApellido;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_entrenador` (IN `p_id` INT)   BEGIN
    SELECT * FROM entrenadores WHERE idEntrenador = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_entrenadores` ()   BEGIN
    SELECT * FROM entrenadores;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_historial_pagos` ()   BEGIN
    SELECT * FROM historialpagos;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_historial_pagos_cliente` (IN `p_cedula` VARCHAR(11))   BEGIN
    SELECT * FROM historialpagos WHERE cedulaCliente = p_cedula;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_membresias` ()   BEGIN
    SELECT * FROM membresias;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_roles` ()   BEGIN
    SELECT * FROM roles;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_usuarios` ()   BEGIN
    SELECT * FROM usuarios;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clases`
--

CREATE TABLE `clases` (
  `idClase` int NOT NULL,
  `tipoClase` enum('Yoga','Crossfit','Zumba','Pilates','Cardio') COLLATE utf8mb4_general_ci NOT NULL,
  `descripcion` text COLLATE utf8mb4_general_ci NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `ubicacion` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `horario` datetime NOT NULL,
  `capacidadMax` int NOT NULL,
  `personasInscritas` int NOT NULL DEFAULT '0',
  `idEntrenador` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clases`
--

INSERT INTO `clases` (`idClase`, `tipoClase`, `descripcion`, `precio`, `ubicacion`, `horario`, `capacidadMax`, `personasInscritas`, `idEntrenador`) VALUES
(2, 'Zumba', 'Clase de Zumba energética — Duración: 50 min. Requisitos: tenis deportivos. Recomendaciones: traer toalla.', 6000.00, 'Sala 2', '2025-03-04 09:00:00', 20, 0, 2),
(3, 'Pilates', 'Pilates para fortalecimiento — Duración: 55 min. Requisitos: esterilla. Recomendaciones: no comer 1 h antes.', 7000.00, 'Sala 3', '2025-03-05 10:00:00', 15, 0, 3),
(4, 'Cardio', 'Entrenamiento Cardio intensivo — Duración: 45 min. Requisitos: buena hidratación. Recomendación: calentar antes.', 5500.00, 'Sala 4', '2025-03-06 11:00:00', 12, 0, 4),
(5, 'Crossfit', 'Crossfit de nivel básico — Duración: 60 min. Requisitos: ropa resistente. Recomendación: traer agua.', 8000.00, 'Sala 5', '2025-03-07 12:00:00', 18, 0, 5),
(6, 'Yoga', 'Clase de estiramiento profundo — Duración: 40 min. Requisitos: flexibilidad básica. Recomendación: respirar profundo.', 4000.00, 'Sala 1', '2025-03-08 09:00:00', 20, 0, 6),
(8, 'Zumba', 'Zumba ritmo latino — Duración: 50 min. Alto gasto calórico.', 6000.00, 'Sala 2', '2025-03-11 09:00:00', 20, 0, 2),
(9, 'Pilates', 'Pilates de control corporal — Duración: 55 min.', 7000.00, 'Sala 3', '2025-03-12 10:00:00', 15, 0, 3),
(10, 'Cardio', 'Cardio HIIT — Duración: 40 min. Ideal para quemar grasa rápido.', 5500.00, 'Sala 4', '2025-03-13 11:00:00', 12, 0, 4),
(11, 'Crossfit', 'Crossfit intermedio — Duración: 60 min.', 8000.00, 'Sala 5', '2025-03-14 12:00:00', 18, 0, 5),
(12, 'Pilates', 'Estiramientos guiados — Duración: 40 min.', 4000.00, 'Sala 1', '2025-03-15 09:00:00', 20, 0, 6),
(15, 'Zumba', 'Zumba extrema — Duración: 50 min. Intensidad alta.', 6000.00, 'Sala 2', '2025-03-18 09:00:00', 20, 0, 2),
(16, 'Pilates', 'Pilates avanzado — Duración: 55 min.', 7000.00, 'Sala 3', '2025-03-19 10:00:00', 15, 0, 3),
(17, 'Cardio', 'Cardio FullBody — Duración: 45 min.', 5500.00, 'Sala 4', '2025-03-20 11:00:00', 12, 0, 4),
(18, 'Crossfit', 'Crossfit fuerza y potencia — Duración: 60 min.', 8000.00, 'Sala 5', '2025-03-21 12:00:00', 18, 0, 5),
(19, 'Pilates', 'Stretching relajante — Duración: 40 min.', 4000.00, 'Sala 1', '2025-03-22 09:00:00', 20, 0, 6),
(23, 'Cardio', 'dwadwadwadwad', 1919191.00, 'Sala 3', '2026-07-29 18:30:00', 6, 0, 2);

--
-- Disparadores `clases`
--
DELIMITER $$
CREATE TRIGGER `validar_sala_entrenador_ocupados` BEFORE INSERT ON `clases` FOR EACH ROW BEGIN
    DECLARE salaOcupada INT;
    DECLARE entrenadorOcupado INT;

    SELECT COUNT(*) INTO salaOcupada
    FROM clases
    WHERE ubicacion = NEW.ubicacion
      AND DATE(horario) = DATE(NEW.horario)
      AND TIME(horario) = TIME(NEW.horario);

    IF salaOcupada > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Sala ocupada';
    END IF;

    SELECT COUNT(*) INTO entrenadorOcupado
    FROM clases
    WHERE idEntrenador = NEW.idEntrenador
      AND DATE(horario) = DATE(NEW.horario)
      AND TIME(horario) = TIME(NEW.horario);

    IF entrenadorOcupado > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Entrenador ocupado';
    END IF;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clienteclase`
--

CREATE TABLE `clienteclase` (
  `cedulaCliente` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `idClase` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Disparadores `clienteclase`
--
DELIMITER $$
CREATE TRIGGER `actualizar_inscritos` AFTER INSERT ON `clienteclase` FOR EACH ROW BEGIN
    UPDATE clases SET personasInscritas = personasInscritas + 1 WHERE idClase = NEW.idClase;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `actualizar_inscritos_delete` AFTER DELETE ON `clienteclase` FOR EACH ROW BEGIN
    UPDATE clases
    SET personasInscritas = personasInscritas - 1
    WHERE idClase = OLD.idClase;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_clase_llena` BEFORE INSERT ON `clienteclase` FOR EACH ROW BEGIN
    DECLARE capacidad INT;
    DECLARE inscritos INT;

    SELECT capacidadMax, personasInscritas
    INTO capacidad, inscritos
    FROM clases
    WHERE idClase = NEW.idClase;

    IF inscritos >= capacidad THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La clase ya está llena.';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_cliente_existe` BEFORE INSERT ON `clienteclase` FOR EACH ROW BEGIN
    DECLARE existe INT;

    SELECT COUNT(*)
    INTO existe
    FROM clientes
    WHERE cedula = NEW.cedulaCliente;

    IF existe = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cliente no existe.';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_cliente_ya_inscrito` BEFORE INSERT ON `clienteclase` FOR EACH ROW BEGIN
    DECLARE repetido INT;

    SELECT COUNT(*)
    INTO repetido
    FROM clienteclase
    WHERE cedulaCliente = NEW.cedulaCliente
      AND idClase = NEW.idClase;

    IF repetido > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cliente ya está inscrito en esta clase.';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_limite_clases` BEFORE INSERT ON `clienteclase` FOR EACH ROW BEGIN
    DECLARE tipoM INT;
    DECLARE clasesSemana INT;
    DECLARE fechaClase DATETIME;

    SELECT tipoMembresia INTO tipoM
    FROM clientes
    WHERE cedula = NEW.cedulaCliente;

    SELECT horario INTO fechaClase
    FROM clases
    WHERE idClase = NEW.idClase;

    SELECT COUNT(*) INTO clasesSemana
    FROM clienteclase cc
    JOIN clases c ON cc.idClase = c.idClase
    WHERE cc.cedulaCliente = NEW.cedulaCliente
      AND YEARWEEK(c.horario) = YEARWEEK(fechaClase);

    IF tipoM = 1 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La membresía BasicFit no permite agendar clases.';
    END IF;

    IF tipoM = 2 AND clasesSemana >= 2 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La membresía Fit solo permite agendar hasta 2 clases por semana.';
    END IF;

    IF tipoM = 3 AND clasesSemana >= 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La membresía DeluxeFit solo permite agendar hasta 5 clases por semana.';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `cedula` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `primerApellido` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `segundoApellido` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `telefono` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `correo` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  `tipoMembresia` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`cedula`, `nombre`, `primerApellido`, `segundoApellido`, `fechaNacimiento`, `telefono`, `correo`, `tipoMembresia`) VALUES
('102345678', 'Laura', 'Soto', 'Ramirez', '2000-05-10', '71112222', 'laura@mail.com', 1),
('305660224', 'Dennis', 'Marchena', 'Delgado', '2006-06-29', '61354000', 'dmarchena9@gmail.com', 3),
('305660227', 'Maria', 'adwa', 'wadawd', '2000-06-29', '82341234', 'dwad@gmail.com', 2),
('800000001', 'Basic', 'Test', 'User', '2000-01-01', '70000000', 'basic@test.com', 1),
('800000003', 'Deluxe', 'Test', 'User', '2000-01-01', '70000002', 'deluxe@test.com', 3);

--
-- Disparadores `clientes`
--
DELIMITER $$
CREATE TRIGGER `validar_cedula_cliente` BEFORE INSERT ON `clientes` FOR EACH ROW BEGIN
    IF SUBSTRING(NEW.cedula,1,1) NOT BETWEEN '1' AND '8' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cédula inválida';
    END IF;

    IF LENGTH(NEW.cedula) NOT IN (9, 11) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La cédula debe tener 9 u 11 dígitos';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_correo_cliente` BEFORE INSERT ON `clientes` FOR EACH ROW BEGIN
    IF NEW.correo NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.com$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Correo inválido';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_fecha_nacimiento` BEFORE INSERT ON `clientes` FOR EACH ROW BEGIN
    IF NEW.fechaNacimiento > CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fecha inválida';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_telefono_cliente` BEFORE INSERT ON `clientes` FOR EACH ROW BEGIN
    IF SUBSTRING(NEW.telefono,1,1) NOT IN ('8','7','6') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Teléfono inválido';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrenadores`
--

CREATE TABLE `entrenadores` (
  `idEntrenador` int NOT NULL,
  `nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `primerApellido` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `segundoApellido` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `telefono` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `correo` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  `especialidades` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `entrenadores`
--

INSERT INTO `entrenadores` (`idEntrenador`, `nombre`, `primerApellido`, `segundoApellido`, `telefono`, `correo`, `especialidades`) VALUES
(2, 'Ana', 'Jimenez', 'Vega', '72223344', 'ana@mail.com', 'Zumba'),
(3, 'Carlos', 'Mendez', 'Lopez', '88887777', 'carlos@mail.com', 'Crossfit'),
(4, 'Ana', 'Jimenez', 'Vega', '72223344', 'ana@mail.com', 'Zumba'),
(5, 'Miguel', 'Rojas', 'Fernandez', '83334444', 'miguel@mail.com', 'Cardio'),
(6, 'Sofia', 'Vargas', 'Lopez', '72225555', 'sofia@mail.com', 'Pilates'),
(8, 'Karla', 'Soto', 'Jimenez', '89996666', 'karla@mail.com', 'Artes Marciales'),
(9, 'David', 'AAAAAAAAAA', 'Montero', '83335555', 'david@mail.com', 'Fitness');

--
-- Disparadores `entrenadores`
--
DELIMITER $$
CREATE TRIGGER `validar_correo_entrenador` BEFORE INSERT ON `entrenadores` FOR EACH ROW BEGIN
    IF NEW.correo NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.com$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Correo inválido';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `validar_telefono_entrenador` BEFORE INSERT ON `entrenadores` FOR EACH ROW BEGIN
    IF SUBSTRING(NEW.telefono,1,1) NOT IN ('8','7','6') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Teléfono inválido';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historialpagos`
--

CREATE TABLE `historialpagos` (
  `idPago` int NOT NULL,
  `cedulaCliente` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `fechaPago` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historialpagos`
--

INSERT INTO `historialpagos` (`idPago`, `cedulaCliente`, `fechaPago`) VALUES
(11, '305660224', '2025-12-08 00:00:00'),
(12, '305660224', '2025-12-08 00:00:00'),
(13, '305660224', '2025-12-08 00:00:00'),
(14, '800000001', '2025-12-08 00:00:00'),
(15, '305660224', '2025-12-08 00:00:00'),
(16, '305660224', '2025-12-08 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `membresias`
--

CREATE TABLE `membresias` (
  `idMembresia` int NOT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `duracionDias` int NOT NULL,
  `beneficios` text COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `membresias`
--

INSERT INTO `membresias` (`idMembresia`, `nombre`, `precio`, `duracionDias`, `beneficios`) VALUES
(1, 'BasicFit', 0.00, 30, 'Acceso ilimitado a sala de máquinas. No permite agendar clases. Sin beneficios adicionales.'),
(2, 'Fit', 15000.00, 30, 'Acceso ilimitado a sala de máquinas. Permite agendar hasta 2 clases por semana. Acceso a clases básicas.'),
(3, 'DeluxeFit', 30000.00, 30, 'Acceso total a sala de máquinas. Permite agendar hasta 5 clases por semana. Acceso a todas las clases del gimnasio. Prioridad de cupo en clases.'),
(4, 'PremiumFit', 40000.00, 30, 'Acceso total a sala de máquinas. Permite agendar todas las clases que quiera por semana. Prioridad de cupo en clases.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `idRol` int NOT NULL,
  `tipoRol` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`idRol`, `tipoRol`) VALUES
(1, 'ADMINISTRADOR'),
(4, 'CLIENTE'),
(2, 'ENTRENADOR'),
(3, 'SECRETARIA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL,
  `usuario` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `contrasena` varbinary(255) DEFAULT NULL,
  `idRol` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `usuario`, `contrasena`, `idRol`) VALUES
(1, 'admin', 0x32343062653531386661626432373234646462366630346565623164613539363734343864376538333163303863386661383232383039663734633732306139, 1),
(2, 'entrenador1', 0x35623364323634653463646332633339636136373038623365316532316630383237323262653132653633656532313438346264626531353733356162303636, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clases`
--
ALTER TABLE `clases`
  ADD PRIMARY KEY (`idClase`),
  ADD KEY `fk_clase_entrenador` (`idEntrenador`);

--
-- Indices de la tabla `clienteclase`
--
ALTER TABLE `clienteclase`
  ADD PRIMARY KEY (`cedulaCliente`,`idClase`),
  ADD KEY `fk_cc_clase` (`idClase`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cedula`),
  ADD KEY `fk_cliente_membresia` (`tipoMembresia`);

--
-- Indices de la tabla `entrenadores`
--
ALTER TABLE `entrenadores`
  ADD PRIMARY KEY (`idEntrenador`);

--
-- Indices de la tabla `historialpagos`
--
ALTER TABLE `historialpagos`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `fk_pago_cliente` (`cedulaCliente`);

--
-- Indices de la tabla `membresias`
--
ALTER TABLE `membresias`
  ADD PRIMARY KEY (`idMembresia`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`idRol`),
  ADD UNIQUE KEY `tipoRol` (`tipoRol`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `fk_usuario_rol` (`idRol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clases`
--
ALTER TABLE `clases`
  MODIFY `idClase` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `entrenadores`
--
ALTER TABLE `entrenadores`
  MODIFY `idEntrenador` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `historialpagos`
--
ALTER TABLE `historialpagos`
  MODIFY `idPago` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `membresias`
--
ALTER TABLE `membresias`
  MODIFY `idMembresia` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `idRol` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `clases`
--
ALTER TABLE `clases`
  ADD CONSTRAINT `fk_clase_entrenador` FOREIGN KEY (`idEntrenador`) REFERENCES `entrenadores` (`idEntrenador`);

--
-- Filtros para la tabla `clienteclase`
--
ALTER TABLE `clienteclase`
  ADD CONSTRAINT `fk_cc_clase` FOREIGN KEY (`idClase`) REFERENCES `clases` (`idClase`),
  ADD CONSTRAINT `fk_cc_cliente` FOREIGN KEY (`cedulaCliente`) REFERENCES `clientes` (`cedula`);

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `fk_cliente_membresia` FOREIGN KEY (`tipoMembresia`) REFERENCES `membresias` (`idMembresia`);

--
-- Filtros para la tabla `historialpagos`
--
ALTER TABLE `historialpagos`
  ADD CONSTRAINT `fk_pago_cliente` FOREIGN KEY (`cedulaCliente`) REFERENCES `clientes` (`cedula`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`idRol`) REFERENCES `roles` (`idRol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
