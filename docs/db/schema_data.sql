DROP TABLE IF EXISTS ebook;
CREATE TABLE ebook (
    id BIGINT NOT NULL COMMENT 'id',
    name VARCHAR ( 64 ) COMMENT '名称',
    category_id BIGINT COMMENT '分类',
    description VARCHAR ( 255 ) COMMENT '描述',
    cover VARCHAR ( 255 ) COMMENT '封面',
    doc_count INT COMMENT '文档数',
    view_count INT COMMENT '阅读数',
    vote_count INT COMMENT '点赞数',
    PRIMARY KEY ( id )
) DEFAULT charset = utf8mb4 COMMENT = 'ebook';
DROP TABLE IF EXISTS ebook_category;

# 分类
drop table if exists `ebook_category`;
create table `ebook_category` (
    `id` bigint not null comment 'id',
    `parent` bigint not null default 0 comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类';
insert into `ebook_category`(id, parent, name, sort) values (100, 000, '前端开发', 100);
insert into `ebook_category`(id, parent, name, sort) values (101, 100, 'Vue', 101);
insert into `ebook_category`(id, parent, name, sort) values (102, 100, 'HTML && CSS', 102);
insert into `ebook_category`(id, parent, name, sort) values (200, 000, 'Java', 200);
insert into `ebook_category`(id, parent, name, sort) values (201, 200, '基础应用', 201);
insert into `ebook_category`(id, parent, name, sort) values (202, 200, '框架应用', 202);
insert into `ebook_category`(id, parent, name, sort) values (300, 000, 'Python', 300);
insert into `ebook_category`(id, parent, name, sort) values (301, 300, '基础应用', 301);
insert into `ebook_category`(id, parent, name, sort) values (302, 300, '进阶方向应用', 302);
insert into `ebook_category`(id, parent, name, sort) values (400, 000, '数据库', 400);
insert into `ebook_category`(id, parent, name, sort) values (401, 400, 'MYSQL', 401);
insert into `ebook_category`(id, parent, name, sort) values (500, 000, '其他', 500);
insert into `ebook_category`(id, parent, name, sort) values (501, 500, '服务器', 501);
insert into `ebook_category`(id, parent, name, sort) values (502, 500, '开发工具', 502);
insert into `ebook_category`(id, parent, name, sort) values (503, 500, '热门服务端语言', 503);

CREATE TABLE ebook_category (
    id BIGINT NOT NULL COMMENT 'id',
    name VARCHAR ( 255 ) COMMENT '分类名',
    category_id BIGINT COMMENT '父类',
    PRIMARY KEY ( id )
) DEFAULT charset = utf8mb4 COMMENT = 'ebook_category';

INSERT INTO `ebook` ( id, name, description )
VALUES
    ( 1, 'Spring Boot入门教程', '零基础入门Java开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, name, description )
VALUES
    ( 2, 'Vue 入门教程', '零基础入门Vue开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, name, description )
VALUES
    ( 3, 'Python 入门教程', '零基础入门Python开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, name, description )
VALUES
    ( 4, 'MySQL 入门教程', '零基础入门MySQL开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, name, description )
VALUES
    ( 5, 'Oracle 入门教程', '零基础入门Oracle开发，企业级应用开发最佳首选框架' );

# 0.7.6
ALTER TABLE `ebook` DROP COLUMN `category_id`;
ALTER TABLE `ebook` ADD `category1_id` BIGINT COMMENT '分类1';
ALTER TABLE `ebook` ADD `category2_id` BIGINT COMMENT '分类2';