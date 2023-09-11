INSERT INTO product.product(name, description, is_in_stock) VALUES ('PNAME1', 'PDescription1', TRUE);
INSERT INTO product.product(name, description, is_in_stock) VALUES ('PNAME2', 'PDescription2', TRUE);
INSERT INTO product.product(name, description, is_in_stock) VALUES ('PNAME3', 'PDescription3', FALSE);

INSERT INTO product.warehouse(name, zip_code) VALUES ('WAREHOUSE_100', '33544');
INSERT INTO product.warehouse(name, zip_code) VALUES ('WAREHOUSE_100', '35444');
INSERT INTO product.warehouse(name, zip_code) VALUES ('WAREHOUSE_200', '90120');

INSERT INTO product.product_warehouse(product_id, warehouse_id) VALUES (1, 1);
INSERT INTO product.product_warehouse(product_id, warehouse_id) VALUES (2, 1);