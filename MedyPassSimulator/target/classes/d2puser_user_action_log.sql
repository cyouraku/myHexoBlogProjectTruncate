--select * from user_action_log where action_date >= '2017-08-22 00:00:00' and screen_id is null or screen_id = '' limit 10;

--select * from user_action_log where user_id = '100000163' and screen_id is null or screen_id = '' limit 10;

--select distinct target_url, action_id, action_detail_id from user_action_log  where  user_id = '100000163' and screen_id is not null or screen_id = '';

select * from screen_master where screen_id = 'SC-USR-301-004'