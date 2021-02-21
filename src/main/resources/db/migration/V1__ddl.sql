CREATE TABLE requests
  (
     id             UUID NOT NULL,
     request_date   TIMESTAMP NOT NULL,
     description    VARCHAR NOT NULL,
     PRIMARY KEY (id)
  );