/*
 *   ISEL-DEETC-SisInf
 *   ND 2022-2025
 *
 *   
 *   Information Systems Project - Active Databases
 *   
 */

/* ### DO NOT REMOVE THE QUESTION MARKERS ### */


-- region Question 1.a
-- só uma trotineta que está numa doca pode ser usada no início de uma viagem
CREATE OR REPLACE FUNCTION checkScooterInDock() RETURNS TRIGGER
AS $$
DECLARE
    scooterId integer := NEW.scooter;
    stId integer := NEW.stinitial;
    scooterInfo scooter%rowtype;
    dockInfo dock%rowtype;
    stationInfo station%rowtype;
BEGIN
    SELECT INTO STRICT scooterInfo * FROM scooter WHERE scooter.id = scooterId;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Scooter % does not exist', scooterId;
    END IF;

    SELECT INTO STRICT dockInfo * FROM dock WHERE dock.scooter = scooterId;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Scooter % is not in any dock %', scooterId, stId;
    END IF;

    SELECT INTO STRICT stationInfo * FROM station WHERE station.id = stId;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Station % does not exist', stId;
    END IF;

    IF dockInfo.station != stationInfo.id THEN
        RAISE EXCEPTION 'Scooter % is not on station %', scooterId, stId;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
---------------------------------------------------
CREATE OR REPLACE TRIGGER checkScooterInDock BEFORE INSERT ON travel
    FOR EACH ROW EXECUTE PROCEDURE checkScooterInDock();

-- region Question 1.b
-- uma trotineta e um utilizador só podem participar numa única viagem a decorrer.
CREATE OR REPLACE FUNCTION checkTravelActive() RETURNS TRIGGER
AS $$
    DECLARE
        scooterId integer := NEW.scooter;
        clientId integer := NEW.client;
        scooterInfo scooter%rowtype;
        clientInfo client%rowtype;
    BEGIN
        SELECT INTO STRICT scooterInfo * FROM scooter WHERE scooter.id = scooterId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Scooter % does not exist', scooterId;
        END IF;
        SELECT INTO STRICT clientInfo * FROM client WHERE client.person = clientId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Client % does not exist', clientId;
        END IF;
        IF COUNT((SELECT dinitial FROM travel WHERE travel.scooter = scooterId AND travel.client = clientId AND travel.dfinal IS NULL)) >= 1 THEN
            RAISE EXCEPTION 'Scooter % and client % are already in an active trip', scooterId, clientId;
        END IF;
        return NEW;
    END;
$$ LANGUAGE plpgsql;
----------------------------------------------------
CREATE OR REPLACE TRIGGER checkTravelActive BEFORE INSERT ON travel
    FOR EACH ROW EXECUTE PROCEDURE checkTravelActive();


-- region Question 2
-- que calcula e devolve o nível de ocupação de uma estação (em percentagem, valor entre 0 e 1);
CREATE OR REPLACE FUNCTION fx_dock_occupancy(dockId integer) RETURNS NUMERIC(4,2)
AS $$
DECLARE
    station_id INTEGER;
    occupied NUMERIC(4,2);
    total NUMERIC(4,2);
BEGIN
    -- Obter o id da estação associada à doca
    SELECT station INTO STRICT station_id FROM dock WHERE number = dockId;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Dock % does not exist', dockId;
    END IF;

    -- Contar docas ocupadas e total de docas na estação
    SELECT COUNT(*)::NUMERIC(4,2) INTO occupied FROM dock WHERE station = station_id AND state = 'occupy';
    SELECT COUNT(*)::NUMERIC(4,2) INTO total FROM dock WHERE station = station_id;

    IF total = 0 THEN
        RETURN 0;
    END IF;

    RETURN (occupied / total)::NUMERIC(4,2);
END;
$$ LANGUAGE plpgsql;
 
-- region Question 3
CREATE OR REPLACE VIEW RIDER
AS
SELECT p.*,c.dtregister,cd.id AS cardid,cd.credit,cd.typeofcard
FROM CLIENT c INNER JOIN PERSON p ON (c.person=p.id)
	INNER JOIN CARD cd ON (cd.client = c.person);
---------------------------------------------------
CREATE OR REPLACE FUNCTION insert_rider()
RETURNS trigger AS
$$
DECLARE
    person_id INTEGER;
    rider RIDER%ROWTYPE;
BEGIN
    -- Inserir na tabela base PERSON
    INSERT INTO PERSON(name, email, taxNumber)
    VALUES (NEW.name, NEW.email, NEW.taxNumber);

    SELECT INTO STRICT person_id id FROM PERSON WHERE taxNumber = NEW.taxNumber;

    -- Inserir na CLIENT
    INSERT INTO CLIENT(person, dtregister)
    VALUES (person_id, NEW.dtregister);

    -- Inserir na CARD
    INSERT INTO CARD(client, credit, typeofcard)
    VALUES (person_id, NEW.credit, NEW.typeofcard);

    SELECT INTO rider * FROM RIDER WHERE taxnumber = NEW.taxNumber;
    RETURN rider;
END;
$$ LANGUAGE plpgsql;
---------------------------------------------------
CREATE OR REPLACE FUNCTION update_rider()
RETURNS trigger AS
$$
BEGIN
    -- Atualizar PERSON
    UPDATE PERSON
    SET name = NEW.name,
        email = NEW.email,
        taxNumber = NEW.taxNumber
    WHERE id = OLD.id;

    -- Atualizar CLIENT
    UPDATE CLIENT
    SET dtregister = NEW.dtregister
    WHERE person = OLD.id;

    -- Atualizar CARD
    UPDATE CARD
    SET credit = NEW.credit,
        typeofcard = NEW.typeofcard
    WHERE id = OLD.cardid;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
---------------------------------------------------------
CREATE OR REPLACE TRIGGER rider_insert
INSTEAD OF INSERT ON RIDER
FOR EACH ROW
EXECUTE FUNCTION insert_rider();

CREATE OR REPLACE TRIGGER rider_update
INSTEAD OF UPDATE ON RIDER
FOR EACH ROW
EXECUTE FUNCTION update_rider();
---------------------------------------------------------
-- endregion

-- region Question 4
CREATE OR REPLACE PROCEDURE startTrip(dockid INTEGER, clientid INTEGER)
LANGUAGE plpgsql
AS
$$
DECLARE
    scooter_id INTEGER;
    station_id INTEGER;
BEGIN
    -- Verifica se o cliente existe
    IF NOT EXISTS (SELECT 1 FROM CLIENT WHERE person = clientid) THEN
        RAISE EXCEPTION 'Client % does not exist', clientid;
    END IF;

    -- Obtem a scooter associada ao dock se estiver ocupada
    SELECT scooter, station INTO scooter_id, station_id
    FROM DOCK
    WHERE number = dockid AND state = 'occupy';

    -- Verifica se existe scooter disponível no dock
    IF scooter_id IS NULL THEN
        RAISE EXCEPTION 'No scooter available at dock %', dockid;
    END IF;

    -- Insere nova viagem na TRAVEL
    INSERT INTO TRAVEL (
        dinitial, client, scooter, stinitial
    ) VALUES (
        CURRENT_TIMESTAMP, clientid :: INTEGER, scooter_id:: INTEGER, station_id:: INTEGER
    );

    -- Atualiza o dock para indicar que está livre
    UPDATE DOCK
    SET scooter = NULL,
        state = 'free',
        version = CURRENT_TIMESTAMP
    WHERE number = dockid;

    RAISE NOTICE 'Trip started: client %, scooter %, from dock %', clientid, scooter_id, dockid;
END;
$$;

-- endregion