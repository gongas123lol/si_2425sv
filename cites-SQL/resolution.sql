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
CREATE OR REPLACE TRIGGER ...
--TODO
-- endregion

-- region Question 1.b
CREATE OR REPLACE TRIGGER ...
--TODO
-- endregion

-- region Question 2
CREATE OR REPLACE FUCTION fx-dock-occupancy(stationkid integer) RETURNS ...
--TODO
-- endregion
 
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