CREATE TABLE categorias
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo       VARCHAR(255) NOT NULL,
    descricao    VARCHAR(255) NOT NULL,
    codigo       VARCHAR(50)  NOT NULL,
    data_criacao DATE         NOT NULL
);
CREATE TABLE produtos
(
    id             BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR2(255)  NOT NULL,
    descricao      VARCHAR2(255)  NOT NULL,
    referencia     VARCHAR2(255)  NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL,
    id_categoria   BIGINT         NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categorias (id)
);

CREATE TABLE usuarios
(
    id       BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR2(255) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    role     VARCHAR2(255) NOT NULL
);
-- Inserir categorias
INSERT INTO categorias (titulo, descricao, codigo, data_criacao)
VALUES ('Eletrônicos', 'Produtos eletrônicos', 'ELE', CURRENT_DATE);

INSERT INTO categorias (titulo, descricao, codigo, data_criacao)
VALUES ('Alimentos', 'Produtos alimentícios', 'ALI', CURRENT_DATE);

INSERT INTO categorias (titulo, descricao, codigo, data_criacao)
VALUES ('Limpeza', 'Produtos de limpeza', 'LIM', CURRENT_DATE);

-- Inserir produtos
INSERT INTO produtos (nome, descricao, referencia, valor_unitario, id_categoria)
VALUES ('Smartphone', 'Smartphone de última geração', 'SMART1', 2000.00, 1);

INSERT INTO produtos (nome, descricao, referencia, valor_unitario, id_categoria)
VALUES ('TV LED', 'TV LED 55 polegadas', 'TVLED1', 3000.00, 1);

INSERT INTO produtos (nome, descricao, referencia, valor_unitario, id_categoria)
VALUES ('Arroz', 'Arroz branco tipo 1', 'ARZ001', 10.00, 2);

INSERT INTO produtos (nome, descricao, referencia, valor_unitario, id_categoria)
VALUES ('Feijão', 'Feijão carioca', 'FEI001', 8.00, 2);

INSERT INTO produtos (nome, descricao, referencia, valor_unitario, id_categoria)
VALUES ('Sabão em pó', 'Sabão em pó para lavar roupas', 'SAB001', 5.00, 3);

INSERT INTO produtos (nome, descricao, referencia, valor_unitario, id_categoria)
VALUES ('Desinfetante', 'Desinfetante para limpeza', 'DES001', 7.00, 3);
