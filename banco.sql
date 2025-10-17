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

CREATE TABLE IF NOT EXISTS carrinho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigoPedido VARCHAR(20),
    comercio VARCHAR(100),
    cnpj VARCHAR(18),
    telefoneComercio VARCHAR(18),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf VARCHAR(10),
    rua VARCHAR(100),
    numero VARCHAR(10),
    operador VARCHAR(100),
    cpfConsumidor VARCHAR(14),
    formaPagamento VARCHAR(20)
    volume DOUBLE,
    quantidade INT NOT NULL,
    pagamento DOUBLE(10,2) NOT NULL,
 total DOUBLE (10,2)NOT NULL,
 troco DOUBLE (10,2) NOT NULL
);