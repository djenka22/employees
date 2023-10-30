CREATE SEQUENCE my_seq INCREMENT BY 1;

CREATE table TEAM
(
    id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    primary key (id)
);

CREATE table EMPLOYEE
(
    id BIGINT NOT NULL,
    name VARCHAR(20) NOT NULL,
    team BIGINT NOT NULL,
    team_lead BIGINT NOT NULL,
    primary key (id),
    CONSTRAINT fk_team_lead foreign key (team_lead) references EMPLOYEE(id),
    CONSTRAINT fk_team foreign key (team) references TEAM(id)
);
