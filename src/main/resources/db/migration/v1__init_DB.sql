drop table if exists bucket_products;
drop table if exists bucket_seq;
drop table if exists buckets;
drop table if exists categories;
drop table if exists category_seq;
drop table if exists order_details;
drop table if exists order_details_seq;
drop table if exists order_seq;
drop table if exists orders;
drop table if exists orders_details;
drop table if exists product_seq;
drop table if exists products;
drop table if exists products_categories;
drop table if exists user_seq;
drop table if exists users;

create table bucket_products (bucket_id bigint not null,
                              product_id bigint not null);

create table bucket_seq (next_val bigint);

insert into bucket_seq values ( 1 );

create table buckets (id bigint not null,
                      user_id bigint,
                      primary key (id));

create table categories (id bigint not null,
                         name varchar(255),
                         primary key (id));

create table category_seq (next_val bigint);
insert into category_seq values ( 1 );

create table brands (id bigint not null,
                     name varchar(255),
                     primary key (id));

create table brand_seq (next_val bigint);
insert into brand_seq values ( 1 );

create table order_details (id bigint not null,
                            amount decimal(38,2),
                            price decimal(38,2),
                            order_id bigint,
                            product_id bigint,
                            primary key (id));



create table order_details_seq (next_val bigint);
insert into order_details_seq values ( 1 );

create table addresses (id bigint not null,
                            amount decimal(38,2),
                            price decimal(38,2),
                            order_id bigint,
                            product_id bigint,
                            primary key (id));



create table order_details_seq (next_val bigint);
insert into order_details_seq values ( 1 );


create table order_seq (next_val bigint);
insert into order_seq values ( 1 );

create table orders (id bigint not null,
                     address varchar(255),
                     created date,
                     status varchar(255),
                     sum decimal(38,2),
                     updated date,
                     user_id bigint,
                     primary key (id));

create table orders_details (order_id bigint not null,
                             details_id bigint not null);

create table product_seq (next_val bigint);
insert into product_seq values ( 1 );

create table products (id bigint not null,
                       name varchar(255),
                       price decimal(38,2),
                       primary key (id));

create table products_categories (product_id bigint not null,
                                  category_id bigint not null);

create table brand_products (product_id bigint not null,
                                  brand_id bigint not null);

create table user_seq (next_val bigint);
insert into user_seq values ( 1 );

create table users (id bigint not null,
                    archive boolean not null,
                    email varchar(255),
                    name varchar(255),
                    password varchar(255),
                    role varchar(255),
                    bucket_id bigint,
                    primary key (id));

create table phone_details_all (id bigint not null,
                                name varchar(255));

alter table orders_details add constraint UK_kk6y3pyhjt6kajomtjbhsoajo unique (details_id);
alter table bucket_products add constraint bucket_fk_products foreign key (product_id) references products (id);
alter table bucket_products add constraint bucket_fk_buckets foreign key (bucket_id) references buckets (id);
alter table buckets add constraint buckets_fk_users foreign key (user_id) references users (id);
alter table order_details add constraint order_details_fk_orders foreign key (order_id) references orders (id);
alter table order_details add constraint order_details_fk_products foreign key (product_id) references products (id);
alter table orders add constraint orders_fk_users foreign key (user_id) references users (id);
alter table orders_details add constraint orders_details_fk_orders_details foreign key (details_id) references order_details (id);
alter table orders_details add constraint orders_details_fk_orders foreign key (order_id) references orders (id);
alter table products_categories add constraint products_categories_fk_categories foreign key (category_id) references categories (id);
alter table products_categories add constraint products_categories_fk_products foreign key (product_id) references products (id);
alter table users add constraint users_fk_buckets foreign key (bucket_id) references buckets (id);