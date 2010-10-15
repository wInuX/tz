create table battle (
  id                        bigint not null,
  battle_date               timestamp not null,
  loc_x                     integer not null,
  loc_y                     integer not null,
  content                   bytea not null,
  constraint pk_battle primary key (id))
;

create index battle_battleddate_index on battle(battle_date);

