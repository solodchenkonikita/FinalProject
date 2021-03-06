
-- Insert timeslot table

INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('10:00:00', '10:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('10:30:00', '11:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('11:00:00', '11:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('11:30:00', '12:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('12:00:00', '12:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('12:30:00', '13:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('13:00:00', '13:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('13:30:00', '14:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('14:00:00', '14:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('14:30:00', '15:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('15:00:00', '15:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('15:30:00', '16:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('16:00:00', '16:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('16:30:00', '17:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('17:00:00', '17:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('17:30:00', '18:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('18:00:00', '18:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('18:30:00', '19:00:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('19:00:00', '19:30:00');
INSERT INTO `beauty_salon`.`timeslot` (`start_time`, `end_time`) VALUES ('19:30:00', '20:00:00');


-- Insert calendar table

INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-05');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-06');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-07');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-08');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-09');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-10');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-11');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-12');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-13');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-14');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-15');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-16');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-17');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-18');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-19');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-20');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-21');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-22');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-23');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-24');
INSERT INTO `beauty_salon`.`calendar` (`date`) VALUES ('2020-10-25');


-- Insert service table

INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Haircut', '100');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Manicure', '120');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Pedicure', '130');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Extension of nails', '200');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Removal of extended nails', '300');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Makeup', '350');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Eyebrow correction', '100');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Eyebrow tinting', '200');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Face cleaning', '700');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Peeling', '700');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Hair straightening', '500');
INSERT INTO `beauty_salon`.`service` (`service_name`, `price`) VALUES ('Massage', '550');


-- Insert role table

INSERT INTO `beauty_salon`.`role` (`rolename`) VALUES ('administrator');
INSERT INTO `beauty_salon`.`role` (`rolename`) VALUES ('master');
INSERT INTO `beauty_salon`.`role` (`rolename`) VALUES ('client');


-- Insert user table

INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Костя', 'Горький', 'admin@gmail.com', 'admin', '1');
INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Влад', 'Парфенюк', 'admin2@gmail.com', 'admin2', '1');
INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Лиза', 'Лупова', 'master@gmail.com', 'master', '2');
INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Катя', 'Матвиенко', 'master2@gmail.com', 'master2', '2');
INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Лера', 'Яковлева', 'master3@gmail.com', 'master3', '2');
INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Вика', 'Стельникович', 'client@gmail.com', 'client', '3');
INSERT INTO `beauty_salon`.`user` (`first_name`, `last_name`, `email`, `password`, `role_id`) VALUES ('Вера', 'Литвиненко', 'client2@gmail.com', 'client2', '3');


-- Insert booking table

INSERT INTO `beauty_salon`.`booking` (`service_id`, `client_id`) VALUES ('3', '11');
INSERT INTO `beauty_salon`.`booking` (`service_id`, `client_id`) VALUES ('4', '12');


-- Insert timetable table

INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '2', '3');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '2', '4');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '4', '5');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '10', '5');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '11', '3');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '11', '4');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '11', '5');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '12', '3');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '12', '4');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '12', '5');
INSERT INTO `beauty_salon`.`timetable` (`calendar_id`, `timeslot_id`, `master_id`) VALUES ('6', '13', '3');

