# --- !Ups

create table account (
  id                        bigint not null,
  created                   timestamp,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (id))
;

create table account_role (
  id                        bigint not null,
  created                   timestamp,
  name                      varchar(255),
  constraint pk_account_role primary key (id))
;

create table book (
  id                        bigint not null,
  created                   timestamp,
  title                     varchar(255),
  description               text,
  price                     decimal(38,2),
  image_id                  bigint,
  constraint pk_book primary key (id))
;

create table orders (
  id                        bigint not null,
  created                   timestamp,
  cardnumber                bigint,
  owner                     varchar(255),
  validity                  timestamp,
  checkdigit                bigint,
  total                     decimal(38,2),
  book_id                   bigint,
  buyer_id                  bigint,
  constraint pk_orders primary key (id))
;

create table review (
  id                        bigint not null,
  created                   timestamp,
  author_id                 bigint,
  book_id                   bigint,
  text                      text,
  constraint pk_review primary key (id))
;

create table s3image (
  id                        bigint not null,
  created                   timestamp,
  key                       varchar(255),
  bucket                    varchar(255),
  constraint pk_s3image primary key (id))
;


create table account_account_role (
  account_id                     bigint not null,
  account_role_id                bigint not null,
  constraint pk_account_account_role primary key (account_id, account_role_id))
;
create sequence account_seq start with 1000;

create sequence account_role_seq start with 1000;

create sequence book_seq start with 1000;

create sequence orders_seq start with 1000;

create sequence review_seq start with 1000;

create sequence s3image_seq start with 1000;

alter table book add constraint fk_book_image_1 foreign key (image_id) references s3image (id) on delete restrict on update restrict;
create index ix_book_image_1 on book (image_id);
alter table orders add constraint fk_orders_book_2 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_orders_book_2 on orders (book_id);
alter table orders add constraint fk_orders_buyer_3 foreign key (buyer_id) references account (id) on delete restrict on update restrict;
create index ix_orders_buyer_3 on orders (buyer_id);
alter table review add constraint fk_review_author_4 foreign key (author_id) references account (id) on delete restrict on update restrict;
create index ix_review_author_4 on review (author_id);
alter table review add constraint fk_review_book_5 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_review_book_5 on review (book_id);



alter table account_account_role add constraint fk_account_account_role_accou_01 foreign key (account_id) references account (id) on delete restrict on update restrict;

alter table account_account_role add constraint fk_account_account_role_accou_02 foreign key (account_role_id) references account_role (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists account_account_role;

drop table if exists account_role;

drop table if exists book;

drop table if exists orders;

drop table if exists review;

drop table if exists s3image;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists account_role_seq;

drop sequence if exists book_seq;

drop sequence if exists orders_seq;

drop sequence if exists review_seq;

drop sequence if exists s3image_seq;

