INSERT INTO `appointment` VALUES (1,2,'2021-10-03 10:00:00.000000','2021-10-03 08:00:00.000000'),(2,2,'2021-10-03 12:30:00.000000','2021-10-03 10:30:00.000000'),(3,2,'2021-10-03 16:00:00.000000','2021-10-03 14:00:00.000000'),(4,2,'2021-10-03 12:30:00.000000','2021-10-03 10:30:00.000000'),(5,2,'2021-10-03 17:30:00.000000','2021-10-03 15:30:00.000000'),(6,2,'2021-10-03 20:00:00.000000','2021-10-03 18:00:00.000000'),(7,4,'2021-10-03 12:30:00.000000','2021-10-03 08:30:00.000000'),(8,2,'2021-10-03 17:30:00.000000','2021-10-03 15:30:00.000000');
INSERT INTO `vehicle` VALUES (1),(2),(3),(4),(5);
INSERT INTO `professional` VALUES (1,NULL,1),(2,NULL,1),(3,NULL,1),(4,NULL,1),(5,NULL,1),(6,NULL,2),(7,NULL,2),(8,NULL,2),(9,NULL,2),(10,NULL,2),(11,NULL,3),(12,NULL,3),(13,NULL,3),(14,NULL,3),(15,NULL,3),(16,NULL,4),(17,NULL,4),(18,NULL,4),(19,NULL,4),(20,NULL,4),(21,NULL,5),(22,NULL,5),(23,NULL,5),(24,NULL,5),(25,NULL,5);
INSERT INTO `appointment_professionallist` VALUES (1,1),(2,1),(3,1),(4,2),(5,2),(6,2),(7,3),(8,3);
INSERT INTO `professional_appointmentlist` VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(2,6),(3,7),(3,8);
INSERT INTO `vehicle_professionallist` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(2,6),(2,7),(2,8),(2,9),(2,10),(3,11),(3,12),(3,13),(3,14),(3,15),(4,16),(4,17),(4,18),(4,19),(4,20),(5,21),(5,22),(5,23),(5,24),(5,25);
