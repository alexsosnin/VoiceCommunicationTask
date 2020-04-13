DROP TABLE IF EXISTS vct_manager CASCADE

CREATE TABLE vct_manager (
  id           SERIAL PRIMARY KEY,
  deputy_id    INTEGER REFERENCES vct_manager( id ) ON DELETE RESTRICT,
  name         TEXT,
  patronymic   TEXT,
  surname      TEXT,
  phone_number TEXT,
  removal_date TIMESTAMP
);

DROP TABLE IF EXISTS vct_client CASCADE

CREATE TABLE vct_client (
  id            SERIAL PRIMARY KEY,
  manager_id    INTEGER REFERENCES vct_manager( id ) ON DELETE RESTRICT,
  code          TEXT,
  name          TEXT,
  legal_address TEXT,
  removal_date  TIMESTAMP
);


