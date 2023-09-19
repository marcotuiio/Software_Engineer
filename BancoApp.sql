create schema clientes;

use clientes;

create table CartaoGeral (
	numUnico int,
	codigoNFC varchar(100),
    isEstudante boolean default false,
    saldoCredito int default 0,
    valorCredito numeric default 4.80,
    primary key(numUnico)
    -- constraint ck_valorCredito check (valorCredito = 2.40 or valorCredito = 4.80)
);

create table Usuario (
	cpf varchar(11),
	nome varchar(100) not null,
    login varchar(100) not null,
    senha varchar(100) not null,
    cep varchar(10),
    endereco varchar(100),
    numUnicoCartao int,
    primary key(cpf), 
    constraint fk_numUnico_cartao foreign key(numUnicoCartao) references CartaoGeral(numUnico),
    constraint uk_numUnico_cartao unique(numUnicoCartao)
);

-- Inserir dados fictícios na tabela CartaoGeral
INSERT INTO CartaoGeral (numUnico, codigoNFC, isEstudante, saldoCredito, valorCredito)
VALUES
    (1, 'ABC123', true, 100, 2.40),
    (2, 'DEF456', false, 50, 4.80),
    (3, 'GHI789', false, 75, 4.80);

-- Inserir dados fictícios na tabela Usuario
INSERT INTO Usuario (cpf, nome, login, senha, cep, endereco, numUnicoCartao)
VALUES
    ('12345678901', 'João Silva', 'joao123', 'senha123', '12345-678', 'Rua A, 123', 1),
    ('98765432109', 'Maria Santos', 'maria456', 'senha456', '54321-876', 'Avenida B, 456', 2),
    ('11122233344', 'Pedro Pereira', 'pedro789', 'senha789', '98765-432', 'Rua C, 789', 3);

select * from cartaogeral;
select * from usuario