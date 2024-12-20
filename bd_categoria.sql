-- Active: 1734154124621@@localhost@3306@sise
CREATE DATABASE sise;
USE sise;


   CREATE TABLE Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    talla VARCHAR(50) NOT NULL,
    tipo_tela VARCHAR(100) NOT NULL,
    color VARCHAR(50) NOT NULL,
    estilo VARCHAR(100) NOT NULL
);

SELECT * FROM  Categoria





