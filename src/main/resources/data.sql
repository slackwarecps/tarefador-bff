INSERT INTO USUARIO(nome, email, senha) VALUES('Aluno', 'aluno@email.com', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');

INSERT INTO CURSO(nome, categoria) VALUES('Spring Boot', 'Programação');
INSERT INTO CURSO(nome, categoria) VALUES('HTML 5', 'Front-end');

INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES('Dúvida', 'Erro ao criar projeto', '2019-05-05 18:00:00', 'NAO_RESPONDIDO', 1, 1);
INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES('Dúvida 2', 'Projeto não compila', '2019-05-05 19:00:00', 'NAO_RESPONDIDO', 1, 1);
INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES('Dúvida 3', 'Tag HTML', '2019-05-05 20:00:00', 'NAO_RESPONDIDO', 1, 2);

INSERT INTO perfil(nome) VALUES( 'USER');
INSERT INTO PERFIL(nome) VALUES( 'GERAL');

INSERT INTO tarefa_facil.usuario_perfis (usuario_id, perfis_id) VALUES(1, 1);

-- lista 
INSERT INTO tarefa_facil.lista (id, descricao) VALUES(1, 'Primeira Lista');


-- perguntas
INSERT INTO tarefa_facil.pergunta ( descricao, obrigatoria, pergunta_status, pergunta_tipo, peso_pergunta, lista_id) 
VALUES( 'asdasd', 1, 'RESPONDIDA', 'BOOLEAN', 0, 1);
INSERT INTO tarefa_facil.pergunta ( descricao, obrigatoria, pergunta_status, pergunta_tipo, peso_pergunta, lista_id) 
VALUES( 'asdasd', 1, 'RESPONDIDA', 'ESCOLHA_SIMPLES', 0, 1);
INSERT INTO tarefa_facil.pergunta ( descricao, obrigatoria, pergunta_status, pergunta_tipo, peso_pergunta, lista_id) 
VALUES( 'asdasd', 1, 'RESPONDIDA', 'NUMERO_INTEIRO', 0, 1);
INSERT INTO tarefa_facil.pergunta ( descricao, obrigatoria, pergunta_status, pergunta_tipo, peso_pergunta, lista_id) 
VALUES( 'asdasd', 1, 'RESPONDIDA', 'TEXTO_LIVRE', 0, 1);


INSERT INTO tarefa_facil.rede (id, descricao, ativa) VALUES(1, 'Dalben Teste', 1);


INSERT INTO tarefa_facil.loja (id, descricao, rede_id) VALUES(1, 'Taquaral', 1);
INSERT INTO tarefa_facil.loja (id, descricao, rede_id) VALUES(2, 'Barao Geraldo', 1);
INSERT INTO tarefa_facil.loja (id, descricao, rede_id) VALUES(3, 'Mansoes Santo Antonio', 1);


INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(1, 'Zezinho', 1);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(2, 'Tiao', 2);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(3, 'Chuck Norris', 3);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(4, 'Isabela', 2);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(5, 'Tatiana', 3);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(6, 'Juliana', 3);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(7, 'Thiago', 3);
INSERT INTO tarefa_facil.operador (id, nome, loja_id) VALUES(8, 'Daniel', 3);










