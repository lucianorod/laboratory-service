CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `complement` varchar(255) DEFAULT NULL,
  `neighborhood` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `exam_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gksn550gjdnp9pugu1yinnd1i` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_removed` bit(1) DEFAULT NULL,
  `exam_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qkjn1bn0arx376uotsywsy9bg` (`name`),
  KEY `FKfahd9o24dqkyeyup3wrgyu8ql` (`exam_type_id`),
  CONSTRAINT `FKfahd9o24dqkyeyup3wrgyu8ql` FOREIGN KEY (`exam_type_id`) REFERENCES `exam_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `laboratory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_removed` bit(1) DEFAULT NULL,
  `address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5km042lu82f2s4sh79qtv7cjq` (`address_id`),
  CONSTRAINT `FK5km042lu82f2s4sh79qtv7cjq` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `laboratory_exam` (
  `laboratory_id` bigint(20) NOT NULL,
  `exam_id` bigint(20) NOT NULL,
  PRIMARY KEY (`laboratory_id`,`exam_id`),
  KEY `FK80xshp7dn2rtyionkgtcqanpr` (`exam_id`),
  CONSTRAINT `FK80xshp7dn2rtyionkgtcqanpr` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `FKmmoetm4vxisfay1il7ubsgqxm` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `exam_type` (name) VALUES ('ANALISE CLINICA');
INSERT INTO `exam_type` (name) VALUES ('IMAGEM');