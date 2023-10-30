ALTER TABLE employee ADD COLUMN personal_id numeric(6,0) UNIQUE;
UPDATE employee set personal_id=123456 where id=1;
UPDATE employee set personal_id=987654 where id=2;
UPDATE employee set personal_id=654321 where id=3;
UPDATE employee set personal_id=321654 where id=4;

