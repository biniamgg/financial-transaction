CREATE SCHEMA IF NOT EXISTS test_schema;

CREATE TABLE test_schema.account (
                                       id bigint auto_increment,
                                       balance Decimal(19,4) NOT NULL,
                                       currency varchar NOT NULL,
                                       created_at timestamp NOT NULL,
                                       CONSTRAINT pk_account_id PRIMARY KEY (id)
                                 ) ;


CREATE TABLE test_schema.transaction (
                                   id bigint auto_increment,
                                   source_account_id bigint NOT NULL,
                                   target_account_id bigint NOT NULL,
                                   amount Decimal(19,4) NOT NULL,
                                   currency varchar NOT NULL,
                                   CONSTRAINT pk_transaction_id PRIMARY KEY (id),
                                   CONSTRAINT FK_transaction_source_account FOREIGN KEY (source_account_id) REFERENCES test_schema.account(id),
                                   CONSTRAINT FK_transaction_target_account FOREIGN KEY (target_account_id) REFERENCES test_schema.account(id)
                                     ) ;


insert into test_schema.account(balance, currency, created_at) values (1000.23, 'GBP', CURRENT_TIMESTAMP);
insert into test_schema.account(balance, currency, created_at) values (10.99000, 'GBP', CURRENT_TIMESTAMP);
insert into test_schema.account(balance, currency, created_at) values (100000.00, 'GBP', CURRENT_TIMESTAMP);

