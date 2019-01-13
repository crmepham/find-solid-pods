create table provider (
	id bigint(10) unsigned not null auto_increment,
	uri varchar(500) not null,
	title varchar(20) not null,
	description varchar(140) not null,
	icon_uri varchar(500) not null,
	type varchar(4) not null,
	country_code varchar(2) not null,
	title_color varchar(7),
	button_color varchar(7),
	button_background_color varchar(7),
	policy_uri varchar(500),
	icon_background_color varchar(7),
	created datetime not null,
	active bit default false,
	indexed bit default false,
	primary key (id),
	unique(uri, type)
) engine=InnoDB default charset=utf8;

CREATE INDEX provider_uri(25) ON `provider` (uri);
CREATE INDEX provider_type ON `provider` (type);