/**
 * MIT License
 * Copyright (c) 2025, Matilde Pato, ISEL-DEETC
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

begin;
-- Insert sample TYPEOFCARD data
INSERT INTO TYPEOFCARD (reference, nodays, price) VALUES
('resident', 365, 29.99),
('tourist', 7, 9.99);

-- Insert sample PERSON data
INSERT INTO PERSON (email, taxnumber, name) VALUES
('john.doe@email.com', 123456789, 'John Doe'),
('jane.smith@email.com', 987654321, 'Jane Smith'),
('bob.wilson@email.com', 456789123, 'Bob Wilson'),
('alice.johnson@email.com', 789123456, 'Alice Johnson'),
('emma.brown@email.com', 321654987, 'Emma Brown'),
('mike.davis@email.com', 654987321, 'Mike Davis');

-- Insert sample client data
INSERT INTO CLIENT (person, dtregister) VALUES
(1, '2025-01-01 10:00:00'),
(2, '2025-01-02 11:30:00'),
(3, '2025-01-03 14:15:00'),
(4, '2025-01-04 09:45:00');

-- Insert sample EMPLOYEE data
INSERT INTO EMPLOYEE (person) VALUES
(5),
(6);

-- Insert sample CARD data
INSERT INTO CARD (credit, typeofcard, client) VALUES
(5.00, 'resident', 1),
(7.50, 'resident', 2),
(3.25, 'tourist', 3),
(9.99, 'tourist', 4);

-- Insert sample STATION data
INSERT INTO STATION (latitude, longitude) VALUES
(38.7371, -9.3027),  -- Lisbon area
(38.7223, -9.1393),
(38.7166, -9.1377),
(38.7494, -9.1492);

-- Insert sample SCOOTERMODEL data
INSERT INTO SCOOTERMODEL (designation, autonomy) VALUES
('Basic Model', 25),
('Premium Model', 35),
('Pro Model', 45);

-- Insert sample SCOOTER data
INSERT INTO SCOOTER (weight, maxvelocity, battery, model) VALUES
(12.50, 25.00, 20, 1),
(13.75, 30.00, 30, 2),
(15.00, 35.00, 40, 3),
(12.50, 25.00, 18, 1),
(13.75, 30.00, 28, 2),
(15.00, 35.00, 38, 3);

-- Insert sample DOCK data
INSERT INTO DOCK (station, state, scooter) VALUES
(1, 'occupy', 1),
(1, 'free', NULL),
(1, 'under maintenance', NULL),
(2, 'occupy', 2),
(2, 'free', NULL),
(3, 'occupy', 3),
(3, 'under maintenance', NULL),
(4, 'occupy', 4),
(4, 'free', NULL);

-- Insert sample REPLACEMENTORDER data
INSERT INTO REPLACEMENTORDER (dorder, dreplacement, roccupation, station) VALUES
('2025-01-05 08:00:00', '2025-01-05 10:00:00', 85, 1),
('2025-01-06 09:00:00', '2025-01-06 11:30:00', 90, 2),
('2025-01-07 10:00:00', NULL, 75, 3),
('2025-01-08 11:00:00', '2025-01-08 14:00:00', 95, 4);

-- Insert sample REPLACEMENT data
INSERT INTO REPLACEMENT (dreplacement, action, reporder, repstation, employee) VALUES
('2025-01-05 10:00:00', 'remove', '2025-01-05 08:00:00', 1, 5),
('2025-01-06 11:30:00', 'inplace', '2025-01-06 09:00:00', 2, 6),
('2025-01-08 14:00:00', 'remove', '2025-01-08 11:00:00', 4, 5);

-- Insert sample TOPUP data
INSERT INTO TOPUP (dttopup, card, value) VALUES
('2025-01-10 12:00:00', 1, 10.00),
('2025-01-11 13:00:00', 2, 15.00),
('2025-01-12 14:00:00', 3, 20.00),
('2025-01-13 15:00:00', 4, 25.00);

-- Insert sample TRAVEL data
INSERT INTO TRAVEL (dinitial, comment, evaluation, dfinal, client, scooter, stinitial, stfinal) VALUES
('2025-01-15 10:00:00', 'Great ride!', 5, '2025-01-15 10:30:00', 1, 1, 1, 2),
('2025-01-16 11:00:00', 'Good experience', 4, '2025-01-16 11:45:00', 2, 2, 2, 3),
('2025-01-17 12:00:00', NULL, NULL, '2025-01-17 12:30:00', 3, 3, 3, 4),
('2025-01-18 13:00:00', 'Battery died too quickly', 2, '2025-01-18 13:15:00', 4, 4, 4, 1);

-- Make sure SERVICECOST has exactly one row with the specified values
TRUNCATE SERVICECOST;
INSERT INTO SERVICECOST (unlock, usable) VALUES (1.00, 0.15);
commit;