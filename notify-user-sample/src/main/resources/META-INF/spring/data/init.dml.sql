insert into innvz_role (id, name, label) values (2, 'ROLE_USER', 'Regular User');
insert into innvz_role (id, name, label) values (1, 'ROLE_ADMIN', 'Administrator');

insert into innvz_user 
(username, full_name, email_address, password, enabled, account_expired, account_locked, credentials_expired) 
values 
('administrator', 'Super Administrator', 'zakyalvan@gmail.com', '$2a$10$Nba6j4VXsBk0hKwPxYpc0OtkWq1TmINDjjekzlq79YjpUSXeyuJNu', true, false, false, false);

insert into user_role (username, role_id) values ('administrator', 1);