create table follow
(
	id BIGINT auto_increment primary key,
	user_id bigint not null,
	follow_type int not null,
	follow_id bigint not null,
	gmt_create bigint
);

