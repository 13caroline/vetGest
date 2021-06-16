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
  `image` LONGBLOB NULL DEFAULT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `observacoes` VARCHAR(255) NULL DEFAULT NULL,
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
  `image` LONGBLOB NULL DEFAULT NULL,
  `morada` VARCHAR(255) NOT NULL,
  `nif` BIGINT NULL DEFAULT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `iban` VARCHAR(255) NULL DEFAULT NULL,
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
  `observacoes` LONGTEXT NULL DEFAULT NULL,
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
values ('Clinica', 'Clinica Veterinaria do Minho', 'clinicaveterinariadominho@gmail.com', '$2a$10$KoDr0ame1kST9Xjw6OhPAu66L5pNpQHO6By5UznaKnDnrNKqxmXV.',
        'Braga', '253601100', 'Gualtar', 'Rua da Universidade 4710-057', 111111111);
insert into utilizador(dtype,email,password,concelho,contacto,freguesia,morada,nif,nome,iban) values
('Veterinario','goncaloSilva@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Amarante','938481114','Travanca','Rua da Pena N 14','147928191','Goncalo Silva','PT50910292917182948591012'),
('Veterinario','catarinaBarbosa.02@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lousada','938481114','Boim','Rua Jose Ribeiro da Silva N 31','991828111','Catarina Barbosa','PT50510852917182948598166'),
('Veterinario','120ManuelaRibeiro@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Penafiel','938481114','Bustelo','Rua do Mosteiro N 83 1 Esq','127483112','Manuela Ribeiro','PT50115223917224188598161'),
('Cliente','joaodiogomota12@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lousada','935373306','Nevogilde','Rua da Padaria N 37','256862010','Joao Mota',NULL),
('Cliente','f7carolina.cunha@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','915293785','Antime','Rua de Teibaes N 295','249705672','Carolina Cunha',NULL),
('Cliente','pedro12Costa@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lisboa','961887112','Ajuda','Calcada do Mirante a Ajuda N 144','471999010','Pedro Costa',NULL),
('Cliente','alves.maria@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','915551628','Cedofeita','Rua de alvares Cabral N 52 2 Dir','899662162','Maria Alves',NULL),
('Cliente','menezesaurora@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Penafiel','926991034','Rans','Rua Mato N 4','111892818','Aurora Menezes',NULL),
('Cliente','sonia_araujo_25@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caminha','911183911',' ancora','Rua da Areia N 76','992838911','Sonia Araujo',NULL),
('Cliente','rodolfina.ol@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caldas de Vizela','933315238','Infias','Rua do Alijo','248918223','Rodolfina Medeiros',NULL),
('Cliente','sofiaferreira@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','931117821','Gualtar','Rua da Lage N 231','412623553','Sofia Ferreira',NULL),
('Cliente','diasnuno@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Gondomar','919998718','Rio Tinto','Rua de Rebordaos','887182831','Nuno Dias',NULL),
('Cliente','marquinhos_silva@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Valongo','931418482','Alfena','Rua Nossa Sra. Piedade N 87','424777522','Marco Silva',NULL),
('Cliente','cristinapinheiro1@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Guimaraes','934561297','Azurem','Rua de Francos, N 344','242187282','Cristina Pinheiro',NULL),
('Cliente','miquelinamaria@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Guimaraes','912347579','Brito','Rua 10 de Junho N 63 1 Esq','512735222','Miquelina Maria',NULL),
('Cliente','manuel_francisco@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','969993381','Fornelos','Rua da Igreja N 99','281800192','Manuel Ribeiro',NULL),
('Cliente','josejoaquim@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','912238760','Lousado','Travessa da Gandra N 3','988982113','Jose Joaquim',NULL),
('Cliente','guilherminasafreitas@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','918871232','Bonfim','Rua Dr. Carlos Passos N 94','267823882','Guilhermina Freitas,',NULL),
('Cliente','manuelantunes@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal N 42','773882121','Manuel Antunes',NULL),
('Cliente','margarida1freitas@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Verde','918381998','Cabanelas','Rua da Lagoa N 122 2 Dir','412637000','Margarida Freitas',NULL),
('Cliente','pedrofrazao@iol.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Povoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, N 71','998628664','Pedro Frazao',NULL),
('Cliente','joanapereirinha@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Ponte da Barca','914443215','Azias','Rua da Veiga N 5','145322011','Joana Pereira',NULL),
('Cliente','antoniomoreira48@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','931122334','Riba dAve','Rua Souto N 100','141299421','Antonio Moreira',NULL),
('Cliente','ameliacarmelia@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Felgueiras','918908907','Idaes','Rua de Balteiro N2','261111992','Amelia Carmelia',NULL),
('Cliente','joanavaz@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','912396748','Palmeira','Rua da Aldeia n 121','412887545','Joana Vaz',NULL),
('Cliente','felisbertocastro@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Barcelos','912398654','Aborim','Rua da Samil N 40','883009111','Felisberto Castro',NULL),
('Cliente','marianasantos@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros n 13','111000921','Mariana Santos',NULL),
('Cliente','catiazinhavilar@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braganca','912985411','Babe','Rua da Ranhola N 79','222918225','Catia Vilar',NULL),
('Cliente','eduardalongras@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vinhais','933332343','Paco','Rua da Pedra N 15','111772983','Eduarda Longras',NULL),
('Cliente','verinhacarvalho@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Macedo de Cavaleiros','912312341','Arcas','Rua da Fonte N 78','123141222','Vera Carvalho',NULL),
('Cliente','justinanazario@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Real','922314528','Campea','Rua do Fontanario N 5','415124176','Justina Nazario',NULL),
('Cliente','joaodiogomota13@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lousada','935373306','Nevogilde','Rua da Padaria N 37','256862010','Joao Mendes',NULL),
('Cliente','f8carolina.cunha@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','915293785','Antime','Rua de Teibaes N 295','249705672','Carolina Alves',NULL),
('Cliente','pedro13Costa@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lisboa','961887112','Ajuda','Calcada do Mirante a Ajuda N 144','471999010','Pedro Cunha',NULL),
('Cliente','alves.maria1@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','915551628','Cedofeita','Rua de alvares Cabral N 52 2 Dir','899662162','Maria Vieira',NULL),
('Cliente','menezesaurora1@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Penafiel','926991034','Rans','Rua Mato N 4','111892818','Aurora Machado',NULL),
('Cliente','sonia_araujo_26@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caminha','911183911',' ancora','Rua da Areia N 76','992838911','Sonia Alves',NULL),
('Cliente','rodolfina.ol2@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caldas de Vizela','933315238','Infias','Rua do Alijo','248918223','Rodolfina do Ceu',NULL),
('Cliente','sofiaferreira1@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','931117821','Gualtar','Rua da Lage N 231','412623553','Sofia Pereira',NULL),
('Cliente','diasnuno1@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Gondomar','919998718','Rio Tinto','Rua de Rebordaos','887182831','Nuno Noites',NULL),
('Cliente','marquinhos_silv7a@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Valongo','931418482','Alfena','Rua Nossa Sra. Piedade N 87','424777522','Marco Goncalves',NULL),
('Cliente','cristinapinheiro13@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Guimaraes','934561297','Azurem','Rua de Francos, N 344','242187282','Cristina Ronalda',NULL),
('Cliente','miquelinamaria4@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Guimaraes','912347579','Brito','Rua 10 de Junho N 63 1 Esq','512735222','Miquelina Mariana',NULL),
('Cliente','manuel_francisc1o@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','969993381','Fornelos','Rua da Igreja N 99','281800192','Manuel Carvalho',NULL),
('Cliente','josejoaquim5@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','912238760','Lousado','Travessa da Gandra N 3','988982113','Jose Manuel',NULL),
('Cliente','guilherminasa12freitas@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','918871232','Bonfim','Rua Dr. Carlos Passos N 94','267823882','Guilhermina Rego,',NULL),
('Cliente','manuelantunes31@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal N 42','773882121','Manuel Moura',NULL),
('Cliente','margarida1freita2s@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Verde','918381998','Cabanelas','Rua da Lagoa N 122 2 Dir','412637000','Margarida Leal',NULL),
('Cliente','pedrofrazao51@iol.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Povoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, N 71','998628664','Pedro Roberto',NULL),
('Cliente','joanapereirinha41@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Ponte da Barca','914443215','Azias','Rua da Veiga N 5','145322011','Joana Duarte',NULL),
('Cliente','antoniomoreira428@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','931122334','Riba dAve','Rua Souto N 100','141299421','Antonio Santos',NULL),
('Cliente','ameliacarmelia144@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Felgueiras','918908907','Idaes','Rua de Balteiro N2','261111992','Amelia CUnha',NULL),
('Cliente','joanavaz2000@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','912396748','Palmeira','Rua da Aldeia n 121','412887545','Joana Pinto',NULL),
('Cliente','felisbertocastro4@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Barcelos','912398654','Aborim','Rua da Samil N 40','883009111','Felisberto Andrade',NULL),
('Cliente','marianasanto51s@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros n 13','111000921','Mariana Cunha',NULL),
('Cliente','catiazinhavilar12@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braganca','912985411','Babe','Rua da Ranhola N 79','222918225','Catia Costa',NULL),
('Cliente','eduardalongras41@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vinhais','933332343','Paco','Rua da Pedra N 15','111772983','Eduarda Veloso',NULL),
('Cliente','verinhacarvalho5@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Macedo de Cavaleiros','912312341','Arcas','Rua da Fonte N 78','141257632','Vera D amaso',NULL),
('Cliente','justinanazari1o@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Real','922314528','Campea','Rua do Fontanario N 5','151362777','Justina Leite',NULL),
('Cliente','joaodiogomota14@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lousada','935373306','Nevogilde','Rua da Padaria N 37','256862010','Joao Cunha',NULL),
('Cliente','f9carolina.cunha@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','915293785','Antime','Rua de Teibaes N 295','249705672','Carolina Pereira',NULL),
('Cliente','pedro132Costa@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lisboa','961887112','Ajuda','Calcada do Mirante a Ajuda N 144','471999010','Pedro Garcia',NULL),
('Cliente','alves.maria11@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','915551628','Cedofeita','Rua de alvares Cabral N 52 2 Dir','899662162','Maria Bandarra',NULL),
('Cliente','menezesaurora12@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Penafiel','926991034','Rans','Rua Mato N 4','111892818','Aurora Carvalho',NULL),
('Cliente','sonia_araujo_326@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caminha','911183911',' ancora','Rua da Areia N 76','992838911','Sonia Neto',NULL),
('Cliente','rodolfina.ol42@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caldas de Vizela','933315238','Infias','Rua do Alijo','248918223','Rodolfina Magalhaes',NULL),
('Cliente','sofiaferreir5a1@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','931117821','Gualtar','Rua da Lage N 231','412623553','Sofia Vilaca',NULL),
('Cliente','diasnuno31@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Gondomar','919998718','Rio Tinto','Rua de Rebordaos','887182831','Nuno Figueiredo',NULL),
('Cliente','marquinhos_silv47a@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Valongo','931418482','Alfena','Rua Nossa Sra. Piedade N 87','424777522','Marco Nunes',NULL),
('Cliente','cristinapinheiro123@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Guimaraes','934561297','Azurem','Rua de Francos, N 344','242187282','Cristina Afonso',NULL),
('Cliente','miquelinamaria41@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Guimaraes','912347579','Brito','Rua 10 de Junho N 63 1 Esq','512735222','Miquelina Martins',NULL),
('Cliente','manuel_francisc21o@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','969993381','Fornelos','Rua da Igreja N 99','281800192','Manuel Sa',NULL),
('Cliente','josejoaquim55@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','912238760','Lousado','Travessa da Gandra N 3','988982113','Jose Azevedo',NULL),
('Cliente','guilherminasa124freitas@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','918871232','Bonfim','Rua Dr. Carlos Passos N 94','267823882','Guilhermina Alves,',NULL),
('Cliente','manuelantunes321@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal N 42','773882121','Manuel Nunes',NULL),
('Cliente','margarida1freita24s@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Verde','918381998','Cabanelas','Rua da Lagoa N 122 2 Dir','412637000','Margarida Carneiro',NULL),
('Cliente','pedrofrazao514@iol.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Povoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, N 71','998628664','Pedro Matias',NULL),
('Cliente','joanapereirinha421@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Ponte da Barca','914443215','Azias','Rua da Veiga N 5','145322011','Joana Gouveia',NULL),
('Cliente','antoniomoreira4428@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','931122334','Riba dAve','Rua Souto N 100','141299421','Antonio Sequeira',NULL),
('Cliente','ameliacarmelia1744@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Felgueiras','918908907','Idaes','Rua de Balteiro N2','261111992','Amelia Silva',NULL),
('Cliente','joanavaz20800@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','912396748','Palmeira','Rua da Aldeia n 121','412887545','Joana Castro',NULL),
('Cliente','felisbertocastro94@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Barcelos','912398654','Aborim','Rua da Samil N 40','883009111','Felisberto Oliveira',NULL),
('Cliente','marianasanto571s@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros n 13','111000921','Mariana Medeiros',NULL),
('Cliente','catiazinhavilar172@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braganca','912985411','Babe','Rua da Ranhola N 79','222918225','Catia Pereira',NULL),
('Cliente','eduardalongras451@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vinhais','933332343','Paco','Rua da Pedra N 15','111772983','Eduarda Lobo',NULL),
('Cliente','verinhacarvalho54@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Macedo de Cavaleiros','912312341','Arcas','Rua da Fonte N 78','124512000','Vera Costa',NULL),
('Cliente','justinanazari22o@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Real','922314528','Campea','Rua do Fontanario N 5','111929006','Justina Damaso',NULL),
('Cliente','manuel_francisc214o@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','969993381','Fornelos','Rua da Igreja N 99','281800192','Manuel Sousa',NULL),
('Cliente','josejoaquim525@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','912238760','Lousado','Travessa da Gandra N 3','988982113','Jose Silva',NULL),
('Cliente','guilherminasa1234freitas@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','918871232','Bonfim','Rua Dr. Carlos Passos N 94','267823882','Guilhermina Branco,',NULL),
('Cliente','manuelantunes3121@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Paredes','921278872','Cristelo','Rua Zeferino Alves Leal N 42','773882121','Manuel Novais',NULL),
('Cliente','margarida1freita244s@sapo.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Verde','918381998','Cabanelas','Rua da Lagoa N 122 2 Dir','412637000','Margarida Sousa',NULL),
('Cliente','pedrofrazao5141@iol.pt','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Povoa de Lanhoso','9673718299','Moure','Lugar da Seixosa, N 71','998628664','Pedro Ferreira',NULL),
('Cliente','joanapereirinha4521@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Ponte da Barca','914443215','Azias','Rua da Veiga N 5','145322011','Joana Silva',NULL),
('Cliente','antoniomoreira44728@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vila Nova de Famalicao','931122334','Riba dAve','Rua Souto N 100','141299421','Antonio Martins',NULL),
('Cliente','ameliacarmelia17344@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Felgueiras','918908907','Idaes','Rua de Balteiro N2','261111992','Amelia Fernandes',NULL),
('Cliente','joanavaz208020@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braga','912396748','Palmeira','Rua da Aldeia n 121','412887545','Joana Costa',NULL),
('Cliente','felisbertocastro914@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Barcelos','912398654','Aborim','Rua da Samil N 40','883009111','Felisberto Carvalho',NULL),
('Cliente','marianasanto5714s@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Cabeceiras de Basto','931124009','Faia','Rua dos Pinheiros n 13','111000921','Mariana Babo',NULL),
('Cliente','catiazinhavilar1722@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Braganca','912985411','Babe','Rua da Ranhola N 79','222918225','Catia Marques',NULL),
('Cliente','eduardalongras4521@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Vinhais','933332343','Paco','Rua da Pedra N 15','111772983','Eduarda Arantes',NULL),
('Cliente','joaodiogomota145@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lousada','935373306','Nevogilde','Rua da Padaria N 37','256862010','Joao Fonseca',NULL),
('Cliente','f9carolina.cunha22@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Fafe','915293785','Antime','Rua de Teibaes N 295','249705672','Carolina Rodrigues',NULL),
('Cliente','pedro132Costa4@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Lisboa','961887112','Ajuda','Calcada do Mirante a Ajuda N 144','471999010','Pedro Sa',NULL),
('Cliente','alves.maria121@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Porto','915551628','Cedofeita','Rua de alvares Cabral N 52 2 Dir','899662162','Maria Oliveira',NULL),
('Cliente','menezesaurora123@hotmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Penafiel','926991034','Rans','Rua Mato N 4','111892818','Aurora Neto',NULL),
('Cliente','sonia_araujo_3326@gmail.com','$2a$10$OXETamq0w.M9WTpvmZRH7.bPgu7VnQy0vLQvtTC3uharoHtRjNrmu','Caminha','911183911',' ancora','Rua da Areia N 76','992838911','Sonia Pacheco',NULL);

INSERT INTO animal (altura, castracao, cauda, chip, cor, data_nascimento, especie, nome, observacoes, pelagem, raca, sexo) VALUES
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Felideo','Bonifacio','Pata partida; Sem canino frontal direito','Media,Lisa', 'Europeu', 'Macho'),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Felideo','Yona',NULL,'Media,Lisa', 'Europeu', 'Femea'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Felideo','Luffy',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Felideo','Peluda',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Felideo','Caramelo',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Felideo','Caramelo',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Felideo','Nina',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Felideo','Patusca',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Pastel',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(18,1,'Comprida','B543A678','Castanho,Creme','2007-10-16','Felideo','Rita',NULL,'Media,Lisa', 'Siames', 'Femea'),
(48,0,'Comprida','G5412A34','Creme','2018-07-10','Canideo','Rafa',NULL,'Curta,Ondulada', 'Podengo', 'Macho'),
(49,1,'Comprida','P1787422','Branco','2020-06-04','Canideo','Boris',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho'),
(30,0,'Curta','L974P102','Castanho,Branco','2004-09-24','Canideo','Bucky',NULL,'Curta,Lisa', 'Sem raca definida', 'Macho'),
(58,0,'Comprida','B4154782','Castanho,Branco,Preto','2011-07-03','Canideo','Poti',NULL,'Media,Ondulada', 'Sao Bernardo', 'Macho'),
(50,0,'Comprida','M1451452','Castanho','2015-10-20','Canideo','Puscas',NULL,'Media,Ondulada', 'Serra da Estrela', 'Macho'),
(40,1,'Comprida','N674A52','Creme','2008-07-17','Canideo','Rex',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho'),
(17,0,'Comprida','L986GT6','Branco,Castanho','2016-02-14','Felideo','Sebastiao',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(15,1,'Comprida','PLO9SAS','Branco,Castanho','2013-02-17','Felideo','Yhammi',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(25,1,'Comprida','N475241','Cinzento,Dourado','2020-07-16','Canideo','Lassie',NULL,'Media,Lisa', 'Yorkshire Terrier', 'Femea'),
(26,1,'Comprida','ML76542','Cinzento,Dourado','2014-05-13','Canideo','Cookie',NULL,'Curta,Lisa', 'Yorkshire Terrier', 'Macho'),
(30,0,'Comprida','B654A45','Castanho','2013-01-06','Canideo','Tay',NULL,'Curta,Lisa', 'Sem raca definida', 'Macho'),
(55,0,'Comprida','M457P95','Castanho,Preto','2013-03-11','Canideo','Rubi',NULL,'Media,Ondulada', 'Serra da Estrela', 'Macho'),
(60,1,'Comprida','N782713','Cinzento','2016-03-03','Canideo','Po de Arroz',NULL,'Curta,Lisa','Weimaraner','Macho'),
(22,0,'Comprida','S145A2S','Castanho,Preto','2021-01-14','Canideo','Minnie',NULL,'Media,Lisa', 'Pastor Alemao', 'Femea'),
(36,1,'Comprida','R41ASAS','Branco,Castanho,Preto','2018-02-27','Canideo','Maxi',NULL,'Curta,Lisa', 'Beagle', 'Macho'),
(30,1,'Comprida','BH29SAS','Preto,Castanho','2012-09-01','Canideo','Black',NULL,'Curta,Lisa', 'Teckel', 'Femea'),
(31,1,'Comprida','B7AS0AS','Preto,Castanho','2017-04-15','Canideo','Broa de Mel',NULL,'Curta,Lisa', 'Teckel', 'Macho'),
(32,0,'Curta','B45854S','Preto','2019-05-19','Canideo','Enzo',NULL,'Curta,Lisa', 'Bulldog Frances', 'Macho'),
(33,1,'Comprida','PR5S4AS','Branco,Castanho,Preto','2017-02-17','Canideo','Kika',NULL,'Curta,Lisa', 'Beagle', 'Femea'),
(23,1,'Comprida','9AS56A1','Castanho','2018-07-19','Canideo','Simba',NULL,'Curta,Lisa', 'Sem raca definida', 'Macho'),
(39,0,'Comprida','POL7845','Branco','2015-07-16','Canideo','Maya',NULL,'Comprida,Lisa', 'Samoiedo', 'Femea'),
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Canideo','Nina','Pata partida; Sem canino frontal direito','Media,Lisa', 'Europeu', 'Macho'),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Canideo','Leite Creme',NULL,'Media,Lisa', 'Europeu', 'Femea'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Bolt',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Milka',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Junior',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Martini',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Princesa',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Cherry',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Rudi',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Runa',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Gorbi',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Nikita',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Zuki',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Pterion',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Violeta',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Eva',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Lua',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Lex',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Pantera',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Tico',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Kiko',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Mel',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Simba',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Zuri',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Snoopy',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Vitoria',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Kiki',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Pepe',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Penny',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Barney',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Pepa',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Pena',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Felideo','Boninhas','Pata partida; Sem canino frontal direito','Media,Lisa', 'Europeu', 'Macho'),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Felideo','Yoninha',NULL,'Media,Lisa', 'Europeu', 'Femea'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Felideo','Luffytaro',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Felideo','Peludinha',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Felideo','Macaquinho',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Felideo','Branquinho',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Felideo','Pretinha',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Felideo','Branquinha',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Felideo','Siames',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(18,1,'Comprida','B543A678','Castanho,Creme','2007-10-16','Felideo','Pequenina',NULL,'Media,Lisa', 'Siames', 'Femea'),
(48,0,'Comprida','G5412A34','Creme','2018-07-10','Canideo','Boneca',NULL,'Curta,Ondulada', 'Podengo', 'Macho'),
(49,1,'Comprida','P1787422','Branco','2020-06-04','Felideo','Gaspar',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho'),
(30,0,'Curta','L974P102','Castanho,Branco','2004-09-24','Felideo','Kiko',NULL,'Curta,Lisa', 'Sem raca definida', 'Macho'),
(58,0,'Comprida','B4154782','Castanho,Branco,Preto','2011-07-03','Canideo','Podi',NULL,'Media,Ondulada', 'Sao Bernardo', 'Macho'),
(50,0,'Comprida','M1451452','Castanho','2015-10-20','Canideo','Puskas',NULL,'Media,Ondulada', 'Serra da Estrela', 'Macho'),
(40,1,'Comprida','N674A52','Creme','2008-07-17','Canideo','Felix',NULL,'Curta,Lisa', 'Labrador Retriever', 'Macho'),
(17,0,'Comprida','L986GT6','Branco,Castanho','2016-02-14','Felideo','Roberto',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(15,1,'Comprida','PLO9SAS','Branco,Castanho','2013-02-17','Felideo','Renato',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(25,1,'Comprida','N475241','Cinzento,Dourado','2020-07-16','Canideo','Patuda',NULL,'Media,Lisa', 'Yorkshire Terrier', 'Femea'),
(26,1,'Comprida','ML76542','Cinzento,Dourado','2014-05-13','Canideo','Bernie',NULL,'Curta,Lisa', 'Yorkshire Terrier', 'Macho'),
(30,0,'Comprida','B654A45','Castanho','2013-01-06','Canideo','Tai',NULL,'Curta,Lisa', 'Sem raca definida', 'Macho'),
(55,0,'Comprida','M457P95','Castanho,Preto','2013-03-11','Canideo','Ruby',NULL,'Media,Ondulada', 'Serra da Estrela', 'Macho'),
(60,1,'Comprida','N782713','Cinzento','2016-03-03','Canideo','Mel',NULL,'Curta,Lisa','Weimaraner','Macho'),
(22,0,'Comprida','S145A2S','Castanho,Preto','2021-01-14','Canideo','Polly',NULL,'Media,Lisa', 'Pastor Alemao', 'Femea'),
(36,1,'Comprida','R41ASAS','Branco,Castanho,Preto','2018-02-27','Canideo','Maxy',NULL,'Curta,Lisa', 'Beagle', 'Macho'),
(30,1,'Comprida','BH29SAS','Preto,Castanho','2012-09-01','Canideo','Blac',NULL,'Curta,Lisa', 'Teckel', 'Femea'),
(31,1,'Comprida','B7AS0AS','Preto,Castanho','2017-04-15','Canideo','Brie',NULL,'Curta,Lisa', 'Teckel', 'Macho'),
(32,0,'Curta','B45854S','Preto','2019-05-19','Canideo','Enso',NULL,'Curta,Lisa', 'Bulldog Frances', 'Macho'),
(33,1,'Comprida','PR5S4AS','Branco,Castanho,Preto','2017-02-17','Canideo','Kiki',NULL,'Curta,Lisa', 'Beagle', 'Femea'),
(23,1,'Comprida','9AS56A1','Castanho','2018-07-19','Canideo','Symba',NULL,'Curta,Lisa', 'Sem raca definida', 'Macho'),
(39,0,'Comprida','POL7845','Branco','2015-07-16','Canideo','Maia',NULL,'Comprida,Lisa', 'Samoiedo', 'Femea'),
(20,1,'Comprida','FA154S2A','Cinzento,Branco','2016-11-25','Canideo','Nyna','Pata partida; Sem canino frontal direito','Media,Lisa', 'Europeu', 'Macho'),
(22,1,'Comprida','FA87A456','Cinzento,Branco','2016-11-25','Canideo','Nick',NULL,'Media,Lisa', 'Europeu', 'Femea'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Pirata',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Pandora',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Geres',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Gin',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Belga',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Zip',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Zipp',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Atena',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Zeus',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Morpheus',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Hades',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Hera',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Hefesto',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Afrodite',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Artemis',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Hermes',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Cronus',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Cerberus',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','Plutao',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Minerva',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Apolo',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(28,1,'Comprida','FS154673','Castanho,Preto','2018-10-08','Canideo','Megara',NULL,'Media,Lisa', 'Europeu', 'Macho'),
(18,0,'Comprida','B6712839','Cinzento,Branco','2017-04-13','Canideo','Filoctetes',NULL,'Comprida,Lisa', 'Europeu', 'Femea'),
(26,1,'Comprida','A147AS52','Castanho,Branco','2016-11-25','Canideo','Pegasus',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(24,0,'Comprida','T521A241','Amarelo,Laranja','2014-07-03','Canideo','Atlanta',NULL,'Curta,Lisa', 'Europeu', 'Macho'),
(15,0,'Comprida','P1428752','Cinzento,Branco','2015-03-17','Canideo','edipo',NULL,'Curta,Lisa', 'Europeu', 'Femea'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Talia',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Dioniso',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho'),
(19,0,'Comprida','L147A564','Cinzento,Castanho','2016-02-14','Canideo','Hestia',NULL,'Curta,Lisa', 'Siames', 'Femea'),
(45,1,'Comprida','T54127AS','Branco','2019-08-17','Canideo','Hercules',NULL,'Curta,Encaracolada', 'Sem raca definida', 'Macho');

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
VALUES(1,'2021-06-14','Outros','Concluida','10:00','Consulta extraordinaria/Por doenca','Consulta',1,2, '2021-06-13','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(2,'2021-06-16','A decorrer','15:00','Consulta de Seguimento','Consulta',1,2, '2021-06-14');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(3,'2021-06-31','Agendada','15:00','Consulta de Seguimento','Consulta',1,2, '2021-06-16');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(4,'2021-05-14','ORQUIECTOMIA','Concluida','10:00','Cirurgia Normal','Cirurgia',1,2, '2021-05-07','ORQUIECTOMIA - CANINO
 EQUIPE CIRÚRGICA: 
Cirurgião: Jose Vieira 
 Auxiliar cirúrgico: Roberto Machado 
Instrumentador: Catia Silva 
 Anestesista: Diana Pires 
 PROCEDIMENTO: 
Pré-operatório:
Peso: 12 kg. FR: 28 mpm. FC: 70 bpm. TR: 39,5 °C.
Medicação pré-anestésica (MPA):
- Acepromazina 1%: 0,6ml
- Meperidina 5 %: 0,96ml
- Xilazina 2%: 0,3ml
- Flotril 10%: 12 ml
Indução anestésica:
- Propofol 4mg/kg
- Cloridrato de Cetamina
Anestesia Epidural:
- Lidocaína c/ vasoconstrictor - Meperidina
Foi realizada tricotomia na região dorsal ao nível das vértebras sacrais e lombares, e também na região ventral. A região dorsal recebeu assepsia com álcool e PVPI (povidona iodada) para realização da anestesia epidural. Foi administrado cloreto de sódio a 0,9% durante o procedimento operatório. Operatório:
Posicionamento do animal: O animal foi deixado em decúbito dorsal com as costas apoiadas num anteparo e teve suas patas amarradas à mesa.
A assepsia da região cirúrgica foi feita com álcool e PVPI (povidona iodada).
A orquiectomia em animais da espécie canina pode ser realizada por métodos abertos ou fechados. A incisão escrotal foi preferida em detrimento da incisão pré-escrotal. E as duas metodologias (aberta e fechada) foram demonstradas.
No método fechado a túnica vaginal parietal não é seccionada. No primeiro momento, este foi o método abordado. Em seguida a exteriorização do testículo, a fáscia espermática e o ligamento escrotal são submetidos à incisão nas proximidades do testículo, com tesoura. O tecido adiposo e a fáscia que circundam o cordão espermático foram rebatidos proximalmente com compressa de gaze. O cordão espermático foi maximamente exteriorizado e ficou pronto para receber a ligadura. Em seguida, no outro testículo, o método aberto foi abordado. No método aberto, é efetuada uma incisão através da túnica vaginal parietal que reveste o cordão espermático. As túnicas e o músculo cremaster foram separados do restante do cordão espermático.
A túnica vaginal parietal e o músculo cremaster foram ligados por uma ligadura circundante. Foram utilizadas duas pinças hemostáticas. O nó foi realizado anteriormente a primeira pinça e esta teve que ser afrouxada no momento da confecção do nó. Em seguida este seguimento foi seccionado.
Os componentes do cordão espermático foram duplamente ligados por uma ligadura por transfixação. Foram utilizadas duas pinças. O nó foi realizado anteriormente a primeira pinça e esta foi afrouxada no momento de confecção do nó. A agulha foi inserida entre o plexo pampiliforme e o ducto deferente. Em seguida este seguimento foi seccionado.
Após isso, o local de incisão recebeu assepsia com soro a 0,9% e o curativo utilizou furosemida tópico, sendo deixado aberto para permitir a drenagem. Os tecidos foram suturados com fio absorvível catgut 2.0.
Dr.  Jose Vieira');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(5,'2021-04-14','Pata Partida','Concluida','10:00','Cirurgia de Urgencia','Cirurgia',1,2, '2021-06-14','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(6,'2021-05-13','Outros','Concluida','10:00','Consulta extraordinaria/Por doenca','Consulta',2,3, '2021-05-13','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(7,'2021-06-16','A decorrer','14:00','Consulta de Seguimento','Consulta',2,3, '2021-06-15');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(8,'2021-07-01','Agendada','15:00','Consulta de Seguimento','Consulta',2,3, '2021-06-16');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(9,'2021-03-14','Castracao','Concluida','10:00','Cirurgia Normal','Cirurgia',2,3, '2021-03-07','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(10,'2021-04-01','Pata Partida','Concluida','10:00','Cirurgia de Urgencia','Cirurgia',2,3, '2021-04-01 ','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(11,'2021-06-13','Outros','Concluida','10:00','Consulta extraordinaria/Por doenca','Consulta',3,4, '2021-06-13','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(12,'2021-06-18','Agendada','15:00','Consulta de Seguimento','Consulta',3,4, '2021-06-13');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(13,'2021-06-31','Agendada','15:00','Consulta de Seguimento','Consulta',3,4, '2021-06-13');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(14,'2021-02-28','Castracao','Concluida','10:00','Cirurgia Normal','Cirurgia',3,4, '2021-02-21','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(15,'2021-04-01','Pata Partida','Concluida','10:00','Cirurgia de Urgencia','Cirurgia',3,4, '2021-04-01','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(16,'2021-06-10','Outros','Concluida','10:00','Consulta extraordinaria/Por doenca','Consulta',4,2, '2021-06-08','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(17,'2021-06-30','Agendada','15:00','Consulta de Seguimento','Consulta',4,2, '2021-06-10');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(18,'2021-07-6','Agendada','15:00','Consulta de Seguimento','Consulta',4,2, '2021-06-10');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(19,'2021-06-16','Castracao','Concluida','10:00','Cirurgia Normal','Cirurgia',1,3, '2021-06-07','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(20,'2021-04-14','Pata Partida','Concluida','10:00','Cirurgia de Urgencia','Cirurgia',1,3, '2021-06-14','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(21,'2021-05-31','Outros','Concluida','10:00','Consulta extraordinaria/Por doenca','Consulta',5,2, '2021-05-30','Falta de apetite do animal, foram receitadas vitaminas');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(22,'2021-06-17','Agendada','15:00','Consulta de Seguimento','Consulta',5,3, '2021-05-31');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`)
VALUES(23,'2021-06-20','Agendada','15:00','Consulta de Seguimento','Consulta',5,4, '2021-05-31');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(24,'2021-05-01','Castracao','Concluida','10:00','Cirurgia Normal','Cirurgia',5,4, '2021-04-22','Cirurgia realizada com sucesso');
INSERT INTO `vetgest`.`intervencao` (`id`,`data`,`descricao`,`estado`,`hora`,`motivo`,`tipo`,`animal_id`,`veterinario_id`,`data_pedido`,`observacoes`)
VALUES(25,'2021-06-101','Pata Partida','Concluida','10:00','Cirurgia de Urgencia','Cirurgia',5,4, '2021-06-01','Cirurgia realizada com sucesso');

INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`veterinario_id`)
VALUES(5,'2021-06-18','Atualizada',null,'Desparasitacao','Advantix',1,2);
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(4,'2021-05-16','Administrada','2021-06-18','Desparasitacao','Advantix',1,5,2,'2021-05-16');
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(3,'2021-04-16','Administrada','2021-05-16','Desparasitacao','Advantix',1,4,2,'2021-04-16');
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(2,'2021-03-16','Administrada','2021-04-16','Desparasitacao','Advantix',1,3,2,'2021-03-16');
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(1,'2021-02-15','Administrada','2021-03-16','Desparasitacao','Advantix',1,2,2,'2021-02-15');

INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`veterinario_id`)
VALUES(10,'2021-06-20','Atualizada',null,'Desparasitacao','Advantix',2,3);
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(9,'2021-05-20','Administrada','2021-06-20','Desparasitacao','Advantix',2,10,2,'2021-05-20');
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(8,'2021-04-20','Administrada','2021-05-20','Desparasitacao','Advantix',2,9,2,'2021-04-20');
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`prox_imunizacao`,`tipo`,`tratamento`,`animal_id`,`proxima_imunizacao_id`,`veterinario_id`,`data_toma`)
VALUES(7,'2021-03-20','Administrada','2021-04-20','Desparasitacao','Advantix',2,8,2,'2021-03-20');
INSERT INTO `vetgest`.`imunizacao`(`id`,`data`,`estado`,`tipo`,`tratamento`,`vacina`,`animal_id`,`veterinario_id`,`data_toma`)
VALUES(6,'2021-06-15','Administrada','Vacinacao','Vacina da Raiva','NOBIVAC RABIES',2,3,'2021-06-15');

INSERT INTO `vetgest`.`internamento` (`id`,`data`,`localizacao`,`motivo`,`animal_id`,`veterinario_id`,`estado`,`nota`,`intervencao_id`)
VALUES(1,'2021-05-14','C12','Fratura da pata dianteira',1,2,'Internado','Cirurgia sob anestesia geral',5);

INSERT INTO `vetgest`.`internamento` (`id`,`data`,`localizacao`,`motivo`,`animal_id`,`veterinario_id`,`estado`,`nota`,`intervencao_id`)
VALUES(2,'2021-04-22','C01','Castracao',3,2,'Internado','Cirurgia sob anestesia geral',14);

INSERT INTO `vetgest`.`historico` (`id`,`alergias`,`antecedentes`,`historia_ginecologica`,`historia_medica`,`medicacao`,`tipo_alergias`,`transfusoes`,`animal_id`)
VALUES(1,1,'Fratura da pata dianteira', 'Fratura de canino superior direito', 'Castracao','Antibiotico apos cirurgias realizadas.','Acaros',0,1);
