INSERT INTO  usuario (email, cpf, senha) VALUES ('usuario@email.com', '12345678901', '$2a$10$VAveRxbOEmCSGyKWYK0fV.z/.s/pUqCNcBK9AzRINHMCwaydEy3Iu');
INSERT INTO  usuario (email, cpf, senha) VALUES ('user@email.com', '12345678911', '$2a$10$VAveRxbOEmCSGyKWYK0fV.z/.s/pUqCNcBK9AzRINHMCwaydEy3Iu');

INSERT INTO perfil (nome) VALUES ('Administrador');

INSERT INTO usuario_perfis (usuario_id, perfis_id) VALUES (1, 1);
INSERT INTO usuario_perfis (usuario_id, perfis_id) VALUES (2, 1);
