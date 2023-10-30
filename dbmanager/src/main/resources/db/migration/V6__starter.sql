ALTER TABLE employee ALTER COLUMN personal_id SET NOT NULL;
select setval('my_seq',(SELECT max(id) from employee));


