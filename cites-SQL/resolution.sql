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
CREATE OR REPLACE TRIGGER checkScooterInDock BEFORE INSERT ON travel
FOR EACH ROW EXECUTE PROCEDURE checkScooterInDock(new.scooter, new.stinitial);


CREATE OR REPLACE PROCEDURE checkScooterInDock(scooterId integer, stId integer)
AS $$
    DECLARE
        scooterInfo scooter%rowtype;
        dockInfo dock%rowtype;
        stationInfo station%rowtype;
    BEGIN
        ---------------------------------------------------------------------------
        SELECT INTO STRICT scooterInfo * FROM scooter WHERE scooter.id = scooterId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Scooter % does not exist', scooterId;
        END IF;
        ---------------------------------------------------------------------------
        SELECT INTO STRICT dockInfo * FROM dock WHERE dock.scooter = scooterId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Scooter % is not in any dock %', scooterId, stId;
        END IF;
        ---------------------------------------------------------------------------
        -- TODO(): CHECK IF EXTRA STEP ON STATION IS NEEDED
        SELECT INTO STRICT stationInfo * FROM station WHERE station.id = stId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Station % does not exist', stId;
        END IF;
        IF dockInfo.station != stationInfo.id THEN
            RAISE EXCEPTION 'Scooter % is not on station %', scooterId, stId;
        END IF;
    END
$$ LANGUAGE plpgsql;

-- region Question 1.b
-- uma trotineta e um utilizador só podem participar numa única viagem a decorrer.
CREATE OR REPLACE TRIGGER checkTravelActive BEFORE INSERT ON travel
FOR EACH ROW EXECUTE PROCEDURE checkTravelActive(new.scooter, new.client);

CREATE OR REPLACE PROCEDURE checkTravelActive(scooterId integer, clientId integer)
AS $$
    DECLARE
        scooterInfo scooter%rowtype;
        clientInfo client%rowtype;
        RS SETOF travel%rowtype;
    BEGIN
        SELECT INTO STRICT scooterInfo * FROM scooter WHERE scooter.id = scooterId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Scooter % does not exist', scooterId;
        END IF;
        SELECT INTO STRICT clientInfo * FROM client WHERE client.person = clientId;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Client % does not exist', clientId;
        END IF;
        SELECT INTO RS * FROM travel WHERE travel.scooter = scooterId AND travel.client = clientId AND travel.dfinal IS NULL;
        IF COUNT(RS) > 1 THEN
            RAISE EXCEPTION 'Scooter % and client % are already in an active trip', scooterId, clientId;
        END IF;
    END;
$$ LANGUAGE plpgsql;


-- region Question 2
-- que calcula e devolve o nível de ocupação de uma estação (em percentagem, valor entre 0 e 1);
CREATE OR REPLACE FUNCTION fx_dock_occupancy(dockId integer) RETURNS INTEGER
AS $$
    DECLARE
        stationInfo station%rowtype;
    BEGIN
        SELECT INTO STRICT stationInfo *
        FROM station WHERE station.id = (SELECT station FROM dock WHERE dock.number = dockId);
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Dock % does not exist', dockId;
        END IF;

        -- Consider only usable docks (Not the ones that are under maintenance)
        RETURN (SELECT count(*) FROM dock WHERE dock.station = stationInfo.id AND dock.state = 'occupy')/
        (SELECT count(*) FROM dock WHERE dock.station = stationInfo.id AND (dock.state = 'occupy' OR dock.state = 'free'));
    END;
$$ LANGUAGE plpgsql;
 
-- region Question 3
CREATE OR REPLACE VIEW RIDER
AS
SELECT p.*,c.dtregister,cd.id AS cardid,cd.credit,cd.typeofcard
FROM CLIENT c INNER JOIN PERSON p ON (c.person=p.id)
	INNER JOIN CARD cd ON (cd.client = c.person);
--TODO
-- endregion

-- region Question 4
CREATE OR REPLACE PROCEDURE startTrip(dockid integer, clientid  integer) ...
--TODO
-- endregion