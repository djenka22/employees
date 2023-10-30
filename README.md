# HOME PROJECT - HYPEROPTIC

This application is responsible for managing data about employees. It has three submodules:
- dbmanager for database migrations on PostgreSQL database
- model which mapps Entities to database tables
- manager with REST API for database manipulation


There are two tables in database:
- Employee with columns 
  - id (BIGINT)
  - name (VARCHAR)
  - personal_id (NUMERIC) (UNIQUE)
  - team_id(fk_team_id) (BIGINT)
  - team_lead_id(fk_employee_id) (BIGINT)


- Team with columns
  - id (BIGINT)
  - name (VARCHAR)

# OpenAPI documentation
To see how to use manager API, service has to be up and running 
on localhost 

**IN ORDER TO RUN APPLICATION:**
- preinstaled Maven, Java 8, PostgreSQL
- run **dbmanager** first and then **manager** service
 
PostgreSQL database can be downloaded from Docker registry with command 

- docker run --env=POSTGRES_USER=postgres --env=POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres



Finally OpenAPI documentation can be seen on path  http://localhost:9073/swagger-ui/index.html?apiDocs=/api-docss


