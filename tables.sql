drop database if exists contestdb;
create database contestdb;
use contestdb;

CREATE TABLE sponsor (
  sponsor_id varchar(42),
  login_id varchar(10),
  unique (login_id),
  company_name varchar(100),
  email varchar(30),
  address varchar(100),
  password varchar(30),
  balance bigint default 1000000,
  PRIMARY KEY (sponsor_id)
);

CREATE TABLE contest (
  contest_id varchar(42),
  sponsor_id varchar(42),
  title varchar(100),
  begin_time datetime,
  end_time datetime,
  status char(10) DEFAULT 'created',
  requirement_list text,
  sponsor_fee bigint default 0,
  PRIMARY KEY (contest_id),
  FOREIGN KEY (sponsor_id) REFERENCES sponsor (sponsor_id),
  CHECK ((status in ('created', 'opened', 'closed', 'past')))
);

CREATE TABLE contestant (
  contestant_id varchar(42),
  login_id varchar(10),
  reward_balance float default 0,
  password varchar(30),
  unique (login_id),
  PRIMARY KEY (contestant_id)
);

CREATE TABLE participate (
  contestant_id varchar(42),
  contest_id varchar(42),
  contestant_reward float default 0,
  PRIMARY KEY (contestant_id,contest_id),
  FOREIGN KEY (contestant_id) REFERENCES contestant (contestant_id),
  FOREIGN KEY (contest_id) REFERENCES contest (contest_id)
);

CREATE TABLE judge (
  judge_id varchar(42) NOT NULL,
  login_id varchar(10),
  unique (login_id),
  reward_balance float default 0,
  password varchar(30),
  avg_score float,
  review_number int default 0,
  PRIMARY KEY (judge_id)
);

create TABLE grade(
	contest_id varchar(42),
    contestant_id varchar(42),
    judge_id varchar(42),
    score integer default 0,
    check (score >= 0 & score <= 100),
	primary key (contest_id, contestant_id, judge_id),
    foreign key (contest_id) references contest (contest_id),
    foreign key (contestant_id) references contestant (contestant_id),
    foreign key (judge_id) references judge (judge_id)
);

CREATE TABLE judgeby (
  contest_id varchar(42),
  judge_id varchar(42),
  judge_reward float default 0,
  PRIMARY KEY (contest_id,judge_id),
  FOREIGN KEY (contest_id) REFERENCES contest (contest_id),
  FOREIGN KEY (judge_id) REFERENCES judge (judge_id)
);

CREATE TABLE review(
	judge_id varchar(42),
    sponsor_id varchar(42),
    review_score integer,
    check (review_score >= 0 & review_score <= 10),
    comment text,
    primary key (judge_id, sponsor_id),
    foreign key (judge_id) references judge (judge_id),
    foreign key (sponsor_id) references sponsor (sponsor_id)
);

create table admin(
	name varchar(20),
    password varchar(30),
    primary key (name)
);

insert into admin values ('root', 'pass1234');

-- create the table used for trigger
-- CREATE TABLE stop_action (
--     id INT NOT NULL AUTO_INCREMENT,
--     name VARCHAR(35),
--     primary key (id),
--     UNIQUE KEY (id, name)
-- );
-- INSERT INTO stop_action values (1, 'Assert Failure');

-- DELIMITER $$
-- CREATE TRIGGER assert_judges_count_after_insert
--     BEFORE INSERT
--     ON judgeby FOR EACH ROW
-- 	BEGIN
--         declare number int;
--         select count(*) into number
-- 		from contest, judgeby
-- 		where contest.contest_id = judgeby.contest_id;
--         
--         if number < 5 or number > 10 then
-- 			INSERT INTO stop_action values (1, 'Assert Failure');
-- 		end if;
-- 	END$$    
-- DELIMITER ;

-- DELIMITER $$
-- CREATE TRIGGER assert_judges_count_after_update
--     BEFORE UPDATE
--     ON judgeby FOR EACH ROW
-- 	BEGIN
--         declare number int;
--         select count(*) into number
-- 		from contest, judgeby
-- 		where contest.contest_id = judgeby.contest_id;
--         
--         if number < 5 or number > 10 then
-- 			INSERT INTO stop_action values (1, 'Assert Failure');
-- 		end if;
-- 	END$$    
-- DELIMITER ;

