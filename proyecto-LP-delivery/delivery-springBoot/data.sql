-- ============================================
-- SCRIPT DE DATOS INICIALES PARA DELIVERY APP
-- ============================================
-- 
-- Este script inserta datos de ejemplo en todas las tablas de la base de datos.
-- 
-- IMPORTANTE: Si encuentras errores de nombres de tablas/columnas, verifica los nombres
-- reales en tu base de datos ejecutando: SHOW TABLES; y DESCRIBE nombre_tabla;
-- 
-- Los nombres de tablas y columnas siguen la convención de Hibernate:
-- - Tablas: nombres de clase en minúsculas (Empresa -> empresa)
-- - Columnas: camelCase convertido a snake_case (placaMoto -> placa_moto)
-- 
-- IMÁGENES:
-- - Productos y Categorías: Imágenes reales de Unsplash (https://unsplash.com)
-- - Fotos de Clientes: Avatares de Pravatar (https://pravatar.cc)
-- Todas las imágenes son gratuitas y de uso libre para desarrollo.
-- 
-- ============================================

-- Desactivar verificaciones de claves foráneas temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- 1. EMPRESAS
-- ============================================
INSERT INTO empresa (nombre, ruc, direccion, telefono, correo) VALUES
('Al Toque Delivery', '20123456789', 'Av. Principal 123, Lima', '987654321', 'contacto@altoque.com'),
('Rápido Express', '20198765432', 'Jr. Comercio 456, Arequipa', '987654322', 'info@rapidoexpress.com'),
('Delicias Express', '20234567890', 'Av. Libertad 789, Trujillo', '987654323', 'ventas@deliciasexpress.com');

-- ============================================
-- 2. ROLES
-- ============================================
INSERT INTO rol (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema con acceso completo'),
('REPARTIDOR', 'Repartidor encargado de entregar pedidos'),
('CLIENTE', 'Cliente que realiza pedidos'),
('GERENTE', 'Gerente de la empresa');

-- ============================================
-- 3. USUARIOS
-- ============================================
INSERT INTO usuario (username, password, activo, rol_id, empresa_id) VALUES
-- Administradores
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 1, 1),
('admin2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 1, 2),
('superadmin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 1, 1),

-- Gerentes
('gerente1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 4, 1),
('gerente2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 4, 2),
('gerente3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 4, 3),
('gerente.inactivo', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', false, 4, 1),

-- Repartidores
('repartidor1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 2, 1),
('repartidor2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 2, 1),
('repartidor3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 2, 2),
('repartidor4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 2, 2),
('repartidor5', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 2, 3),
('repartidor.inactivo', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', false, 2, 1),

-- Clientes
('cliente1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 1),
('cliente2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 1),
('cliente3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 1),
('cliente4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 2),
('cliente5', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 2),
('cliente6', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 3),
('cliente7', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 3),
('cliente.inactivo', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', false, 3, 1),
('test.user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 1),
('demo.cliente', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8p6w2', true, 3, 1);

-- Nota: La contraseña para TODOS los usuarios es "password" encriptada con BCrypt
-- Usuarios inactivos: gerente.inactivo, repartidor.inactivo, cliente.inactivo
-- Estos usuarios no podrán hacer login aunque tengan credenciales correctas

-- ============================================
-- 4. CATEGORÍAS
-- ============================================
INSERT INTO categoria (nombre, descripcion, imagen) VALUES
('Comida Rápida', 'Hamburguesas, papas fritas, pollo frito y más', 'https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800&h=600&fit=crop'),
('Pizzas', 'Pizzas artesanales con ingredientes frescos', 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&h=600&fit=crop'),
('Bebidas', 'Refrescos, jugos naturales y bebidas energéticas', 'https://images.unsplash.com/photo-1544145945-f90425340c7e?w=800&h=600&fit=crop'),
('Postres', 'Tortas, helados, brownies y más dulces', 'https://images.unsplash.com/photo-1551024506-0bccd828d307?w=800&h=600&fit=crop'),
('Asiática', 'Sushi, ramen, pad thai y comida asiática', 'https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800&h=600&fit=crop'),
('Mexicana', 'Tacos, burritos, quesadillas y más', 'https://images.unsplash.com/photo-1565299585323-38174c1a0c0e?w=800&h=600&fit=crop');

-- ============================================
-- 5. PRODUCTOS
-- ============================================
INSERT INTO producto (nombre, descripcion, precio, imagen, disponible, categoria_id, empresa_id) VALUES
-- Comida Rápida
('Hamburguesa Clásica', 'Hamburguesa con carne, lechuga, tomate y queso', 15.50, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop', true, 1, 1),
('Pollo Frito (6 piezas)', 'Pollo frito crujiente con papas fritas', 22.00, 'https://images.unsplash.com/photo-1626645738192-c2a58f5d1c57?w=800&h=600&fit=crop', true, 1, 1),
('Papas Fritas Grandes', 'Papas fritas crujientes con salsas', 8.50, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=800&h=600&fit=crop', true, 1, 1),
('Nuggets de Pollo (10 piezas)', 'Nuggets de pollo empanizados', 18.00, 'https://images.unsplash.com/photo-1606755962773-d324e166a853?w=800&h=600&fit=crop', true, 1, 1),

-- Pizzas
('Pizza Margarita', 'Pizza con tomate, mozzarella y albahaca', 25.00, 'https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=800&h=600&fit=crop', true, 2, 1),
('Pizza Pepperoni', 'Pizza con pepperoni y queso mozzarella', 28.00, 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=800&h=600&fit=crop', true, 2, 1),
('Pizza Hawaiana', 'Pizza con jamón, piña y queso', 27.00, 'https://images.unsplash.com/photo-1594007654729-407eedc4be65?w=800&h=600&fit=crop', true, 2, 1),
('Pizza Cuatro Quesos', 'Pizza con cuatro tipos de queso', 30.00, 'https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?w=800&h=600&fit=crop', true, 2, 1),

-- Bebidas
('Coca Cola 500ml', 'Refresco de cola', 4.50, 'https://images.unsplash.com/photo-1554866585-cd94860890b7?w=800&h=600&fit=crop', true, 3, 1),
('Jugo de Naranja Natural', 'Jugo de naranja recién exprimido', 6.00, 'https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=800&h=600&fit=crop', true, 3, 1),
('Agua Mineral 500ml', 'Agua mineral sin gas', 3.00, 'https://images.unsplash.com/photo-1548839140-5a059c3c5a1a?w=800&h=600&fit=crop', true, 3, 1),
('Limonada Natural', 'Limonada fresca con hierbabuena', 5.50, 'https://images.unsplash.com/photo-1523677011787-c91d1bbe2fdc?w=800&h=600&fit=crop', true, 3, 1),

-- Postres
('Brownie con Helado', 'Brownie caliente con helado de vainilla', 12.00, 'https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800&h=600&fit=crop', true, 4, 1),
('Torta de Chocolate', 'Porción de torta de chocolate', 10.00, 'https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=800&h=600&fit=crop', true, 4, 1),
('Helado de Vainilla', 'Helado de vainilla artesanal', 8.00, 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=800&h=600&fit=crop', true, 4, 1),

-- Asiática
('Sushi Roll California', 'Roll de sushi con cangrejo, aguacate y pepino', 18.00, 'https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800&h=600&fit=crop', true, 5, 1),
('Ramen de Pollo', 'Sopa ramen con pollo, huevo y verduras', 20.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800&h=600&fit=crop', true, 5, 1),
('Pad Thai', 'Fideos tailandeses salteados con camarones', 22.00, 'https://images.unsplash.com/photo-1559314809-0d155014e29e?w=800&h=600&fit=crop', true, 5, 1),

-- Mexicana
('Tacos de Pollo (3 unidades)', 'Tacos de pollo con salsa y verduras', 16.00, 'https://images.unsplash.com/photo-1565299585323-38174c1a0c0e?w=800&h=600&fit=crop', true, 6, 1),
('Burrito de Carne', 'Burrito grande con carne, frijoles y queso', 19.00, 'https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=800&h=600&fit=crop', true, 6, 1),
('Quesadilla de Pollo', 'Quesadilla con pollo y queso derretido', 15.00, 'https://images.unsplash.com/photo-1618040996337-56904b7850b9?w=800&h=600&fit=crop', true, 6, 1);

-- ============================================
-- 6. DIRECCIONES
-- ============================================
INSERT INTO direccion (calle, numero, referencia, distrito, ciudad) VALUES
('Av. Los Olivos', '123', 'Frente al parque', 'Los Olivos', 'Lima'),
('Jr. San Martín', '456', 'Cerca del mercado', 'Miraflores', 'Lima'),
('Av. Arequipa', '789', 'Edificio Los Pinos, Dpto 301', 'San Isidro', 'Lima'),
('Calle Las Begonias', '321', 'Al lado del banco', 'Surco', 'Lima'),
('Av. Brasil', '654', 'Segundo piso', 'Magdalena', 'Lima'),
('Jr. Unión', '987', 'Frente a la iglesia', 'Centro', 'Lima');

-- ============================================
-- 7. CLIENTES
-- ============================================
INSERT INTO cliente (nombre, apellido, dni, telefono, email, foto, direccion_id) VALUES
('Juan', 'Pérez', '12345678', '987654321', 'juan.perez@email.com', 'https://i.pravatar.cc/300?img=12', 1),
('María', 'García', '87654321', '987654322', 'maria.garcia@email.com', 'https://i.pravatar.cc/300?img=47', 2),
('Carlos', 'Rodríguez', '11223344', '987654323', 'carlos.rodriguez@email.com', 'https://i.pravatar.cc/300?img=33', 3),
('Ana', 'López', '44332211', '987654324', 'ana.lopez@email.com', 'https://i.pravatar.cc/300?img=20', 4),
('Luis', 'Martínez', '55667788', '987654325', 'luis.martinez@email.com', 'https://i.pravatar.cc/300?img=51', 5),
('Sofía', 'González', '88776655', '987654326', 'sofia.gonzalez@email.com', 'https://i.pravatar.cc/300?img=45', 6);

-- ============================================
-- 7.1. ASOCIAR USUARIOS CON CLIENTES
-- ============================================
-- Actualizar usuarios para asociarlos con sus clientes correspondientes
-- cliente1 -> Cliente 1 (Juan Pérez)
-- cliente2 -> Cliente 2 (María García)
-- cliente3 -> Cliente 3 (Carlos Rodríguez)
-- cliente4 -> Cliente 4 (Ana López)
-- cliente5 -> Cliente 5 (Luis Martínez)
-- cliente6 -> Cliente 6 (Sofía González)
UPDATE usuario SET cliente_id = 1 WHERE username = 'cliente1';
UPDATE usuario SET cliente_id = 2 WHERE username = 'cliente2';
UPDATE usuario SET cliente_id = 3 WHERE username = 'cliente3';
UPDATE usuario SET cliente_id = 4 WHERE username = 'cliente4';
UPDATE usuario SET cliente_id = 5 WHERE username = 'cliente5';
UPDATE usuario SET cliente_id = 6 WHERE username = 'cliente6';

-- ============================================
-- 8. REPARTIDORES
-- ============================================
INSERT INTO repartidor (nombre, telefono, placa_moto, licencia, disponible, empresa_id) VALUES
('Pedro Sánchez', '987654330', 'ABC-123', 'LIC-001', true, 1),
('Miguel Torres', '987654331', 'XYZ-456', 'LIC-002', true, 1),
('Roberto Vargas', '987654332', 'DEF-789', 'LIC-003', false, 1),
('Fernando Castro', '987654333', 'GHI-012', 'LIC-004', true, 1),
('Diego Ramírez', '987654334', 'JKL-345', 'LIC-005', true, 2),
('Andrés Morales', '987654335', 'MNO-678', 'LIC-006', true, 2);

-- ============================================
-- 9. PEDIDOS
-- ============================================
INSERT INTO pedido (fecha, estado, total, cliente_id, repartidor_id) VALUES
('2024-01-15 10:30:00', 'ENTREGADO', 35.50, 1, 1),
('2024-01-15 12:15:00', 'EN_CAMINO', 52.00, 2, 2),
('2024-01-15 14:20:00', 'PENDIENTE', 28.00, 3, NULL),
('2024-01-15 16:45:00', 'PREPARANDO', 45.00, 4, NULL),
('2024-01-15 18:30:00', 'ENTREGADO', 67.50, 5, 3),
('2024-01-16 09:00:00', 'EN_CAMINO', 22.00, 6, 4),
('2024-01-16 11:30:00', 'PENDIENTE', 40.00, 1, NULL),
('2024-01-16 13:45:00', 'PREPARANDO', 55.00, 2, NULL),
('2024-01-16 15:20:00', 'ENTREGADO', 30.00, 3, 1),
('2024-01-16 17:00:00', 'EN_CAMINO', 48.00, 4, 2);

-- ============================================
-- 10. DETALLES DE PEDIDO
-- ============================================
INSERT INTO detalle_pedido (cantidad, precio_unitario, subtotal, pedido_id, producto_id) VALUES
-- Pedido 1 (35.50)
(1, 15.50, 15.50, 1, 1),
(2, 10.00, 20.00, 1, 13),

-- Pedido 2 (52.00)
(1, 25.00, 25.00, 2, 5),
(1, 18.00, 18.00, 2, 4),
(2, 4.50, 9.00, 2, 9),

-- Pedido 3 (28.00)
(1, 28.00, 28.00, 3, 6),

-- Pedido 4 (45.00)
(1, 22.00, 22.00, 4, 2),
(1, 18.00, 18.00, 4, 4),
(1, 5.00, 5.00, 4, 10),

-- Pedido 5 (67.50)
(2, 25.00, 50.00, 5, 5),
(1, 12.00, 12.00, 5, 13),
(1, 5.50, 5.50, 5, 12),

-- Pedido 6 (22.00)
(1, 22.00, 22.00, 6, 2),

-- Pedido 7 (40.00)
(1, 18.00, 18.00, 7, 15),
(1, 22.00, 22.00, 7, 17),

-- Pedido 8 (55.00)
(1, 30.00, 30.00, 8, 8),
(1, 20.00, 20.00, 8, 16),
(1, 5.00, 5.00, 8, 10),

-- Pedido 9 (30.00)
(1, 27.00, 27.00, 9, 7),
(1, 3.00, 3.00, 9, 11),

-- Pedido 10 (48.00)
(1, 19.00, 19.00, 10, 20),
(1, 15.00, 15.00, 10, 22),
(2, 7.00, 14.00, 10, 13);

-- ============================================
-- 11. PAGOS
-- ============================================
INSERT INTO pago (monto, metodo, estado, fecha_pago, pedido_id) VALUES
(35.50, 'EFECTIVO', 'COMPLETADO', '2024-01-15 10:30:00', 1),
(52.00, 'TARJETA', 'COMPLETADO', '2024-01-15 12:15:00', 2),
(28.00, 'TARJETA', 'PENDIENTE', '2024-01-15 14:20:00', 3),
(45.00, 'EFECTIVO', 'PENDIENTE', '2024-01-15 16:45:00', 4),
(67.50, 'TARJETA', 'COMPLETADO', '2024-01-15 18:30:00', 5),
(22.00, 'EFECTIVO', 'COMPLETADO', '2024-01-16 09:00:00', 6),
(40.00, 'TARJETA', 'PENDIENTE', '2024-01-16 11:30:00', 7),
(55.00, 'TARJETA', 'PENDIENTE', '2024-01-16 13:45:00', 8),
(30.00, 'EFECTIVO', 'COMPLETADO', '2024-01-16 15:20:00', 9),
(48.00, 'TARJETA', 'COMPLETADO', '2024-01-16 17:00:00', 10);

-- ============================================
-- 12. PROMOCIONES
-- ============================================
INSERT INTO promociones (nombre, descripcion, descuento, fecha_inicio, fecha_fin, producto_id) VALUES
('Descuento Pizza', '20% de descuento en todas las pizzas', 20.00, '2024-01-01', '2024-01-31', 5),
('Combo Hamburguesa', '15% de descuento en hamburguesas', 15.00, '2024-01-01', '2024-02-29', 1),
('Promo Bebidas', '10% de descuento en todas las bebidas', 10.00, '2024-01-15', '2024-02-15', 9),
('Descuento Postres', '25% de descuento en postres', 25.00, '2024-01-10', '2024-01-25', 13),
('Promo Asiática', '18% de descuento en comida asiática', 18.00, '2024-01-05', '2024-02-05', 15),
('Descuento Mexicana', '12% de descuento en comida mexicana', 12.00, '2024-01-12', '2024-02-12', 19);

-- Reactivar verificaciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- FIN DEL SCRIPT
-- ============================================

