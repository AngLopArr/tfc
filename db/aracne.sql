-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-04-2025 a las 15:03:53
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aracne`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha_agregado` datetime(6) DEFAULT NULL,
  `talla` varchar(255) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `id_producto` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `email`, `name`, `password`, `username`) VALUES
(1, 'cliente@gmail.com', 'Carla', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'Carlita');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `devoluciones`
--

CREATE TABLE `devoluciones` (
  `id_devolucion` bigint(20) NOT NULL,
  `aceptada` bit(1) DEFAULT NULL,
  `fecha_devolucion` datetime(6) DEFAULT NULL,
  `motivo_devolucion` varchar(255) DEFAULT NULL,
  `id_cliente` bigint(20) NOT NULL,
  `id_pedido` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id_empleado` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id_empleado`, `email`, `name`, `password`, `role`) VALUES
(1, 'admin@gmail.com', 'Carmen Martínez', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'admin'),
(2, 'manuelgomez@gmail.com', 'Manuel Gómez', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'employee'),
(3, 'luisgarcia@gmail.com', 'Luis García', 'b3ec50d758f26460152ed846fb48fe61fb6a0778f490b3d5bbd23017c81f868b', 'employee'),
(4, 'martasuarz@gmail.com', 'Marta Suárez', '2f150a981d2a2dd656435fad3befd5549b55d1b5cb005f1400de92ba31ebcb9c', 'admin'),
(9, 'pepe@gmail.com', 'Pepe Gonzalez', '28bb6b46dfbb459f80e89fd315a596b2f2b7c0e45273de4737e0032fd4ee4b12', 'employee');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `id_producto` bigint(20) NOT NULL,
  `image` varchar(2048) DEFAULT NULL,
  `l` int(11) NOT NULL,
  `m` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `s` int(11) NOT NULL,
  `xl` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`id_producto`, `image`, `l`, `m`, `name`, `price`, `s`, `xl`) VALUES
(17, 'https://static.kiabi.es/images/pantalon-de-lino-purpura-ded73_1_hd1.jpg?width=800', 2, 2, 'Pantalones', 12.55, 2, 2),
(18, 'https://static.kiabi.es/images/pantalon-de-lino-purpura-ded73_1_hd1.jpg?width=800', 16, 11, 'Pantalones de lino púrpuras', 12.55, 8, 20),
(20, 'https://static.kiabi.es/images/pantalon-de-lino-purpura-ded73_1_hd1.jpg?width=800', 15, 0, 'Pantalones de lino violetas', 16.87, 12, 0),
(22, 'https://dam.elcorteingles.es/producto/www-8434685497732-00.jpg?impolicy=Resize&width=967&height=1200', 6, 15, 'Camiseta blanca básica', 12.55, 21, 16),
(25, 'https://static.kiabi.es/images/pantalon-de-lino-purpura-ded73_1_hd1.jpg?width=800', 12, 15, 'Pantalones de lino', 14.55, 12, 0),
(28, 'https://m.media-amazon.com/images/I/61FkCXfR8gL._AC_UY1000_.jpg', 0, 35, 'Falda beige', 18.55, 16, 0),
(29, 'https://i.etsystatic.com/52339591/r/il/3fa75b/6021812050/il_fullxfull.6021812050_e8d2.jpg', 26, 15, 'Vestido azul royal ', 54.85, 0, 19),
(30, 'https://fiftyoutlet.com/dw/image/v2/AAYL_PRD/on/demandware.static/-/Sites-gc-spf-master-catalog/default/dwbab08498/images/hi-res/P_044702621FM.jpg?sw=600&sh=900&sm=fit', 17, 13, 'Pantalones de lino verdes', 23.95, 16, 19),
(31, 'https://cdn.palbincdn.com/users/30665/images/IMG_20230216_231150-1676585663.jpg', 65, 87, 'Pantalones vaqueros azul intermedio anchos', 32, 54, 75),
(32, 'https://m.media-amazon.com/images/I/61sPxoSftVL._AC_UY1000_.jpg', 14, 14, 'Pantalones vaqueros azul claro anchos', 32, 13, 24),
(33, 'https://img1.shopcider.com/product/1739247747000-KGZGhT.jpg?x-oss-process=image/resize,w_700,m_lfit/quality,Q_60/format,webp', 43, 12, 'Pantalones vaqueros negros anchos con bolsillos laterales', 34.5, 54, 74),
(34, 'https://media.boohoo.com/i/boohoo/fzz57769_white_xl_3/mujer-top-de-tela-con-escote-bardot-y-mangas-anchas', 23, 53, 'Top corset blanco con mangas bombacho', 21.55, 42, 15),
(35, 'https://img.kwcdn.com/product/Fancyalgo/VirtualModelMatting/76b06d015439d07ec3c1b8db56840bfb.jpg?imageMogr2/auto-orient%7CimageView2/2/w/800/q/70/format/webp', 15, 42, 'Blusa blanca ancha de manga intermedia con estampado de letras negra ', 18.55, 21, 52),
(36, 'https://i5.walmartimages.com/asr/3e8bcc70-0184-4f7e-8419-dfb4dea9108c.a1e3b7b48b61230ba4f8937cbe53f008.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF', 31, 15, 'Mono beige con estampado de letras grandes en negro', 27.55, 17, 61),
(37, 'https://babaroni.es/cdn/shop/files/Alaia-1.jpg?v=1686814966&width=533', 16, 0, 'Vestido lila de pecho entallado y cuello cerrado', 28.5, 15, 5),
(38, 'https://sorellasthebrand.com/cdn/shop/products/image_e7ef2fd9-c7bc-4bdd-a693-bf26e19b7f86.jpg?v=1651618298', 34, 61, 'Mono lila torso entallado y tiras finas', 26.5, 0, 21),
(39, 'https://media.boohoo.com/i/boohoo/tzz91848_black_xl_1/mujer-camisa-ancha-de-cuadros-vichy-alta', 18, 31, 'Camisa ancha de cuadros blanca y negra', 28.5, 17, 21),
(40, 'https://anoei.com/wp-content/uploads/2024/11/1-600x783.jpg', 14, 17, 'Camisa beige ancha de lino', 25.5, 15, 18),
(41, 'https://www.lolitamoda.com/uploads/photo/image/141108/gallery_M143727_1.JPG', 42, 42, 'Camisa ancha negra de lino con bolsillo', 18.55, 18, 0),
(42, 'https://i.etsystatic.com/5641614/r/il/2774e2/1571035675/il_fullxfull.1571035675_66wg.jpg', 62, 32, 'Vestido rosa palo estilo griego', 31.55, 26, 21),
(43, 'https://www.atributo.co/cdn/shop/files/232.png?crop=center&height=600&v=1693517212&width=480', 0, 51, 'Vestido azul marino de escote cruzado', 31.55, 0, 21),
(44, 'https://www.apparentia.com/views/resources/img/productos/20360/l/vestido-halter-cuello-cadena-crepe-rosa-palo-invitada-boda-bautizo-comunion-graduacion-fiesta-apparentia-comprar.jpg', 21, 0, 'Vestido de satén rosa palo entallado de cuello alto', 31.5, 15, 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_pedido` bigint(20) NOT NULL,
  `fecha_pedido` datetime(6) DEFAULT NULL,
  `total_pedido` double NOT NULL,
  `id_cliente` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_devoluciones`
--

CREATE TABLE `productos_devoluciones` (
  `id` bigint(20) NOT NULL,
  `cantidad_devuelta` int(11) NOT NULL,
  `talla` varchar(255) DEFAULT NULL,
  `id_devolucion` bigint(20) DEFAULT NULL,
  `id_producto` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_pedidos`
--

CREATE TABLE `productos_pedidos` (
  `id_pedidos_productos` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `talla` varchar(255) DEFAULT NULL,
  `id_pedido` bigint(20) DEFAULT NULL,
  `id_producto` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKejbxqccmyx2xejj37wtf6ioge` (`id_cliente`),
  ADD KEY `FKha7nxvx1phrmhfl13pfcwbwrr` (`id_producto`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `UK1c96wv36rk2hwui7qhjks3mvg` (`email`),
  ADD UNIQUE KEY `UKtnw6h0blmf11ywd5k67ll1a1b` (`username`);

--
-- Indices de la tabla `devoluciones`
--
ALTER TABLE `devoluciones`
  ADD PRIMARY KEY (`id_devolucion`),
  ADD KEY `FK4qhit1245x3j959pkacnxyjtq` (`id_cliente`),
  ADD KEY `FK61udund2rmjgvrb6rl9plqqqk` (`id_pedido`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id_empleado`),
  ADD UNIQUE KEY `UK6fdpo2x5rmegfbngre7xb3yoh` (`email`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`id_producto`),
  ADD UNIQUE KEY `UKl1hkpxwlnrp6gpjbew5tskf7g` (`name`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `FKdnomiluem4t3x66t6b9aher47` (`id_cliente`);

--
-- Indices de la tabla `productos_devoluciones`
--
ALTER TABLE `productos_devoluciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm9pvf5vbcmcahfy5uhf76hbjv` (`id_devolucion`),
  ADD KEY `FKoecsadc8g2g2ys4feyppvfsb7` (`id_producto`);

--
-- Indices de la tabla `productos_pedidos`
--
ALTER TABLE `productos_pedidos`
  ADD PRIMARY KEY (`id_pedidos_productos`),
  ADD KEY `FKgvy13jpycywx60hasm7k3w2f` (`id_pedido`),
  ADD KEY `FKcjmj6ybu4wwo9ikfitlhwwduk` (`id_producto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `devoluciones`
--
ALTER TABLE `devoluciones`
  MODIFY `id_devolucion` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id_empleado` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `id_producto` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_pedido` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos_devoluciones`
--
ALTER TABLE `productos_devoluciones`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos_pedidos`
--
ALTER TABLE `productos_pedidos`
  MODIFY `id_pedidos_productos` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `FKejbxqccmyx2xejj37wtf6ioge` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`),
  ADD CONSTRAINT `FKha7nxvx1phrmhfl13pfcwbwrr` FOREIGN KEY (`id_producto`) REFERENCES `inventario` (`id_producto`);

--
-- Filtros para la tabla `devoluciones`
--
ALTER TABLE `devoluciones`
  ADD CONSTRAINT `FK4qhit1245x3j959pkacnxyjtq` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`),
  ADD CONSTRAINT `FK61udund2rmjgvrb6rl9plqqqk` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `FKdnomiluem4t3x66t6b9aher47` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`);

--
-- Filtros para la tabla `productos_devoluciones`
--
ALTER TABLE `productos_devoluciones`
  ADD CONSTRAINT `FKm9pvf5vbcmcahfy5uhf76hbjv` FOREIGN KEY (`id_devolucion`) REFERENCES `devoluciones` (`id_devolucion`),
  ADD CONSTRAINT `FKoecsadc8g2g2ys4feyppvfsb7` FOREIGN KEY (`id_producto`) REFERENCES `inventario` (`id_producto`);

--
-- Filtros para la tabla `productos_pedidos`
--
ALTER TABLE `productos_pedidos`
  ADD CONSTRAINT `FKcjmj6ybu4wwo9ikfitlhwwduk` FOREIGN KEY (`id_producto`) REFERENCES `inventario` (`id_producto`),
  ADD CONSTRAINT `FKgvy13jpycywx60hasm7k3w2f` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
