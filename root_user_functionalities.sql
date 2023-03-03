use contestdb;

-- Big Sponsors --
select * from sponsor where sponsor_id in (
select sponsor_id as selected_sponsor
from contest group by sponsor_id
having count(sponsor_id)=(
	select max(count_num)
	from (
		select sponsor_id, count(sponsor_id) count_num
		from contest
		group by sponsor_id) groupby_sponsor 
) ) ;