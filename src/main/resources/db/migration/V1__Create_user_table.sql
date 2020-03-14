create table USER
(
	ID BIGINT auto_increment primary key not null,
	ACCOUNT VARCHAR(100),
	EMAIL VARCHAR(100),
	PHONENO VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFY BIGINT,
	THIRDPARTY VARCHAR(50),
	THIRDPARTYID VARCHAR(50),
	BIO VARCHAR(256),
	PASSWORD VARCHAR(50),
	AVARTARURL VARCHAR(50),
	FLOWCOUNT INT DEFAULT 0
);

