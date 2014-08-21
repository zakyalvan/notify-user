insert into innvz_role (id, name) values (1, 'ROLE_USER');
insert into innvz_role (id, name) values (2, 'ROLE_ADMIN');

insert into innvz_user 
(username, full_name, email_address, password, enabled, account_expired, account_locked, credentials_expired) 
values 
('zakyalvan', 'Muhammad Zaky Alvan', 'zakyalvan@gmail.com', '$2a$10$Nba6j4VXsBk0hKwPxYpc0OtkWq1TmINDjjekzlq79YjpUSXeyuJNu', true, false, false, false);

insert into innvz_user 
(username, full_name, email_address, password, enabled, account_expired, account_locked, credentials_expired) 
values 
('administrator', 'Super Administrator', 'zaky@innovez-one.com', '$2a$10$Nba6j4VXsBk0hKwPxYpc0OtkWq1TmINDjjekzlq79YjpUSXeyuJNu', true, false, false, false);

insert into user_role (username, role_id) values ('zakyalvan', 1);
insert into user_role (username, role_id) values ('administrator', 2);