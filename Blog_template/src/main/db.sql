
-- 这个数据库有两个作用
-- 1、创建博客系统的库
-- 2、创建用户表
-- 3、创建博客列表

create database if not exists BlogSystem;
use BlogSystem;

-- 创建表
drop table if exists blog;
create table blog (
    blogId int primary key auto_increment,
    title varchar(1024),
    content mediumtext,
    postTime datetime,
    userId int
);

-- 创建用户表
drop table if exists user;
create table user(
    userId int primary key auto_increment,
    -- 名字也是唯一的，unique
    username varchar(128) unique,
    password varchar(128)

);