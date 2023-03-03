use contestdb;

-- update status for a contest --
drop procedure if exists contest_start_status;
drop procedure if exists contest_close_status;

DELIMITER $$
CREATE DEFINER=`john`@`%` PROCEDURE `contest_start_status`()
BEGIN
	update contest set status = 'opened'
    where status = 'created' and (UNIX_TIMESTAMP(begin_time) <= unix_timestamp(now()));
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`john`@`%` PROCEDURE `contest_close_status`()
BEGIN
	update contest set status = 'closed'
    where status = 'opened' and (UNIX_TIMESTAMP(end_time) <= unix_timestamp(now()));
END$$
DELIMITER ;

-- DELIMITER $$
-- CREATE DEFINER=`john`@`%` PROCEDURE `contest_past_status`()
-- BEGIN
-- 	update contest set status = 'past'
--     where status = 'closed' and (UNIX_TIMESTAMP(end_time) < unix_timestamp(now()));
-- END$$
-- DELIMITER ;

-- set scheduled task on --
set global event_scheduler = on;

drop event if exists check_contest_status;
delimiter |
create DEFINER=`john`@'%' EVENT if not exists`check_contest_status`
on schedule
every '10' second starts '2023-03-02 08:00:00'
comment 'check if contest stauts needs to be updated'
do
	begin
		CALL `contestdb`.`contest_start_status`();
		CALL `contestdb`.`contest_close_status`();
	end |
    
delimiter ; 

show variables like 'event%';






