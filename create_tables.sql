use testdb;

CREATE TABLE sponsor (
  sponsor_id varchar(42),
  company_name varchar(100) DEFAULT NULL,
  email varchar(30) DEFAULT NULL,
  address varchar(100),
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
  sponsor_fee int,
  PRIMARY KEY (contest_id),
  FOREIGN KEY (sponsor_id) REFERENCES sponsor (sponsor_id),
  CHECK ((status in ('created', 'opened', 'closed', 'past')))
);

CREATE TABLE contestant (
  contestant_id varchar(42),
  PRIMARY KEY (contestant_id)
);

CREATE TABLE participate (
  contestant_id varchar(42),
  contest_id varchar(42),
  contestant_reward float,
  PRIMARY KEY (contestant_id,contest_id),
  FOREIGN KEY (contestant_id) REFERENCES contestant (contestant_id),
  FOREIGN KEY (contest_id) REFERENCES contest (contest_id)
);

CREATE TABLE judge (
  judge_id varchar(42) NOT NULL,
  PRIMARY KEY (judge_id)
);

create TABLE grade(
	contest_id varchar(42),
    contestant_id varchar(42),
    judge_id varchar(42),
    score integer,
    check (score >= 0 & score <= 100),
	primary key (contest_id, contestant_id, judge_id),
    foreign key (contest_id) references contest (contest_id),
    foreign key (contestant_id) references contestant (contestant_id),
    foreign key (judge_id) references judge (judge_id)
);

CREATE TABLE judgeby (
  contest_id varchar(42),
  judge_id varchar(42),
  judge_reward float,
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