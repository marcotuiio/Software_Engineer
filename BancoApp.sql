create schema clientes;

use clientes;

create table Usuario (
	cpf varchar(11),
	nome varchar(100) not null,
    login varchar(100) not null,
    senha varchar(100) not null,
    cep varchar(10),
    endereco varchar(100),
    primary key(cpf)
);

create table CartaoGeral (
	numUnico int not null auto_increment,
	codigoNFC varchar(100), -- talvez colocar como not null na hora de criar
    beneficio boolean default false,
    saldoGeral int default 0,
    saldoBeneficio int default 0,
    cpfUser varchar(11),
    primary key(numUnico),
    constraint fk_cpgUser foreign key(cpfUser) references Usuario(cpg),
    constraint uk_cpfUser unique(cpfUser),
    constraint ck_saldoBeneficio check (not (beneficio = false and saldoBeneficio <> 0))
    -- constraint uk_codNFC unique(codigoNFC) 
);

-- Inserir dados fictícios na tabela CartaoGeral
INSERT INTO CartaoGeral (codigoNFC, beneficio, saldoGeral, saldoBeneficio, userCpf)
VALUES
    ('ABC123', true, 100, 0, '12345678901'),
    ('DEF456', false, 50, 0, '98765432109'),
    ('GHI789', false, 75, 0, '11122233344');
    
-- Inserir dados fictícios na tabela Usuario
INSERT INTO Usuario (cpf, nome, login, senha, cep, endereco)
VALUES
    ('12345678901', 'João Silva', 'joao123', 'senha123', '12345-678', 'Rua A, 123'),
    ('98765432109', 'Maria Santos', 'maria456', 'senha456', '54321-876', 'Avenida B, 456'),
    ('11122233344', 'Pedro Pereira', 'pedro789', 'senha789', '98765-432', 'Rua C, 789');

select * from cartaogeral;
select * from usuario;
SELECT * FROM usuario WHERE login = 'joao123'  and senha = 'senha123';

-- TEST AREA

INSERT INTO Usuario (cpf, nome, login, senha, cep, endereco)
VALUES
    ('72932331345', 'Bia Atriz', 'bia666', 'senha921', '65308-992', 'Rua XV, 92');
INSERT INTO CartaoGeral (numUnico, codigoNFC, beneficio, saldoCredito)
VALUES
    (6, 'KSD986', true, 20, '11122233344');
    
-- drop table usuario;
-- drop table cartaogeral; 

-- END TEST AREA