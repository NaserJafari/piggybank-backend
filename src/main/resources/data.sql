INSERT INTO account (id, `name`, balance, userId) VALUES (1, 'Rekening van Melvin', 1168.64, 1);
INSERT INTO account (id, `name`, balance, userId) VALUES (2, 'Rekening van Sara', 968.65, 2);
INSERT INTO account (id, `name`, balance, userId) VALUES (3, 'Rekening van Cem', 1000.00, 3);
INSERT INTO account (id, `name`, balance, userId) VALUES (4, 'Rekening van Sophie', 862.71, 4);

INSERT INTO transaction  (AMOUNT,  DATE_TIME, DESCRIPTION, RECEIVER_ACCOUNT_ID, SENDER_ACCOUNT_ID) VALUES ( 15.00, '2022-10-05T13:15:00.00Z', 'Lunch voor je verjaardag', 1, 2);
INSERT INTO transaction  (AMOUNT,  DATE_TIME, DESCRIPTION, RECEIVER_ACCOUNT_ID, SENDER_ACCOUNT_ID) VALUES (31.35, '2022-10-05T13:30:00.00Z', 'Taxi ritje Nijmegen voorschot', 1, 2);
INSERT INTO transaction  (AMOUNT,  DATE_TIME, DESCRIPTION, RECEIVER_ACCOUNT_ID, SENDER_ACCOUNT_ID) VALUES (137.29, '2022-10-07T13:30:00.00Z', 'Hotel overnachting + ontbijt', 1, 4);
INSERT INTO transaction  (AMOUNT,  DATE_TIME, DESCRIPTION, RECEIVER_ACCOUNT_ID, SENDER_ACCOUNT_ID) VALUES (15.00, '2022-10-08T13:30:00.00Z', 'Te weinig lunchgeld betaald', 2, 1);