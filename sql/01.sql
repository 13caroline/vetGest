-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema vetgest
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema vetgest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vetgest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `vetgest` ;

-- -----------------------------------------------------
-- Table `vetgest`.`animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`animal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `altura` INT NULL DEFAULT NULL,
  `castracao` BIT(1) NOT NULL,
  `cauda` VARCHAR(255) NULL DEFAULT NULL,
  `chip` VARCHAR(255) NULL DEFAULT NULL,
  `cor` VARCHAR(255) NOT NULL,
  `data_nascimento` VARCHAR(255) NOT NULL,
  `especie` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `observacoes` VARCHAR(255) NULL DEFAULT NULL,
  `path` VARCHAR(255) NULL DEFAULT NULL,
  `pelagem` VARCHAR(255) NULL DEFAULT NULL,
  `raca` VARCHAR(255) NOT NULL,
  `sexo` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`utilizador` (
  `dtype` VARCHAR(31) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `concelho` VARCHAR(255) NOT NULL,
  `contacto` VARCHAR(255) NOT NULL,
  `freguesia` VARCHAR(255) NOT NULL,
  `morada` VARCHAR(255) NOT NULL,
  `nif` BIGINT NULL DEFAULT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `iban` VARCHAR(255) NULL DEFAULT NULL,
  `path` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `utilizador_email_unique` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`intervencao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`intervencao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `data_pedido` VARCHAR(255) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `estado` VARCHAR(255) NOT NULL,
  `hora` VARCHAR(255) NOT NULL,
  `motivo` VARCHAR(255) NULL DEFAULT NULL,
  `observacoes` VARCHAR(255) NULL DEFAULT NULL,
  `tipo` VARCHAR(255) NOT NULL,
  `animal_id` INT NULL DEFAULT NULL,
  `veterinario_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKpivjb8vkdvrcj7g56w7jvoerw` (`animal_id` ASC) VISIBLE,
  INDEX `FK5uanw1629ckea8mwtxyo5p1pu` (`veterinario_id` ASC) VISIBLE,
  CONSTRAINT `FK5uanw1629ckea8mwtxyo5p1pu`
    FOREIGN KEY (`veterinario_id`)
    REFERENCES `vetgest`.`utilizador` (`id`),
  CONSTRAINT `FKpivjb8vkdvrcj7g56w7jvoerw`
    FOREIGN KEY (`animal_id`)
    REFERENCES `vetgest`.`animal` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`internamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`internamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `estado` VARCHAR(255) NOT NULL,
  `localizacao` VARCHAR(255) NOT NULL,
  `motivo` VARCHAR(255) NOT NULL,
  `nota` VARCHAR(255) NULL DEFAULT NULL,
  `animal_id` INT NULL DEFAULT NULL,
  `intervencao_id` INT NULL DEFAULT NULL,
  `veterinario_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKjjg40j6b74mfbwx0acwhgsilt` (`animal_id` ASC) VISIBLE,
  INDEX `FKpdydiv518u97rqig4e1w4b17j` (`intervencao_id` ASC) VISIBLE,
  INDEX `FKks76hnd2xfrcnwd0bw7k1mubk` (`veterinario_id` ASC) VISIBLE,
  CONSTRAINT `FKjjg40j6b74mfbwx0acwhgsilt`
    FOREIGN KEY (`animal_id`)
    REFERENCES `vetgest`.`animal` (`id`),
  CONSTRAINT `FKks76hnd2xfrcnwd0bw7k1mubk`
    FOREIGN KEY (`veterinario_id`)
    REFERENCES `vetgest`.`utilizador` (`id`),
  CONSTRAINT `FKpdydiv518u97rqig4e1w4b17j`
    FOREIGN KEY (`intervencao_id`)
    REFERENCES `vetgest`.`intervencao` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`alta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`alta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `evolucao` VARCHAR(255) NULL DEFAULT NULL,
  `historia_medica` VARCHAR(255) NULL DEFAULT NULL,
  `motivo` VARCHAR(255) NULL DEFAULT NULL,
  `orientacao` VARCHAR(255) NULL DEFAULT NULL,
  `proposta_terapeutica` VARCHAR(255) NULL DEFAULT NULL,
  `internamento_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK2ibvna4ffo2etyj165ndpho3i` (`internamento_id` ASC) VISIBLE,
  CONSTRAINT `FK2ibvna4ffo2etyj165ndpho3i`
    FOREIGN KEY (`internamento_id`)
    REFERENCES `vetgest`.`internamento` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`dica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`dica` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `dica` VARCHAR(255) NOT NULL,
  `veterinario_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK7vs91cyfxl43umlvq1rrw283` (`veterinario_id` ASC) VISIBLE,
  CONSTRAINT `FK7vs91cyfxl43umlvq1rrw283`
    FOREIGN KEY (`veterinario_id`)
    REFERENCES `vetgest`.`utilizador` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`historico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`historico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `alergias` BIT(1) NOT NULL,
  `antecedentes` VARCHAR(255) NULL DEFAULT NULL,
  `historia_ginecologica` VARCHAR(255) NULL DEFAULT NULL,
  `historia_medica` VARCHAR(255) NULL DEFAULT NULL,
  `medicacao` VARCHAR(255) NULL DEFAULT NULL,
  `tipo_alergias` VARCHAR(255) NULL DEFAULT NULL,
  `transfusoes` BIT(1) NOT NULL,
  `animal_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK4skfms65xrfyp9rsuqrshan9i` (`animal_id` ASC) VISIBLE,
  CONSTRAINT `FK4skfms65xrfyp9rsuqrshan9i`
    FOREIGN KEY (`animal_id`)
    REFERENCES `vetgest`.`animal` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`imunizacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`imunizacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `data_toma` VARCHAR(255) NULL DEFAULT NULL,
  `estado` VARCHAR(255) NOT NULL,
  `observacoes` VARCHAR(255) NULL DEFAULT NULL,
  `prox_imunizacao` VARCHAR(255) NULL DEFAULT NULL,
  `tipo` VARCHAR(255) NOT NULL,
  `tratamento` VARCHAR(255) NULL DEFAULT NULL,
  `vacina` VARCHAR(255) NULL DEFAULT NULL,
  `animal_id` INT NULL DEFAULT NULL,
  `proxima_imunizacao_id` INT NULL DEFAULT NULL,
  `veterinario_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKn96p462esxr0dgy32fx9cwuvg` (`animal_id` ASC) VISIBLE,
  INDEX `FKdhpwltdhfx7ht77k8b41uhck1` (`proxima_imunizacao_id` ASC) VISIBLE,
  INDEX `FKik5s5lhhdqp3x5sf4u8piw5am` (`veterinario_id` ASC) VISIBLE,
  CONSTRAINT `FKdhpwltdhfx7ht77k8b41uhck1`
    FOREIGN KEY (`proxima_imunizacao_id`)
    REFERENCES `vetgest`.`imunizacao` (`id`),
  CONSTRAINT `FKik5s5lhhdqp3x5sf4u8piw5am`
    FOREIGN KEY (`veterinario_id`)
    REFERENCES `vetgest`.`utilizador` (`id`),
  CONSTRAINT `FKn96p462esxr0dgy32fx9cwuvg`
    FOREIGN KEY (`animal_id`)
    REFERENCES `vetgest`.`animal` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`jwtoken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`jwtoken` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_expiracao` DATETIME(6) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`nota`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`nota` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `intervencao_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKn24b0v06uf5nli48jna3qbfuv` (`intervencao_id` ASC) VISIBLE,
  CONSTRAINT `FKn24b0v06uf5nli48jna3qbfuv`
    FOREIGN KEY (`intervencao_id`)
    REFERENCES `vetgest`.`intervencao` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`nota_internamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`nota_internamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `internamento_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKpk0l90lpxyt8aoefaugdp7fhx` (`internamento_id` ASC) VISIBLE,
  CONSTRAINT `FKpk0l90lpxyt8aoefaugdp7fhx`
    FOREIGN KEY (`internamento_id`)
    REFERENCES `vetgest`.`internamento` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vetgest`.`utilizador_animais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vetgest`.`utilizador_animais` (
  `cliente_id` INT NOT NULL,
  `animais_id` INT NOT NULL,
  UNIQUE INDEX `UK_i41xh1f9gwgjq0fxj2w6yfutc` (`animais_id` ASC) VISIBLE,
  INDEX `FKd1i1sugb0i1vkpsm17lsfd0bg` (`cliente_id` ASC) VISIBLE,
  CONSTRAINT `FKd1i1sugb0i1vkpsm17lsfd0bg`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `vetgest`.`utilizador` (`id`),
  CONSTRAINT `FKnpv6fvljtnxfutrsn182qltov`
    FOREIGN KEY (`animais_id`)
    REFERENCES `vetgest`.`animal` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



insert into utilizador (dtype,nome, email,password,concelho, contacto, freguesia, morada, nif)
values ('Clinica', 'Clínica Veterinária do Minho', 'clinicaveterinariadominho@gmail.com', '$2a$10$KoDr0ame1kST9Xjw6OhPAu66L5pNpQHO6By5UznaKnDnrNKqxmXV.',
        'Braga', '253601100', 'Gualtar', 'Rua da Universidade 4710-057', 111111111);
insert into utilizador(dtype,email,password,concelho,contacto,freguesia,morada,nif,nome,iban) values
('Veterinario','goncaloSilva@gmail.com','Pass1','Amarante','938481114','Travanca','Rua da Pena Nº14','147928191','Gonçalo Silva','PT50910292917182948591012'),
('Veterinario','catarinaBarbosa.02@gmail.com','Pass1','Lousada','938481114','Boim','Rua José Ribeiro da Silva Nº31','991828111','Catarina Barbosa','PT50510852917182948598166'),
('Veterinario','120ManuelaRibeiro@gmail.com','Pass1','Penafiel','938481114','Bustelo','Rua do Mosteiro Nº83 1ºEsq','127483112','Manuela Ribeiro','PT50115223917224188598161'),
('Cliente','joaodiogomota12@gmail.com','Pass1','Lousada','935373306','Nevogilde','Rua da Padaria Nº37','256862010','João Mota',NULL),
('Cliente','f7carolina.cunha@gmail.com','Pass1','Fafe','915293785','Antime','Rua de Teibães Nº295','249705672','Carolina Cunha',NULL),
('Cliente','pedro12Costa@gmail.com','Pass1','Lisboa','961887112','Ajuda','Calçada do Mirante à Ajuda Nº144','471999010','Pedro Costa',NULL),
('Cliente','alves.maria@gmail.com','Pass1','Porto','915551628','Cedofeita','Rua de Álvares Cabral Nº52 2ºDir','899662162','Maria Alves',NULL),
('Cliente','menezesaurora@hotmail.com','Pass1','Penafiel','926991034','Rans','Rua Mato Nº4','111892818','Aurora Menezes',NULL),
('Cliente','sonia_araujo_25@gmail.com','Pass1','Caminha','911183911','Âncora','Rua da Areia Nº76','992838911','Sónia Araújo',NULL),
('Cliente','rodolfina.ol@gmail.com','Pass1','Caldas de Vizela','933315238','Infias','Rua do Alijó','248918223','Rodolfina Medeiros',NULL),
('Cliente','sofiaferreira@gmail.com','Pass1','Braga','931117821','Gualtar','Rua da Lage Nº231','412623553','Sofia Ferreira',NULL),
('Cliente','diasnuno@hotmail.com','Pass1','Gondomar','919998718','Rio Tinto','Rua de Rebordãos','887182831','Nuno Dias',NULL),
('Cliente','marquinhos_silva@sapo.pt','Pass1','Valongo','931418482','Alfena','Rua Nossa Sra. Piedade Nº87','424777522','Marco Silva',NULL),
('Cliente','cristinapinheiro1@hotmail.com','Pass1','Guimarães','934561297','Azurém','Rua de Francos, Nº344','242187282','Cristina Pinheiro',NULL),
('Cliente','miquelinamaria@hotmail.com','Pass1','Guimarães','912347579','Brito','Rua 10 de Junho Nº63 1ºEsq','512735222','Miquelina Maria',NULL),
('Cliente','manuel_francisco@gmail.com','Pass1','Fafe','969993381','Fornelos','Rua da Igreja Nº99','281800192','Manuel Ribeiro',NULL),
('Cliente','josejoaquim@gmail.com','Pass1','Vila Nova de Famalicão','912238760','Lousado','Travessa da Gandra Nº3','988982113','José Joaquim',NULL),
('Cliente','guilherminasafreitas@hotmail.com','Pass1','Porto','918871232','Bonfim','Rua Dr. Carlos Passos Nº94','267823882','Guilhermina Freitas,',NULL),
('Cliente','manuelantunes@hotmail.com','Pass1','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal Nº42','773882121','Manuel Antunes',NULL),
('Cliente','margarida1freitas@sapo.pt','Pass1','Vila Verde','918381998','Cabanelas','Rua da Lagoa Nº122 2ºDir','412637000','Margarida Freitas',NULL),
('Cliente','pedrofrazao@iol.pt','Pass1','Póvoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, Nº71','998628664','Pedro Frazão',NULL),
('Cliente','joanapereirinha@hotmail.com','Pass1','Ponte da Barca','914443215','Azias','Rua da Veiga Nº5','145322011','Joana Pereira',NULL),
('Cliente','antoniomoreira48@hotmail.com','Pass1','Vila Nova de Famalicão','931122334','Riba dAve','Rua Souto Nº100','141299421','António Moreira',NULL),
('Cliente','ameliacarmelia@gmail.com','Pass1','Felgueiras','918908907','Idães','Rua de Balteiro Nª2','261111992','Amélia Carmelia',NULL),
('Cliente','joanavaz@gmail.com','Pass1','Braga','912396748','Palmeira','Rua da Aldeia nº121','412887545','Joana Vaz',NULL),
('Cliente','felisbertocastro@hotmail.com','Pass1','Barcelos','912398654','Aborim','Rua da Samil Nº40','883009111','Felisberto Castro',NULL),
('Cliente','marianasantos@gmail.com','Pass1','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros nº13','111000921','Mariana Santos',NULL),
('Cliente','catiazinhavilar@gmail.com','Pass1','Bragança','912985411','Babe','Rua da Ranhola Nº79','222918225','Cátia Vilar',NULL),
('Cliente','eduardalongras@hotmail.com','Pass1','Vinhais','933332343','Paçó','Rua da Pedra Nº15','111772983','Eduarda Longras',NULL),
('Cliente','verinhacarvalho@gmail.com','Pass1','Macedo de Cavaleiros','912312341','Arcas','Rua da Fonte Nº78','123141222','Vera Carvalho',NULL),
('Cliente','justinanazario@hotmail.com','Pass1','Vila Real','922314528','Campeã','Rua do Fontanário Nº5','415124176','Justina Nazário',NULL),
('Cliente','joaodiogomota13@gmail.com','Pass1','Lousada','935373306','Nevogilde','Rua da Padaria Nº37','256862010','João Mendes',NULL),
('Cliente','f8carolina.cunha@gmail.com','Pass1','Fafe','915293785','Antime','Rua de Teibães Nº295','249705672','Carolina Alves',NULL),
('Cliente','pedro13Costa@gmail.com','Pass1','Lisboa','961887112','Ajuda','Calçada do Mirante à Ajuda Nº144','471999010','Pedro Cunha',NULL),
('Cliente','alves.maria1@gmail.com','Pass1','Porto','915551628','Cedofeita','Rua de Álvares Cabral Nº52 2ºDir','899662162','Maria Vieira',NULL),
('Cliente','menezesaurora1@hotmail.com','Pass1','Penafiel','926991034','Rans','Rua Mato Nº4','111892818','Aurora Machado',NULL),
('Cliente','sonia_araujo_26@gmail.com','Pass1','Caminha','911183911','Âncora','Rua da Areia Nº76','992838911','Sónia Alves',NULL),
('Cliente','rodolfina.ol2@gmail.com','Pass1','Caldas de Vizela','933315238','Infias','Rua do Alijó','248918223','Rodolfina do Céu',NULL),
('Cliente','sofiaferreira1@gmail.com','Pass1','Braga','931117821','Gualtar','Rua da Lage Nº231','412623553','Sofia Pereira',NULL),
('Cliente','diasnuno1@hotmail.com','Pass1','Gondomar','919998718','Rio Tinto','Rua de Rebordãos','887182831','Nuno Noites',NULL),
('Cliente','marquinhos_silv7a@sapo.pt','Pass1','Valongo','931418482','Alfena','Rua Nossa Sra. Piedade Nº87','424777522','Marco Gonçalves',NULL),
('Cliente','cristinapinheiro13@hotmail.com','Pass1','Guimarães','934561297','Azurém','Rua de Francos, Nº344','242187282','Cristina Ronalda',NULL),
('Cliente','miquelinamaria4@hotmail.com','Pass1','Guimarães','912347579','Brito','Rua 10 de Junho Nº63 1ºEsq','512735222','Miquelina Mariana',NULL),
('Cliente','manuel_francisc1o@gmail.com','Pass1','Fafe','969993381','Fornelos','Rua da Igreja Nº99','281800192','Manuel Carvalho',NULL),
('Cliente','josejoaquim5@gmail.com','Pass1','Vila Nova de Famalicão','912238760','Lousado','Travessa da Gandra Nº3','988982113','José Manuel',NULL),
('Cliente','guilherminasa12freitas@hotmail.com','Pass1','Porto','918871232','Bonfim','Rua Dr. Carlos Passos Nº94','267823882','Guilhermina Rego,',NULL),
('Cliente','manuelantunes31@hotmail.com','Pass1','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal Nº42','773882121','Manuel Moura',NULL),
('Cliente','margarida1freita2s@sapo.pt','Pass1','Vila Verde','918381998','Cabanelas','Rua da Lagoa Nº122 2ºDir','412637000','Margarida Leal',NULL),
('Cliente','pedrofrazao51@iol.pt','Pass1','Póvoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, Nº71','998628664','Pedro Roberto',NULL),
('Cliente','joanapereirinha41@hotmail.com','Pass1','Ponte da Barca','914443215','Azias','Rua da Veiga Nº5','145322011','Joana Duarte',NULL),
('Cliente','antoniomoreira428@hotmail.com','Pass1','Vila Nova de Famalicão','931122334','Riba dAve','Rua Souto Nº100','141299421','António Santos',NULL),
('Cliente','ameliacarmelia144@gmail.com','Pass1','Felgueiras','918908907','Idães','Rua de Balteiro Nª2','261111992','Amélia CUnha',NULL),
('Cliente','joanavaz2000@gmail.com','Pass1','Braga','912396748','Palmeira','Rua da Aldeia nº121','412887545','Joana Pinto',NULL),
('Cliente','felisbertocastro4@hotmail.com','Pass1','Barcelos','912398654','Aborim','Rua da Samil Nº40','883009111','Felisberto Andrade',NULL),
('Cliente','marianasanto51s@gmail.com','Pass1','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros nº13','111000921','Mariana Cunha',NULL),
('Cliente','catiazinhavilar12@gmail.com','Pass1','Bragança','912985411','Babe','Rua da Ranhola Nº79','222918225','Cátia Costa',NULL),
('Cliente','eduardalongras41@hotmail.com','Pass1','Vinhais','933332343','Paçó','Rua da Pedra Nº15','111772983','Eduarda Veloso',NULL),
('Cliente','verinhacarvalho5@gmail.com','Pass1','Macedo de Cavaleiros','912312341','Arcas','Rua da Fonte Nº78','141257632','Vera Dâmaso',NULL),
('Cliente','justinanazari1o@hotmail.com','Pass1','Vila Real','922314528','Campeã','Rua do Fontanário Nº5','151362777','Justina Leite',NULL),
('Cliente','joaodiogomota14@gmail.com','Pass1','Lousada','935373306','Nevogilde','Rua da Padaria Nº37','256862010','João Cunha',NULL),
('Cliente','f9carolina.cunha@gmail.com','Pass1','Fafe','915293785','Antime','Rua de Teibães Nº295','249705672','Carolina Pereira',NULL),
('Cliente','pedro132Costa@gmail.com','Pass1','Lisboa','961887112','Ajuda','Calçada do Mirante à Ajuda Nº144','471999010','Pedro Garcia',NULL),
('Cliente','alves.maria11@gmail.com','Pass1','Porto','915551628','Cedofeita','Rua de Álvares Cabral Nº52 2ºDir','899662162','Maria Bandarra',NULL),
('Cliente','menezesaurora12@hotmail.com','Pass1','Penafiel','926991034','Rans','Rua Mato Nº4','111892818','Aurora Carvalho',NULL),
('Cliente','sonia_araujo_326@gmail.com','Pass1','Caminha','911183911','Âncora','Rua da Areia Nº76','992838911','Sónia Neto',NULL),
('Cliente','rodolfina.ol42@gmail.com','Pass1','Caldas de Vizela','933315238','Infias','Rua do Alijó','248918223','Rodolfina Magalhaes',NULL),
('Cliente','sofiaferreir5a1@gmail.com','Pass1','Braga','931117821','Gualtar','Rua da Lage Nº231','412623553','Sofia Vilaça',NULL),
('Cliente','diasnuno31@hotmail.com','Pass1','Gondomar','919998718','Rio Tinto','Rua de Rebordãos','887182831','Nuno Figueiredo',NULL),
('Cliente','marquinhos_silv47a@sapo.pt','Pass1','Valongo','931418482','Alfena','Rua Nossa Sra. Piedade Nº87','424777522','Marco Nunes',NULL),
('Cliente','cristinapinheiro123@hotmail.com','Pass1','Guimarães','934561297','Azurém','Rua de Francos, Nº344','242187282','Cristina Afonso',NULL),
('Cliente','miquelinamaria41@hotmail.com','Pass1','Guimarães','912347579','Brito','Rua 10 de Junho Nº63 1ºEsq','512735222','Miquelina Martins',NULL),
('Cliente','manuel_francisc21o@gmail.com','Pass1','Fafe','969993381','Fornelos','Rua da Igreja Nº99','281800192','Manuel Sá',NULL),
('Cliente','josejoaquim55@gmail.com','Pass1','Vila Nova de Famalicão','912238760','Lousado','Travessa da Gandra Nº3','988982113','José Azevedo',NULL),
('Cliente','guilherminasa124freitas@hotmail.com','Pass1','Porto','918871232','Bonfim','Rua Dr. Carlos Passos Nº94','267823882','Guilhermina Alves,',NULL),
('Cliente','manuelantunes321@hotmail.com','Pass1','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal Nº42','773882121','Manuel Nunes',NULL),
('Cliente','margarida1freita24s@sapo.pt','Pass1','Vila Verde','918381998','Cabanelas','Rua da Lagoa Nº122 2ºDir','412637000','Margarida Carneiro',NULL),
('Cliente','pedrofrazao514@iol.pt','Pass1','Póvoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, Nº71','998628664','Pedro Matias',NULL),
('Cliente','joanapereirinha421@hotmail.com','Pass1','Ponte da Barca','914443215','Azias','Rua da Veiga Nº5','145322011','Joana Gouveia',NULL),
('Cliente','antoniomoreira4428@hotmail.com','Pass1','Vila Nova de Famalicão','931122334','Riba dAve','Rua Souto Nº100','141299421','António Sequeira',NULL),
('Cliente','ameliacarmelia1744@gmail.com','Pass1','Felgueiras','918908907','Idães','Rua de Balteiro Nª2','261111992','Amélia Silva',NULL),
('Cliente','joanavaz20800@gmail.com','Pass1','Braga','912396748','Palmeira','Rua da Aldeia nº121','412887545','Joana Castro',NULL),
('Cliente','felisbertocastro94@hotmail.com','Pass1','Barcelos','912398654','Aborim','Rua da Samil Nº40','883009111','Felisberto Oliveira',NULL),
('Cliente','marianasanto571s@gmail.com','Pass1','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros nº13','111000921','Mariana Medeiros',NULL),
('Cliente','catiazinhavilar172@gmail.com','Pass1','Bragança','912985411','Babe','Rua da Ranhola Nº79','222918225','Cátia Pereira',NULL),
('Cliente','eduardalongras451@hotmail.com','Pass1','Vinhais','933332343','Paçó','Rua da Pedra Nº15','111772983','Eduarda Lobo',NULL),
('Cliente','verinhacarvalho54@gmail.com','Pass1','Macedo de Cavaleiros','912312341','Arcas','Rua da Fonte Nº78','124512000','Vera Costa',NULL),
('Cliente','justinanazari22o@hotmail.com','Pass1','Vila Real','922314528','Campeã','Rua do Fontanário Nº5','111929006','Justina Damaso',NULL),
('Cliente','manuel_francisc214o@gmail.com','Pass1','Fafe','969993381','Fornelos','Rua da Igreja Nº99','281800192','Manuel Sousa',NULL),
('Cliente','josejoaquim525@gmail.com','Pass1','Vila Nova de Famalicão','912238760','Lousado','Travessa da Gandra Nº3','988982113','José Silva',NULL),
('Cliente','guilherminasa1234freitas@hotmail.com','Pass1','Porto','918871232','Bonfim','Rua Dr. Carlos Passos Nº94','267823882','Guilhermina Branco,',NULL),
('Cliente','manuelantunes3121@hotmail.com','Pass1','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal Nº42','773882121','Manuel Novais',NULL),
('Cliente','margarida1freita244s@sapo.pt','Pass1','Vila Verde','918381998','Cabanelas','Rua da Lagoa Nº122 2ºDir','412637000','Margarida Sousa',NULL),
('Cliente','pedrofrazao5141@iol.pt','Pass1','Póvoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, Nº71','998628664','Pedro Ferreira',NULL),
('Cliente','joanapereirinha4521@hotmail.com','Pass1','Ponte da Barca','914443215','Azias','Rua da Veiga Nº5','145322011','Joana Silva',NULL),
('Cliente','antoniomoreira44728@hotmail.com','Pass1','Vila Nova de Famalicão','931122334','Riba dAve','Rua Souto Nº100','141299421','António Martins',NULL),
('Cliente','ameliacarmelia17344@gmail.com','Pass1','Felgueiras','918908907','Idães','Rua de Balteiro Nª2','261111992','Amélia Fernandes',NULL),
('Cliente','joanavaz208020@gmail.com','Pass1','Braga','912396748','Palmeira','Rua da Aldeia nº121','412887545','Joana Costa',NULL),
('Cliente','felisbertocastro914@hotmail.com','Pass1','Barcelos','912398654','Aborim','Rua da Samil Nº40','883009111','Felisberto Carvalho',NULL),
('Cliente','marianasanto5714s@gmail.com','Pass1','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros nº13','111000921','Mariana Babo',NULL),
('Cliente','catiazinhavilar1722@gmail.com','Pass1','Bragança','912985411','Babe','Rua da Ranhola Nº79','222918225','Cátia Marques',NULL),
('Cliente','eduardalongras4521@hotmail.com','Pass1','Vinhais','933332343','Paçó','Rua da Pedra Nº15','111772983','Eduarda Arantes',NULL),
('Cliente','joaodiogomota145@gmail.com','Pass1','Lousada','935373306','Nevogilde','Rua da Padaria Nº37','256862010','João Fonseca',NULL),
('Cliente','f9carolina.cunha22@gmail.com','Pass1','Fafe','915293785','Antime','Rua de Teibães Nº295','249705672','Carolina Rodrigues',NULL),
('Cliente','pedro132Costa4@gmail.com','Pass1','Lisboa','961887112','Ajuda','Calçada do Mirante à Ajuda Nº144','471999010','Pedro Sá',NULL),
('Cliente','alves.maria121@gmail.com','Pass1','Porto','915551628','Cedofeita','Rua de Álvares Cabral Nº52 2ºDir','899662162','Maria Oliveira',NULL),
('Cliente','menezesaurora123@hotmail.com','Pass1','Penafiel','926991034','Rans','Rua Mato Nº4','111892818','Aurora Neto',NULL),
('Cliente','sonia_araujo_3326@gmail.com','Pass1','Caminha','911183911','Âncora','Rua da Areia Nº76','992838911','Sónia Pacheco',NULL);

INSERT INTO animal (altura, castracao, cauda, chip, cor, data_nascimento, especie, nome, observacoes, pelagem, raca, sexo, path) VALUES
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Felídeo','Bonifácio','Pata partida; Sem canino frontal direito','Média,Lisa', 'Europeu', 'Macho',''),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Felídeo','Yona',NULL,'Média,Lisa', 'Europeu', 'Fêmea',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Felídeo','Luffy',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Felídeo','Peluda',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Felídeo','Caramelo',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Felídeo','Caramelo',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Felídeo','Nina',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Felídeo','Patusca',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Pastel',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(18,1,'Comprida','B543A678','Castanho,Creme','2007-10-16','Felídeo','Rita',NULL,'Média,Lisa', 'Siamês', 'Fêmea',''),
(48,0,'Comprida','G5412A34','Creme','2018-07-10','Canídeo','Rafa',NULL,'Curta,Ondulada', 'Podengo', 'Macho',''),
(49,1,'Comprida','P1787422','Branco','2020-06-04','Canídeo','Boris',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho',''),
(30,0,'Curta','L974P102','Castanho,Branco','2004-09-24','Canídeo','Bucky',NULL,'Curta,Lisa', 'Sem raça definida', 'Macho',''),
(58,0,'Comprida','B4154782','Castanho,Branco,Preto','2011-07-03','Canídeo','Poti',NULL,'Média,Ondulada', 'São Bernardo', 'Macho',''),
(50,0,'Comprida','M1451452','Castanho','2015-10-20','Canídeo','Puscas',NULL,'Média,Ondulada', 'Serra da Estrela', 'Macho',''),
(40,1,'Comprida','N674A52','Creme','2008-07-17','Canídeo','Rex',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho',''),
(17,0,'Comprida','L986GT6','Branco,Castanho','2016-02-14','Felídeo','Sebastião',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(15,1,'Comprida','PLO9SAS','Branco,Castanho','2013-02-17','Felídeo','Yhammi',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(25,1,'Comprida','N475241','Cinzento,Dourado','2020-07-16','Canídeo','Lassie',NULL,'Média,Lisa', 'Yorkshire Terrier', 'Fêmea',''),
(26,1,'Comprida','ML76542','Cinzento,Dourado','2014-05-13','Canídeo','Cookie',NULL,'Curta,Lisa', 'Yorkshire Terrier', 'Macho',''),
(30,0,'Comprida','B654A45','Castanho','2013-01-06','Canídeo','Tay',NULL,'Curta,Lisa', 'Sem raça definida', 'Macho',''),
(55,0,'Comprida','M457P95','Castanho,Preto','2013-03-11','Canídeo','Rubi',NULL,'Média,Ondulada', 'Serra da Estrela', 'Macho',''),
(60,1,'Comprida','N782713','Cinzento','2016-03-03','Canídeo','Pó de Arroz',NULL,'Curta,Lisa','Weimaraner','Macho',''),
(22,0,'Comprida','S145A2S','Castanho,Preto','2021-01-14','Canídeo','Minnie',NULL,'Média,Lisa', 'Pastor Alemão', 'Fêmea',''),
(36,1,'Comprida','R41ASAS','Branco,Castanho,Preto','2018-02-27','Canídeo','Maxi',NULL,'Curta,Lisa', 'Beagle', 'Macho',''),
(30,1,'Comprida','BH29SAS','Preto,Castanho','2012-09-01','Canídeo','Black',NULL,'Curta,Lisa', 'Teckel', 'Fêmea',''),
(31,1,'Comprida','B7AS0AS','Preto,Castanho','2017-04-15','Canídeo','Broa de Mel',NULL,'Curta,Lisa', 'Teckel', 'Macho',''),
(32,0,'Curta','B45854S','Preto','2019-05-19','Canídeo','Enzo',NULL,'Curta,Lisa', 'Bulldog Francês', 'Macho',''),
(33,1,'Comprida','PR5S4AS','Branco,Castanho,Preto','2017-02-17','Canídeo','Kika',NULL,'Curta,Lisa', 'Beagle', 'Fêmea',''),
(23,1,'Comprida','9AS56A1','Castanho','2018-07-19','Canídeo','Simba',NULL,'Curta,Lisa', 'Sem raça definida', 'Macho',''),
(39,0,'Comprida','POL7845','Branco','2015-07-16','Canídeo','Maya',NULL,'Comprida,Lisa', 'Samoiedo', 'Fêmea',''),
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Canídeo','Nina','Pata partida; Sem canino frontal direito','Média,Lisa', 'Europeu', 'Macho',''),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Canídeo','Leite Creme',NULL,'Média,Lisa', 'Europeu', 'Fêmea',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Bolt',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Milka',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Junior',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Martini',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Princesa',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Cherry',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Rudi',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Runa',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Gorbi',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Nikita',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Zuki',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Pterion',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Violeta',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Eva',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Lua',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Lex',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Pantera',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Tico',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Kiko',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Mel',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Simba',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Zuri',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Snoopy',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Vitória',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Kiki',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Pepe',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Penny',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Barney',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Pepa',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Pena',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Felídeo','Boninhas','Pata partida; Sem canino frontal direito','Média,Lisa', 'Europeu', 'Macho',''),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Felídeo','Yoninha',NULL,'Média,Lisa', 'Europeu', 'Fêmea',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Felídeo','Luffytaro',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Felídeo','Peludinha',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Felídeo','Macaquinho',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Felídeo','Branquinho',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Felídeo','Pretinha',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Felídeo','Branquinha',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Felídeo','Siamês',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(18,1,'Comprida','B543A678','Castanho,Creme','2007-10-16','Felídeo','Pequenina',NULL,'Média,Lisa', 'Siamês', 'Fêmea',''),
(48,0,'Comprida','G5412A34','Creme','2018-07-10','Canídeo','Boneca',NULL,'Curta,Ondulada', 'Podengo', 'Macho',''),
(49,1,'Comprida','P1787422','Branco','2020-06-04','Felídeo','Gaspar',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho',''),
(30,0,'Curta','L974P102','Castanho,Branco','2004-09-24','Felídeo','Kiko',NULL,'Curta,Lisa', 'Sem raça definida', 'Macho',''),
(58,0,'Comprida','B4154782','Castanho,Branco,Preto','2011-07-03','Canídeo','Podi',NULL,'Média,Ondulada', 'São Bernardo', 'Macho',''),
(50,0,'Comprida','M1451452','Castanho','2015-10-20','Canídeo','Puskas',NULL,'Média,Ondulada', 'Serra da Estrela', 'Macho',''),
(40,1,'Comprida','N674A52','Creme','2008-07-17','Canídeo','Felix',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho',''),
(17,0,'Comprida','L986GT6','Branco,Castanho','2016-02-14','Felídeo','Roberto',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(15,1,'Comprida','PLO9SAS','Branco,Castanho','2013-02-17','Felídeo','Renato',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(25,1,'Comprida','N475241','Cinzento,Dourado','2020-07-16','Canídeo','Patuda',NULL,'Média,Lisa', 'Yorkshire Terrier', 'Fêmea',''),
(26,1,'Comprida','ML76542','Cinzento,Dourado','2014-05-13','Canídeo','Bernie',NULL,'Curta,Lisa', 'Yorkshire Terrier', 'Macho',''),
(30,0,'Comprida','B654A45','Castanho','2013-01-06','Canídeo','Tai',NULL,'Curta,Lisa', 'Sem raça definida', 'Macho',''),
(55,0,'Comprida','M457P95','Castanho,Preto','2013-03-11','Canídeo','Ruby',NULL,'Média,Ondulada', 'Serra da Estrela', 'Macho',''),
(60,1,'Comprida','N782713','Cinzento','2016-03-03','Canídeo','Mel',NULL,'Curta,Lisa','Weimaraner','Macho',''),
(22,0,'Comprida','S145A2S','Castanho,Preto','2021-01-14','Canídeo','Polly',NULL,'Média,Lisa', 'Pastor Alemão', 'Fêmea',''),
(36,1,'Comprida','R41ASAS','Branco,Castanho,Preto','2018-02-27','Canídeo','Maxy',NULL,'Curta,Lisa', 'Beagle', 'Macho',''),
(30,1,'Comprida','BH29SAS','Preto,Castanho','2012-09-01','Canídeo','Blac',NULL,'Curta,Lisa', 'Teckel', 'Fêmea',''),
(31,1,'Comprida','B7AS0AS','Preto,Castanho','2017-04-15','Canídeo','Brie',NULL,'Curta,Lisa', 'Teckel', 'Macho',''),
(32,0,'Curta','B45854S','Preto','2019-05-19','Canídeo','Enso',NULL,'Curta,Lisa', 'Bulldog Francês', 'Macho',''),
(33,1,'Comprida','PR5S4AS','Branco,Castanho,Preto','2017-02-17','Canídeo','Kiki',NULL,'Curta,Lisa', 'Beagle', 'Fêmea',''),
(23,1,'Comprida','9AS56A1','Castanho','2018-07-19','Canídeo','Symba',NULL,'Curta,Lisa', 'Sem raça definida', 'Macho',''),
(39,0,'Comprida','POL7845','Branco','2015-07-16','Canídeo','Maia',NULL,'Comprida,Lisa', 'Samoiedo', 'Fêmea',''),
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Canídeo','Nyna','Pata partida; Sem canino frontal direito','Média,Lisa', 'Europeu', 'Macho',''),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Canídeo','Nick',NULL,'Média,Lisa', 'Europeu', 'Fêmea',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Pirata',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Pandora',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Gerês',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Gin',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Belga',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Zip',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Zipp',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Atena',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Zeus',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Morpheus',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Hades',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Hera',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Hefesto',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Afrodite',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Artemis',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Hermes',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Cronus',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Cerberus',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Plutão',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Minerva',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Apolo',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canídeo','Megara',NULL,'Média,Lisa', 'Europeu', 'Macho',''),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canídeo','Filoctetes',NULL,'Comprida,Lisa', 'Europeu', 'Fêmea',''),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canídeo','Pégasus',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canídeo','Atlanta',NULL,'Curta,Lisa', 'Europeu', 'Macho',''),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canídeo','Édipo',NULL,'Curta,Lisa', 'Europeu', 'Fêmea',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Talia',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Dioniso',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho',''),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canídeo','Héstia',NULL,'Curta,Lisa', 'Siamês', 'Fêmea',''),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canídeo','Hércules',NULL,'Curta,Encaracolada', 'Sem raça definida', 'Macho','');

INSERT INTO utilizador_animais (cliente_id, animais_id) VALUES
(5,1),
(5,2),
(5,3),
(5,4),
(5,5),
(5,6),
(5,7),
(5,8),
(5,9),
(5,10),
(5,11),
(5,12),
(5,13),
(5,14),
(6,15),
(6,16),
(6,17),
(6,18),
(6,19),
(6,20),
(6,21),
(6,22),
(6,23),
(6,24),
(6,25),
(6,26),
(6,27),
(7,28),
(7,29),
(7,30),
(7,31),
(7,32),
(7,33),
(7,34),
(7,35),
(7,36),
(7,37),
(7,38),
(7,39),
(7,40),
(7,41),
(7,42),
(7,43),
(7,44),
(7,45),
(7,46),
(7,47),
(7,48),
(7,49),
(8,50),
(8,51),
(8,52),
(8,53),
(8,54),
(8,55),
(8,56),
(8,57),
(8,58),
(8,59),
(8,60),
(8,61),
(9,62),
(9,63),
(9,64),
(9,65),
(9,66),
(9,67),
(9,68),
(9,69),
(9,70),
(9,71),
(9,72),
(9,73),
(9,74),
(9,75),
(9,76),
(10,77),
(10,78),
(10,79),
(10,80),
(10,81),
(10,82),
(10,83),
(10,84),
(10,85),
(10,86),
(10,87),
(10,88),
(10,89),
(10,90),
(10,91),
(10,92),
(10,93),
(11,94),
(11,95),
(11,96),
(11,97),
(11,98),
(11,99),
(11,100),
(11,101),
(11,102),
(11,103),
(11,104),
(11,105),
(11,106),
(11,107),
(11,108),
(11,109),
(12,110),
(12,111),
(12,112),
(12,113),
(12,114),
(12,115),
(12,116),
(12,117),
(12,118),
(12,119),
(12,120),
(12,121),
(12,122),
(12,123),
(12,124),
(12,125),
(12,126);

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(1,'2021-06-14','Outros','Concluída','10:00','Consulta extraordinária/Por doença','Consulta',1,2, '2021-06-13','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(2,'2021-06-16','A decorrer','15:00','Consulta de Seguimento','Consulta',1,2, '2021-06-14');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(3,'2021-06-31','Agendada','15:00','Consulta de Seguimento','Consulta',1,2, '2021-06-16');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(4,'2021-05-14','Castração','Concluída','10:00','Cirurgia Normal','Cirurgia',1,2, '2021-05-07','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(5,'2021-04-14','Pata Partida','Concluída','10:00','Cirurgia de Urgência','Cirurgia',1,2, '2021-06-14','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(6,'2021-05-13','Outros','Concluída','10:00','Consulta extraordinária/Por doença','Consulta',2,3, '2021-05-13','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(7,'2021-06-16','A decorrer','14:00','Consulta de Seguimento','Consulta',2,3, '2021-06-15');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(8,'2021-07-01','Agendada','15:00','Consulta de Seguimento','Consulta',2,3, '2021-06-16');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(9,'2021-03-14','Castração','Concluída','10:00','Cirurgia Normal','Cirurgia',2,3, '2021-03-07','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(10,'2021-04-01','Pata Partida','Concluída','10:00','Cirurgia de Urgência','Cirurgia',2,3, '2021-04-01 ','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(11,'2021-06-13','Outros','Concluída','10:00','Consulta extraordinária/Por doença','Consulta',3,4, '2021-06-13','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(12,'2021-06-18','Agendada','15:00','Consulta de Seguimento','Consulta',3,4, '2021-06-13');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(13,'2021-06-31','Agendada','15:00','Consulta de Seguimento','Consulta',3,4, '2021-06-13');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(14,'2021-02-28','Castração','Concluída','10:00','Cirurgia Normal','Cirurgia',3,4, '2021-02-21','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(15,'2021-04-01','Pata Partida','Concluída','10:00','Cirurgia de Urgência','Cirurgia',3,4, '2021-04-01','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(16,'2021-06-10','Outros','Concluída','10:00','Consulta extraordinária/Por doença','Consulta',4,2, '2021-06-08','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(17,'2021-06-30','Agendada','15:00','Consulta de Seguimento','Consulta',4,2, '2021-06-10');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(18,'2021-07-6','Agendada','15:00','Consulta de Seguimento','Consulta',4,2, '2021-06-10');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(19,'2021-06-16','Castração','Concluída','10:00','Cirurgia Normal','Cirurgia',1,3, '2021-06-07','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(20,'2021-04-14','Pata Partida','Concluída','10:00','Cirurgia de Urgência','Cirurgia',1,3, '2021-06-14','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(21,'2021-05-31','Outros','Concluída','10:00','Consulta extraordinária/Por doença','Consulta',5,2, '2021-05-30','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(22,'2021-06-17','Agendada','15:00','Consulta de Seguimento','Consulta',5,3, '2021-05-31');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(23,'2021-06-20','Agendada','15:00','Consulta de Seguimento','Consulta',5,4, '2021-05-31');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(24,'2021-05-01','Castração','Concluída','10:00','Cirurgia Normal','Cirurgia',5,4, '2021-04-22','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(25,'2021-06-101','Pata Partida','Concluída','10:00','Cirurgia de Urgência','Cirurgia',5,4, '2021-06-01','Cirurgia realizada com sucesso');
