DELETE FROM pessoa;
DROP TABLE pessoa;

CREATE TABLE pessoa(
                       id   BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
                       ativo BOOLEAN NOT NULL,
                       nome VARCHAR(50) NOT NULL,
                       logradouro VARCHAR(50),
                       numero VARCHAR(50),
                       bairro VARCHAR(50),
                       cep VARCHAR(50),
                       cidade VARCHAR(50),
                       estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo) values ('Jão', false);
INSERT INTO pessoa (nome, ativo) values ('Nana', false);
INSERT INTO pessoa (nome, ativo) values ('Lala', true);
INSERT INTO pessoa (nome, ativo) values ('Toto', false);
INSERT INTO pessoa (nome, ativo) values ('Pito', true);
INSERT INTO pessoa (nome, ativo,logradouro, numero, bairro, cep, cidade, estado) VALUES ('Truta', true, 'Travessa Atravessia', '450', 'Vista Vistosa', '74185296', 'goiania', 'goias');
INSERT INTO pessoa (nome, ativo,logradouro, numero, bairro, cep, cidade, estado) VALUES ('Pafuncio', true, 'Av. Atravessia', '610', 'Morro Bonito', '70000000', 'caldas novas', 'goias');
INSERT INTO pessoa (nome, ativo,logradouro, numero, bairro, cep, cidade, estado) VALUES ('Cátia', true, 'Rua Ladrilhada', '666', 'Palmadares', '80000000', 'ululândia', 'programaquistão');
INSERT INTO pessoa (nome, ativo,logradouro, numero, bairro, cep, cidade, estado) VALUES ('Neide', false, 'Void Stret', '000', 'Dark Neighborhood', '66556655', 'Someplace', 'somecountry');
INSERT INTO pessoa (nome, ativo,logradouro, numero, bairro, cep, cidade, estado) VALUES ('Lindia', false, 'one street', '001', 'one land', '66556655', 'one city', 'one country');