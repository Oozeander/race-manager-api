CREATE TABLE race (
   id UUID NOT NULL,
   date DATE,
   number INTEGER NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE participant (
   id UUID NOT NULL,
   number INTEGER NOT NULL,
   name VARCHAR(255),
   id_race UUID NOT NULL,
   CONSTRAINT pk_partant PRIMARY KEY (id)
);

ALTER TABLE participant ADD CONSTRAINT FK_PARTICIPANT_ON_ID_RACE FOREIGN KEY (id_race) REFERENCES race (id);
