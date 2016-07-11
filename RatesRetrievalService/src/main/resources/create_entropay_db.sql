drop database if exists entropay;

CREATE DATABASE entropay DEFAULT CHARACTER SET utf8 ;

drop table if exists entropay.rate;

CREATE TABLE entropay.rate (
  id int(11) NOT NULL AUTO_INCREMENT,
  buyCurrency varchar(255) DEFAULT NULL,
  file varchar(255) DEFAULT NULL,
  rate decimal(19,2) DEFAULT NULL,
  sellCurrency varchar(255) DEFAULT NULL,
  validDate timestamp NULL ,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--INSERT INTO entropay.rate (buyCurrency, file, rate, sellCurrency, validDate) VALUES ('EUR', 'aaa.txt', '5.0', 'PLN', '2016-01-01');

commit;
