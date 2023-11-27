create schema LondriBus;

create table LondriBus.Usuario (
	cpf varchar(11),
	nome varchar(100) not null,
    login varchar(100) not null,
    senha varchar(100) not null,
    cep varchar(10),
    endereco varchar(100),
    primary key(cpf)
);

create table LondriBus.CartaoGeral (
	numUnico serial,
	codigoNFC varchar(100), -- talvez colocar como not null na hora de criar
    beneficio boolean default false,
    saldoGeral int default 0,
    saldoBeneficio int default 0,
    cpfUser varchar(11),
    primary key(numUnico),
    constraint fk_cpfUser foreign key(cpfUser) references LondriBus.Usuario(cpf),
    constraint uk_cpfUser unique(cpfUser),
    constraint ck_saldoBeneficio check (not (beneficio = false and saldoBeneficio <> 0))
    -- constraint uk_codNFC unique(codigoNFC) 
);

-- Inserir dados fictícios na tabela Usuario
INSERT INTO LondriBus.Usuario (cpf, nome, login, senha, cep, endereco)
VALUES
    ('12345678901', 'João Silva', 'joao123', 'senha123', '12345-678', 'Rua A, 123'),
    ('98765432109', 'Maria Santos', 'maria456', 'senha456', '54321-876', 'Avenida B, 456'),
    ('11122233344', 'Pedro Pereira', 'pedro789', 'senha789', '98765-432', 'Rua C, 789');

-- Inserir dados fictícios na tabela CartaoGeral
INSERT INTO LondriBus.CartaoGeral (codigoNFC, beneficio, saldoGeral, saldoBeneficio, cpfUser)
VALUES
    ('ABC123', true, 100, 0, '12345678901'),
    ('DEF456', false, 50, 0, '98765432109'),
    ('GHI789', false, 75, 0, '11122233344');
    

select * from LondriBus.cartaogeral;
select * from LondriBus.usuario;
SELECT * FROM LondriBus.usuario WHERE login = 'joao123'  and senha = 'senha123';

-- TEST AREA

INSERT INTO LondriBus.Usuario (cpf, nome, login, senha, cep, endereco)
VALUES
    ('72932331345', 'Bia Atriz', 'bia666', 'senha921', '65308-992', 'Rua XV, 92');
INSERT INTO LondriBus.CartaoGeral (codigoNFC, beneficio, saldoGeral, saldoBeneficio, cpfUser)
VALUES
    ('A1B4T05', false, 10, 0, '72932331345');
    
-- drop table usuario;
-- drop table cartaogeral; 

-- END TEST AREA