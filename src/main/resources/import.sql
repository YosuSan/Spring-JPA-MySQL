INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Pepito', 'Grillo', 'grillorreo@hotmail.com', '2020-03-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jose', 'Sanjuan', 'yosua13@hotmail.com', '2020-06-24', '');



INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic LCD', '300', NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony camara digital', '150', NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple ipod chungo', '30', NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony notebook 14', '650', NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Multifuncion HP', '98', NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bicicleta con ruedas', '1250', NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Cómoda con 86 cajones', '369852', NOW());


INSERT INTO facturas (descripcion, observaciones, cliente_id, create_at) VALUES('Factura equipos oficina', null, '1', NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observaciones, cliente_id, create_at) VALUES('Factura bicicleta', 'Observacion importante', '1', NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

INSERT INTO users (username, password, enabled) VALUES('jose', '$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');

