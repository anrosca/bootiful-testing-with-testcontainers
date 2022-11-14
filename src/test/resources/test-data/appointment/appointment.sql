-- --Clean up
delete from appointments;
delete from patients;
delete from doctors;

insert into doctors (id, email_address, first_name, last_name, specialty, telephone_number)
values ('cafe11c0-7d59-45be-85cc-0dc146532e78',  'sponge-bob@gmail.com', 'Sponge', 'Bob', 'ORTHODONTIST', '37369666667');
INSERT INTO doctors (id, email_address, first_name, last_name, specialty, telephone_number)
VALUES ('a23e4567-a89b-12d3-a456-42661417400a', 'vusaci@gmail.com', 'Vasile', 'Usaci', 'ORTHODONTIST', '37369666666');

insert into patients(id, first_name, last_name, birth_date, phone_number)
values ('b23e4567-b89b-12d3-a456-42661417400', 'Jim', 'Morrison', '1994-12-13', '+37369952147');

insert into patients(id, first_name, last_name, birth_date, phone_number)
values ('ac4ec567-fc9c-C2d3-f45b-c26c141c4fcf', 'Ray', 'Charles', '1994-12-12', '+37369952149');

insert into appointments(id, operation, doctor_id, patient_id, appointment_date, start_time, end_time, details)
values ('aa3e4567-e89b-12d3-b457-5267141750aa', 'Выдача каппы', 'a23e4567-a89b-12d3-a456-42661417400a',
        'b23e4567-b89b-12d3-a456-42661417400', '2021-12-12', '17:00', '18:00', 'New patient');
