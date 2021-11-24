CREATE TABLE pessoa(
    id   BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    logradouro VARCHAR(50),
    numero VARCHAR(50),
    bairro VARCHAR(50),
    cep VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome) values ('Jão');
INSERT INTO pessoa (nome) values ('Nana');
INSERT INTO pessoa (nome) values ('Lala');
INSERT INTO pessoa (nome) values ('Toto');
INSERT INTO pessoa (nome) values ('Pito');
INSERT INTO pessoa (nome, logradouro, numero, bairro, cep, cidade, estado) VALUES ('Truta', 'Travessa Atravessia', '450', 'Vista Vistosa', '74185296', 'goiania', 'goias');
INSERT INTO pessoa (nome, logradouro, numero, bairro, cep, cidade, estado) VALUES ('Pafuncio', 'Av. Atravessia', '610', 'Morro Bonito', '70000000', 'caldas novas', 'goias');
INSERT INTO pessoa (nome, logradouro, numero, bairro, cep, cidade, estado) VALUES ('Cátia', 'Rua Ladrilhada', '666', 'Palmadares', '80000000', 'ululândia', 'programaquistão');
INSERT INTO pessoa (nome, logradouro, numero, bairro, cep, cidade, estado) VALUES ('Neide', 'Void Stret', '000', 'Dark Neighborhood', '66556655', 'Someplace', 'somecountry');
INSERT INTO pessoa (nome, logradouro, numero, bairro, cep, cidade, estado) VALUES ('Lindia', 'one street', '001', 'one land', '66556655', 'one city', 'one country');