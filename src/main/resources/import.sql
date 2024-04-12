INSERT INTO users (dni, name, lastname, email, phone_number, role, password) VALUES
('12345678A', 'John', 'Doe', 'john@example.com', '123456789', 0, 'adminpass'),
('11111111A', 'Michael', 'Johnson', 'michael@example.com', '111111111', 0, 'adminpass'),
('98765432B', 'Jane', 'Smith', 'jane@example.com', '987654321', 1, 'userpass'),
('56789012C', 'Alice', 'Johnson', 'alice@example.com', '567890123', 1, 'clientpass'),
('22222222B', 'Emily', 'Brown', 'emily@example.com', '222222222', 1, 'userpass'),
('33333333C', 'Daniel', 'Martinez', 'daniel@example.com', '333333333', 2, 'clientpass'),
('44444444D', 'Sophia', 'Wilson', 'sophia@example.com', '444444444', 2, 'adminpass'),
('55555555E', 'James', 'Taylor', 'james@example.com', '555555555', 2, 'userpass'),
('66666666F', 'Olivia', 'Anderson', 'olivia@example.com', '666666666', 2, 'clientpass');

INSERT INTO zones (name) VALUES
('Center'),
('North'),
('South'),
('West'),
('East');

INSERT INTO offices (address, id_zone) VALUES 
('16171 Shanahan Orchard Apt. 771', 1),
('47772 Johnston Stravenue Suite 747', 2),
('3493 Durgan Village', 3);

INSERT INTO properties (address, area, price, type, keys_available, state, id_owner, id_office, id_zone) VALUES
('123 Main St', 120.5, 250000.00, 1, true, 0, 1, 1, 2),
('456 Elm St', 200.0, 400000.00, 0, false, 1, 2, 2, 3);

INSERT INTO chars (id_property, name, available) VALUES
(1, "Parking", true),
(1, "Gas", true),
(1, "Cameras", true),
(2, "Parking", false),
(2, "Gas", true);

INSERT INTO stays (id_property, name, amount) VALUES
(1, "Rooms", 4),
(1, "Kitchen", 1),
(1, "Bathrooms", 2),
(1, "Living Room", 1),
(1, "Dining Room", 1),
(2, "Rooms", 3),
(2, "Kitchen", 1),
(2, "Bathrooms", 2),
(2, "Living Room", 1),
(2, "Dining Room", 1);

INSERT INTO visits (id_visitor, id_property, visit_at, comment) VALUES
(6, 1, '2024-04-11 10:00:00', 'I loved the layout of the house.'),
(6, 1, '2024-04-12 11:00:00', 'The house has a spacious and bright kitchen.'),
(7, 2, '2024-04-13 09:30:00', 'The property has an impressive view from the balcony.'),
(7, 2, '2024-04-14 15:45:00', 'The bedrooms are spacious and well-lit.'),
(6, 2, '2024-04-15 14:00:00', 'The garden of this house is perfect for kids.'),
(7, 1, '2024-04-16 16:30:00', 'The living area is very cozy.'),
(8, 1, '2024-04-17 13:15:00', 'This house has an amazing swimming pool!'),
(9, 1, '2024-04-18 12:30:00', 'I really liked the layout of the garden in this property.');



