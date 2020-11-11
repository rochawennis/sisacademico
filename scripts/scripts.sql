CREATE TABLE "tb_curso"(
    "idCurso" INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    "nomeCurso" VARCHAR(80) NOT NULL,
    "tipoCurso" VARCHAR(80)
);

CREATE TABLE "tb_aluno" (
    "idAluno" INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    "nomeAluno" VARCHAR(100) NOT NULL,
    "raAluno" BIGINT NOT NULL,
    "idCurso" INT,
    CONSTRAINT fk_aluno_curso FOREIGN KEY("idCurso") REFERENCES "tb_curso"
);

CREATE TABLE "tb_tipoUsuario"(
    "idTipo" INT NOT NULL PRIMARY KEY 
        GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    "tipo" VARCHAR(10)
);


INSERT INTO "tb_tipoUsuario" ("tipo") VALUES ('admin');
INSERT INTO "tb_tipoUsuario" ("tipo") VALUES ('user');


CREATE TABLE "tb_usuario"(
    "idUsuario" INT NOT NULL PRIMARY KEY 
        GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    "email" VARCHAR(20) NOT NULL,
    "senha" VARCHAR(256) NOT NULL,
    "idTipoUsuario" INT NOT NULL,
    CONSTRAINT fk_tipo FOREIGN KEY("idTipoUsuario") 
        REFERENCES "tb_tipoUsuario"
);

--A senha de ambos é "Senha123", criptografada com SHA-256:
INSERT INTO "tb_usuario" ("email", "senha", "idTipoUsuario") 
	VALUES ('admin@uninove.br', 'c00357563669ed21c34e13687cad669038eb88a2831fc8109b40ddc62f63e934', 1);
INSERT INTO "tb_usuario" ("email", "senha", "idTipoUsuario") 
	VALUES ('user@uninove.br', 'c00357563669ed21c34e13687cad669038eb88a2831fc8109b40ddc62f63e934', 2);
