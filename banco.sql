CREATE DATABASE IF NOT EXISTS comercio;
USE comercio;

CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigobarras VARCHAR(100) NOT NULL UNIQUE, -- Evita duplicidade de código
    valorcompra DOUBLE(10,2),                  -- Definindo precisão
    estoque INT NOT NULL,
    fornecedor VARCHAR(100),
    telefoneforne VARCHAR(16),
    preco DOUBLE(10,2) NOT NULL
);