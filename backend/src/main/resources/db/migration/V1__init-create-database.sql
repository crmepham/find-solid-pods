create table provider (
	id bigint(10) unsigned not null auto_increment,
	uri text not null,
	title varchar(20) not null,
	description varchar(140) not null,
	icon_uri text not null,
	type varchar(4) not null,
	country_code varchar(2) not null,
	title_color varchar(7),
	button_color varchar(7),
	button_background_color varchar(7),
	policy_uri text,
	icon_background_color varchar(7),
	created datetime not null,
	active bit default false,
	indexed bit default false,
	primary key (id),
	unique(uri(30), type)
) engine=InnoDB default charset=utf8;

CREATE INDEX provider_uri ON `provider` (uri(30));
CREATE INDEX provider_type ON `provider` (type);