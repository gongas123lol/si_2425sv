/**
 * MIT License
 * Copyright (c) 2024, Matilde Pato, ISEL-DEETC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
*/

/**
 * 
 * Didactic material to support 
 * the Information Systems course
 *
 * The examples may not be complete and/or totally correct.
 * They are made available for teaching purposes and 
 * Any inaccuracies are the subject of discussion in classes.
 * */

START TRANSACTION;
DROP VIEW IF EXISTS RIDER;
DROP TABLE IF EXISTS SERVICECOST;
DROP TABLE IF EXISTS TRAVEL;
DROP TABLE IF EXISTS TOPUP;
DROP TABLE IF EXISTS REPLACEMENT;
DROP TABLE IF EXISTS REPLACEMENTORDER;
DROP TABLE IF EXISTS DOCK;
DROP TABLE IF EXISTS SCOOTER;
DROP TABLE IF EXISTS SCOOTERMODEL;
DROP TABLE IF EXISTS STATION;
DROP TABLE IF EXISTS CARD;
DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS PERSON;
DROP TABLE IF EXISTS TYPEOFCARD;


-- Create CARDTYPE table
CREATE TABLE IF NOT EXISTS TYPEOFCARD (
    reference CHAR(10) PRIMARY KEY CHECK (reference IN ('resident', 'tourist')),
    nodays INTEGER CHECK (nodays > 0),
    price NUMERIC(4,2) CHECK (price > 0)
);

-- Create PERSON table
CREATE TABLE IF NOT EXISTS PERSON (
    id serial PRIMARY KEY,
    email VARCHAR(40) UNIQUE CHECK (email LIKE '%@%'),
    taxnumber INTEGER UNIQUE,
    name VARCHAR(50) NOT NULL
);

-- Create USER table
CREATE TABLE IF NOT EXISTS CLIENT (
    person INTEGER PRIMARY KEY REFERENCES PERSON(id),
    dtregister TIMESTAMP NOT NULL
);

-- Create EMPLOYEE table
CREATE TABLE IF NOT EXISTS EMPLOYEE (
    number serial unique,
    person INTEGER PRIMARY KEY REFERENCES PERSON(id)
);

-- Create CARD table
CREATE TABLE IF NOT EXISTS CARD (
    id serial PRIMARY KEY,
    credit NUMERIC(4,2) CHECK (credit >= 0),
    typeofcard CHAR(10) REFERENCES TYPEOFCARD(reference),
    client INTEGER REFERENCES CLIENT(person)
);

-- Create STATION table
CREATE TABLE IF NOT EXISTS STATION (
    id serial PRIMARY KEY,
    latitude NUMERIC(6,4) NOT NULL,
    longitude NUMERIC(6,4) NOT NULL
);

-- Create SCOOTERMODEL table
CREATE TABLE IF NOT EXISTS SCOOTERMODEL (
    number serial PRIMARY KEY,
    designation VARCHAR(30) NOT NULL,
    autonomy INTEGER CHECK (autonomy > 0) NOT NULL 
);

-- Create SCOOTER table
CREATE TABLE IF NOT EXISTS SCOOTER (
    id serial PRIMARY KEY,
    weight NUMERIC(4,2) CHECK (weight > 0) NOT NULL,
    maxvelocity NUMERIC(4,2) CHECK (maxvelocity > 0) NOT NULL,
    battery INTEGER CHECK (battery > 0) NOT NULL,
    model INTEGER REFERENCES SCOOTERMODEL(number) NOT null,
    version TIMESTAMP NULL 
);


-- Create DOCK table
CREATE TABLE IF NOT EXISTS DOCK (
    number serial PRIMARY KEY,
    station INTEGER REFERENCES STATION(id) NOT NULL,
    state VARCHAR(30) NOT NULL CHECK (state IN ('free', 'occupy', 'under maintenance')),
    scooter INTEGER REFERENCES SCOOTER(id),
    CONSTRAINT dock_state_check1 CHECK (
        (state != 'occupy' AND scooter IS NULL) OR
        (state = 'occupy' AND scooter IS NOT NULL)
    ),
     version TIMESTAMP NULL 
);

-- Create REPLACEMENTORDER table
CREATE TABLE IF NOT EXISTS REPLACEMENTORDER (
    dorder TIMESTAMP,
    dreplacement TIMESTAMP CHECK (dreplacement > dorder OR dreplacement IS NULL),
    roccupation INTEGER NOT NULL CHECK (roccupation BETWEEN 0 AND 100),
    station INTEGER REFERENCES STATION(id) NOT NULL,
    PRIMARY KEY (dorder, station)
);


-- Create REPLACEMENT table
CREATE TABLE IF NOT EXISTS REPLACEMENT (
    number serial PRIMARY KEY,
    dreplacement TIMESTAMP NOT NULL,
    action CHAR(8) CHECK (action IN ('inplace', 'remove')),
    reporder TIMESTAMP NOT NULL,
    repstation INTEGER NOT NULL,
    employee INTEGER NOT NULL REFERENCES EMPLOYEE(person),
    FOREIGN KEY (reporder, repstation) REFERENCES REPLACEMENTORDER(dorder, station),
    CONSTRAINT replacement_date_check CHECK (dreplacement > reporder)
);

-- Create TOPUP table
CREATE TABLE IF NOT EXISTS TOPUP (
    dttopup TIMESTAMP,
    card INTEGER REFERENCES CARD(id),
    value NUMERIC(4,2) NOT NULL CHECK (value > 0),
    PRIMARY KEY (dttopup, card)
);

-- Create TRAVEL table
CREATE TABLE IF NOT EXISTS TRAVEL (
    dinitial TIMESTAMP,
    comment VARCHAR(100),
    evaluation INTEGER CHECK (evaluation BETWEEN 1 AND 5 OR evaluation IS NULL),
    dfinal TIMESTAMP CHECK (dfinal > dinitial OR dfinal IS NULL),
    client INTEGER NOT NULL REFERENCES CLIENT(person),
    scooter INTEGER NOT NULL REFERENCES SCOOTER(id),
    stinitial INTEGER NOT NULL REFERENCES STATION(id),
    stfinal INTEGER REFERENCES STATION(id),
    PRIMARY KEY (dinitial, scooter),
    CONSTRAINT comment_evaluation_check CHECK (
        (comment IS NULL AND evaluation IS NULL) OR
        (comment IS NOT NULL AND evaluation IS NOT NULL)
    )
);

-- Create SERVICECOST table
CREATE TABLE IF NOT EXISTS SERVICECOST (
    unlock NUMERIC(3,2) DEFAULT 1.00 ,
    usable NUMERIC(3,2) DEFAULT 0.15
);

CREATE OR REPLACE VIEW RIDER
AS
SELECT p.*,c.dtregister,cd.id AS cardid,cd.credit,cd.typeofcard
FROM CLIENT c INNER JOIN PERSON p ON (c.person=p.id)
	INNER JOIN CARD cd ON (cd.client = c.person);

-- Insert default service costs
INSERT INTO SERVICECOST (unlock, usable) VALUES (1.00, 0.15); --HELPER TABLE that has the CURRENT cost FOR the scooter USAGE.
COMMIT;