DROP DATABASE IF EXISTS travel_agency;--
CREATE DATABASE travel_agency;--
USE travel_agency;
--
CREATE TABLE document_types (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40)
);
--
CREATE TABLE customers (
    id VARCHAR(20)  NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    age INT,
    id_document INT,
    CONSTRAINT fk_customer_document_type FOREIGN KEY (id_document) REFERENCES document_types(id)
);
--
CREATE TABLE flight_fares (
    id INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(40),
    details TEXT,
    value DOUBLE(7,3)
);
--
CREATE TABLE trips (
    id INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    trip_date DATE,
    price_trip DOUBLE
);
--
CREATE TABLE trip_booking (
    id INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    date DATE,
    id_trip INT,
    CONSTRAINT fk_trip_booking_trips FOREIGN KEY (id_trip) REFERENCES trips(id)
);
--
CREATE TABLE trip_booking_details (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_trip_booking INT,
    id_customers VARCHAR(20),
    id_fares INT,
    total_price DOUBLE NOT NULL,
    CONSTRAINT fk_details_trip_booking FOREIGN KEY (id_trip_booking) REFERENCES trip_booking(id),
    CONSTRAINT fk_booking_details_customers FOREIGN KEY (id_customers) REFERENCES customers(id),
    CONSTRAINT fk_booking_details_fares FOREIGN KEY (id_fares) REFERENCES flight_fares(id)
);
--
CREATE TABLE payment_methods (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30)
);
--
CREATE TABLE payments (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_trip_booking_details INT,
    id_payment_method INT,
    card_number INT,
    CONSTRAINT fk_payments_booking_details FOREIGN KEY (id_trip_booking_details) REFERENCES trip_booking_details (id),
    CONSTRAINT fk_payments_payment_method FOREIGN KEY (id_payment_method) REFERENCES payment_methods (id)
);
--
CREATE TABLE countries (
  id VARCHAR(5) NOT NULL PRIMARY KEY,
  name VARCHAR(30) NOT NULL
);
--
CREATE TABLE cities (
    id VARCHAR(5) NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    id_country VARCHAR(5) NOT NULL,
    
    CONSTRAINT fk_cities_countries FOREIGN KEY (id_country) REFERENCES countries (id)
);
--
CREATE TABLE airports (
    id VARCHAR(5) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    id_city VARCHAR(5) NOT NULL,
    
    CONSTRAINT fk_airports_cities FOREIGN KEY ( id_city ) REFERENCES cities (id)
);
--
CREATE TABLE gates (
    id INTEGER  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gate_number VARCHAR(10) NOT NULL,
    id_airport VARCHAR(5),
    
    CONSTRAINT fk_gates_airports FOREIGN KEY ( id_airport ) REFERENCES airports (id)
);
--
CREATE TABLE airlines (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);
--
CREATE TABLE tripulation_roles (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40)
);
--
CREATE TABLE employees (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    id_rol INTEGER NOT NULL,
    ingress_date DATE,
    id_airline INTEGER NOT NULL,
    id_airport VARCHAR(5),
    
    CONSTRAINT fk_employees_tripulation_roles FOREIGN KEY ( id_rol ) REFERENCES tripulation_roles (id),
    CONSTRAINT fk_employees_airlines FOREIGN KEY ( id_airline ) REFERENCES airlines (id),
    CONSTRAINT fk_employees_airports FOREIGN KEY ( id_airport ) REFERENCES airports (id)
);
--
CREATE TABLE revision_details (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    description TEXT,
    id_employee VARCHAR(20) NOT NULL,
    
    CONSTRAINT fk_revision_details_employees FOREIGN KEY ( id_employee ) REFERENCES employees (id)
);
--
CREATE TABLE manufacturers (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40)
);
--
CREATE TABLE models (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    id_manufacturer INT,
    CONSTRAINT fk_models_manufacturers FOREIGN KEY (id_manufacturer) REFERENCES manufacturers(id)
);
--
CREATE TABLE statuses (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30)
);
--
CREATE TABLE planes (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    plates VARCHAR(30),
    capacity INT,
    fabrication_date DATE,
    id_status INT,
    id_model INT,
    CONSTRAINT fk_planes_statuses FOREIGN KEY (id_status) REFERENCES statuses(id),
    CONSTRAINT fk_planes_models FOREIGN KEY (id_model) REFERENCES models(id)
);
--
CREATE TABLE revisions (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    revision_date DATE,
    id_plane INT,
    CONSTRAINT fk_revisions_planes FOREIGN KEY (id_plane) REFERENCES planes(id)
);
--
CREATE TABLE rev_employee (
    id_employee VARCHAR(20) NOT NULL,
    id_revision INT NOT NULL,
    PRIMARY KEY (id_employee, id_revision)
);
--
CREATE TABLE flight_connections (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  connection_number VARCHAR(20),
  id_trip INTEGER NOT NULL,
  id_plane INTEGER,
  id_origin VARCHAR(5),
  id_destination VARCHAR(5),
  CONSTRAINT fk_flight_connections_trips FOREIGN KEY ( id_trip ) REFERENCES trips (id),
  CONSTRAINT fk_flight_connections_planes FOREIGN KEY ( id_plane ) REFERENCES planes (id),
  CONSTRAINT fk_flight_connections_airport_origin FOREIGN KEY ( id_origin ) REFERENCES airports (id),
  CONSTRAINT fk_flight_connections_airport_destination FOREIGN KEY ( id_destination ) REFERENCES airports (id)
);
--
CREATE TABLE trip_crews (
  id_employee VARCHAR(20) NOT NULL,
  id_connection INTEGER NOT NULL,
  CONSTRAINT fk_trip_crews_employees FOREIGN KEY ( id_employee ) REFERENCES employees (id),
  CONSTRAINT fk_trip_crews_connections FOREIGN KEY ( id_connection ) REFERENCES flight_connections (id)
);
--
DROP PROCEDURE IF EXISTS SeleccionarVuelo;
--
CREATE PROCEDURE SeleccionarVuelo (IN idCustomer VARCHAR(20), IN idFlight INT, IN idFares INT, IN Date DATE, IN totalPrice DOUBLE)
    BEGIN
        DECLARE idTrip INT;
        DECLARE idTripBooking INT;
        
        SELECT id_trip INTO idTrip FROM flight_connections WHERE id = idFlight;
        
        INSERT INTO trip_booking (date, id_trip) VALUES
        (Date, idTrip);

        SELECT MAX(id) INTO idTripBooking FROM trip_booking;

        INSERT INTO trip_booking_details (id_trip_booking, id_customers, id_fares, total_price) VALUES
            (idTripBooking, idCustomer, idFares, totalPrice);
    END;
--
INSERT INTO document_types (name) VALUES 
    ('Passport'),
    ('ID Card'),
    ('Driver License'),
    ('Residence Permit'),
    ('Military ID'),
    ('Student ID');
--
INSERT INTO customers (id, name, age, id_document) VALUES 
    ('CUST001', 'John Doe', 30, 1),
    ('CUST002', 'Jane Smith', 25, 2),
    ('CUST003', 'Alice Johnson', 28, 3),
    ('CUST004', 'Bob Brown', 35, 4),
    ('CUST005', 'Carol White', 22, 5),
    ('CUST006', 'Eve Black', 27, 6);
--
INSERT INTO flight_fares (description, details, value) VALUES
    ('Economy', 'Economy class fare', 150.000),
    ('Business', 'Business class fare', 300.000),
    ('First Class', 'First class fare', 500.000);
--
INSERT INTO trips (trip_date, price_trip) VALUES 
    ('2024-07-01', 1200.00),
    ('2024-07-02', 1500.00),
    ('2024-07-03', 1800.00),
    ('2024-07-04', 2000.00),
    ('2024-07-05', 2200.00),
    ('2024-07-06', 2500.00),
    ('2024-07-01', 1000.00),
    ('2024-07-15', 1500.00),
    ('2024-08-01', 2000.00);

--
INSERT INTO trip_booking (date, id_trip) VALUES 
    ('2024-06-01', 1),
    ('2024-06-02', 2),
    ('2024-06-03', 3),
    ('2024-06-04', 4),
    ('2024-06-05', 5),
    ('2024-06-06', 6);
--
INSERT INTO trip_booking_details (id_trip_booking, id_customers, id_fares, total_price) VALUES 
    (1, 'CUST001', 1, 1350.00),
    (2, 'CUST002', 2, 1800.00),
    (3, 'CUST003', 3, 2300.00),
    (4, 'CUST004', 3, 2500.00),
    (5, 'CUST005', 1, 2350.00),
    (6, 'CUST006', 1, 2650.00);
--
INSERT INTO payment_methods (name) VALUES 
    ('Credit Card'),
    ('Debit Card'),
    ('PayPal'),
    ('Cash'),
    ('Bank Transfer');
--
INSERT INTO countries (id, name) VALUES 
    ('USA', 'United States'),
    ('CAN', 'Canada'),
    ('MEX', 'Mexico'),
    ('BRA', 'Brazil'),
    ('ARG', 'Argentina'),
    ('COL', 'Colombia');
--
INSERT INTO cities (id, name, id_country) VALUES 
    ('NYC', 'New York', 'USA'),
    ('LAX', 'Los Angeles', 'USA'),
    ('YVR', 'Vancouver', 'CAN'),
    ('YYZ', 'Toronto', 'CAN'),
    ('MEX', 'Mexico City', 'MEX'),
    ('GDL', 'Guadalajara', 'MEX');
--
INSERT INTO airports (id, name, id_city) VALUES 
    ('JFK', 'John F. Kennedy International Airport', 'NYC'),
    ('LAX', 'Los Angeles International Airport', 'LAX'),
    ('YVR', 'Vancouver International Airport', 'YVR'),
    ('YYZ', 'Toronto Pearson International Airport', 'YYZ'),
    ('MEX', 'Mexico City International Airport', 'MEX'),
    ('GDL', 'Guadalajara International Airport', 'GDL');
--
INSERT INTO gates (gate_number, id_airport) VALUES 
    ('A1', 'JFK'),
    ('B2', 'LAX'),
    ('C3', 'YVR'),
    ('D4', 'YYZ'),
    ('E5', 'MEX'),
    ('F6', 'GDL');
--
INSERT INTO airlines (name) VALUES 
    ('American Airlines'),
    ('Delta Airlines'),
    ('United Airlines'),
    ('Air Canada'),
    ('Aeromexico'),
    ('LATAM Airlines');
--
INSERT INTO tripulation_roles (name) VALUES 
    ('Pilot'),
    ('Co-Pilot'),
    ('Flight Attendant'),
    ('Ground Crew'),
    ('Air Traffic Controller'),
    ('Maintenance Technician');
--
INSERT INTO employees (id, name, id_rol, ingress_date, id_airline, id_airport) VALUES 
    ('EMP001', 'Tom Hanks', 1, '2020-01-01', 1, 'JFK'),
    ('EMP002', 'Emma Watson', 2, '2020-02-01', 2, 'LAX'),
    ('EMP003', 'Robert Downey Jr.', 3, '2020-03-01', 3, 'YVR'),
    ('EMP004', 'Chris Evans', 4, '2020-04-01', 4, 'YYZ'),
    ('EMP005', 'Scarlett Johansson', 5, '2020-05-01', 5, 'MEX'),
    ('EMP006', 'Chris Hemsworth', 6, '2020-06-01', 6, 'GDL');
--
INSERT INTO revision_details (id, description, id_employee) VALUES 
    ('REV001', 'Routine maintenance', 'EMP001'),
    ('REV002', 'Engine check', 'EMP002'),
    ('REV003', 'Avionics upgrade', 'EMP003'),
    ('REV004', 'Fuel system check', 'EMP004'),
    ('REV005', 'Navigation system check', 'EMP005'),
    ('REV006', 'Safety equipment check', 'EMP006');
--
INSERT INTO manufacturers (name) VALUES 
    ('Boeing'),
    ('Airbus'),
    ('Embraer'),
    ('Bombardier'),
    ('Cessna'),
    ('Gulfstream');
--
INSERT INTO models (name, id_manufacturer) VALUES 
    ('737', 1),
    ('A320', 2),
    ('E175', 3),
    ('CRJ700', 4),
    ('Citation X', 5),
    ('G650', 6);
--
INSERT INTO statuses (name) VALUES 
    ('Operational'),
    ('Under Maintenance'),
    ('Decommissioned'),
    ('Retired'),
    ('Stored'),
    ('Sold');
--
INSERT INTO planes (plates, capacity, fabrication_date, id_status, id_model) VALUES 
    ('N12345', 180, '2010-05-15', 1, 1),
    ('N67890', 150, '2012-07-20', 1, 2),
    ('N54321', 200, '2015-11-25', 2, 3),
    ('N09876', 70, '2018-02-10', 1, 4),
    ('N11223', 12, '2020-09-05', 1, 5),
    ('N33445', 18, '2021-06-30', 1, 6);
--
INSERT INTO revisions (revision_date, id_plane) VALUES 
    ('2024-06-15', 1),
    ('2024-06-16', 2),
    ('2024-06-17', 3),
    ('2024-06-18', 4),
    ('2024-06-19', 5),
    ('2024-06-20', 6);
--
INSERT INTO rev_employee (id_employee, id_revision) VALUES 
    ('EMP001', 1),
    ('EMP002', 2),
    ('EMP003', 3),
    ('EMP004', 4),
    ('EMP005', 5),
    ('EMP006', 6);
--
INSERT INTO flight_connections (connection_number, id_trip, id_plane, id_origin, id_destination) VALUES 
    ('CON001', 1, 1, 'JFK', 'YYZ'),
    ('CON002', 2, 2, 'YYZ', 'MEX'),
    ('CON003', 3, 3, 'MEX', 'GDL'),
    ('CON004', 4, 4, 'GDL', 'LAX'),
    ('CON005', 5, 5, 'LAX', 'YVR'),
    ('CON006', 6, 6, 'YVR', 'JFK');
--
INSERT INTO trip_crews (id_employee, id_connection) VALUES 
    ('EMP001', 1),
    ('EMP002', 2),
    ('EMP003', 3),
    ('EMP004', 4),
    ('EMP005', 5),
    ('EMP006', 6);
--