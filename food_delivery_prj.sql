create database food_delivery;
create table restaurant (
res_id int auto_increment,
res_name varchar(50),
res_location varchar(50),
primary key (res_id)
);

insert into restaurant value
(1, 'res 1', 'cusat'),
(2, 'res 2', 'Angamally'),
(3, 'res 3', 'Thrissur');

insert into menu values
(1, 'fried-rice', 1),
(2, 'plain-rice', 1),
(3, 'chicken-rice', 2),
(4, 'food-1', 3);

create table menu(
menu_id int auto_increment,
food_item varchar(80),
res_id int,
primary key (menu_id),
foreign key (res_id) references restaurant(res_id)
);


insert into customer values
(1, "Thomas kutty", "Thomaskutty123@gmail.com"),
(2, "Alan", "Alan123@gmail.com"),
(3, "Akhila", "Akhila123@gmail.com"),
(4, "Chandni", "chandni123@gmail.com"),
(5, "Dona", "dona123@gmail.com");

create table customer(
customer_id int auto_increment,
customer_name varchar(60),
customer_email varchar(30),
primary key (customer_id)
);

insert into orders values 
(3, 5,3, 4);

create table orders(
order_id int auto_increment,
customer_id int,
res_id int,
menu_id int,
primary key (order_id), 
foreign key (customer_id) references customer(customer_id),
foreign key (res_id) references restaurant(res_id),
foreign key (menu_id) references menu(menu_id)
)

