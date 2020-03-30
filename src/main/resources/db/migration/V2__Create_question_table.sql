create table question
(
	id BIGINT auto_increment primary key,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modify bigint,
	creator bigint,
	reply_count int default 0 comment '回复数',
	view_count int default 0 comment '阅读数',
	like_count int default 0 comment '点赞数',
	tag varchar(256)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

