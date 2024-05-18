
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
    nome           VARCHAR(255)   NOT NULL,
    descricao      VARCHAR(255)   NOT NULL,
    referencia     VARCHAR(255)   NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL,
    id_categoria   BIGINT         NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categorias (id)
);

