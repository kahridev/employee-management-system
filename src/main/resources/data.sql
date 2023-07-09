DELETE FROM EMPLOYEE;
DELETE FROM DEPARTMENT;

INSERT INTO DEPARTMENT (department_name, create_date) VALUES
  ('Human Resources', CURRENT_TIMESTAMP),
  ('Finance', CURRENT_TIMESTAMP),
  ('Marketing', CURRENT_TIMESTAMP),
  ('Purchase', CURRENT_TIMESTAMP),
  ('Sales', CURRENT_TIMESTAMP),
  ('Operations', CURRENT_TIMESTAMP);

INSERT INTO EMPLOYEE (name, surname, position, salary, department_id, create_date) VALUES
  ('Danyal', 'Blevins', 'Manager', 10000, 1, CURRENT_TIMESTAMP),
  ('Ajay', 'Daniels', 'Recruiter', 8000, 1, CURRENT_TIMESTAMP),
  ('Kyra', 'Bishop', 'Officer', 5000, 3, CURRENT_TIMESTAMP),
  ('Andrea', 'Wyatt', 'Team Leader', 5000, 2, CURRENT_TIMESTAMP),
  ('Cormac', 'Wilson', 'Officer', 4500, 4, CURRENT_TIMESTAMP),
  ('Wyatt', 'Keith', 'Officer', 6500, 4, CURRENT_TIMESTAMP),
  ('Abbey', 'Benson', 'Officer', 6000, 5, CURRENT_TIMESTAMP),
  ('Larry', 'Oneill', 'Director', 12000, 2, CURRENT_TIMESTAMP),
  ('Simon', 'Eaton', 'Accounter', 4000, 2, CURRENT_TIMESTAMP),
  ('Eden', 'Mclean', 'Officer', 3000, 5, CURRENT_TIMESTAMP),
  ('Rahul', 'Owens', 'Manager', 9000, 6, CURRENT_TIMESTAMP),
  ('Azaan', 'Maddox', 'Officer', 6000, 6, CURRENT_TIMESTAMP);