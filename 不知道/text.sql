create database student_management;
use student_management;
create table student(
`name` varchar(10) NOT null,
`Total`varchar(10) NOT null,
`grade`varchar(10) NOT null,
`id`varchar(20) NOT null PRIMARY key,
`graduation_statues`varchar(20)Not null,
`gender`ENUM('男','女') DEFAULT NULL,
`class`varchar(20) not null 
);