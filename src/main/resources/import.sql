INSERT INTO members (version, created_at, updated_at, phone, name, email, password, created_by, updated_by) VALUES (0, '2024-05-30 01:15:04.391197', NULL, '0912345678', 'system admin', 'system_admin@e-commerce.org', '$2a$10$eYcuJD9FzL0X6t3WQJ3Ub.u33S7jWOKqrVWEgHnpbXwPoDoH4VRQW', 'system admin', NULL);
INSERT INTO members (version, created_at, updated_at, phone, name, email, password, created_by, updated_by) VALUES (0, '2024-05-30 01:14:24.133902', NULL, '0912345678', 'normal member', 'normal_member@e-commerce.org', '$2a$10$pSwvO3Rv5Fxx5jUW4XYVkup/Nl7ocdV9/yus.UODqvGIFb9U0Sifm', 'system admin', NULL);
INSERT INTO members (version, created_at, updated_at, phone, name, email, password, created_by, updated_by) VALUES (0, '2024-05-30 01:18:06.558544', NULL, '0912345678', 'product seller', 'product_seller@e-commerce.org', '$2a$10$.va11DNVT.sJQhdAKvjuF.BSWgvqaIW6FfSY9V201GEM386GnfWYK', 'system admin', NULL);

INSERT INTO roles (version, created_at, updated_at, created_by, name, updated_by) VALUES (0, '2024-05-30 01:20:11.000000', null, 'system admin', 'ROLE_SYSTEM_ADMIN', null);
INSERT INTO roles (version, created_at, updated_at, created_by, name, updated_by) VALUES (0, '2024-05-30 01:22:05.000000', null, 'system admin', 'ROLE_NORMAL_MEMBER', null);
INSERT INTO roles (version, created_at, updated_at, created_by, name, updated_by) VALUES (0, '2024-05-30 01:23:12.000000', null, 'system admin', 'ROLE_PRODUCT_SELLER', null);

INSERT INTO member_role (member_id, role_id) VALUES (1, 1);
INSERT INTO member_role (member_id, role_id) VALUES (2, 2);
INSERT INTO member_role (member_id, role_id) VALUES (3, 3);
