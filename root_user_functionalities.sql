use contestdb;

-- Big Sponsors --
select sponsor_id
from contest group by sponsor_id
having count(sponsor_id)=(
	select max(count_num)
	from (
		select sponsor_id, count(sponsor_id) count_num
		from contest
		group by sponsor_id) groupby_sponsor 
);