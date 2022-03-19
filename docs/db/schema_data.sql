DROP TABLE IF EXISTS ebook;
CREATE TABLE ebook (
    id BIGINT NOT NULL COMMENT 'id',
    NAME VARCHAR ( 64 ) COMMENT '名称',
    category_id BIGINT COMMENT '分类',
    description VARCHAR ( 255 ) COMMENT '描述',
    cover VARCHAR ( 255 ) COMMENT '封面',
    doc_count INT COMMENT '文档数',
    view_count INT COMMENT '阅读数',
    vote_count INT COMMENT '点赞数',
    PRIMARY KEY ( id )
) DEFAULT charset = utf8mb4 COMMENT = 'ebook';
DROP TABLE IF EXISTS ebook_category;

CREATE TABLE ebook_category (
    id BIGINT NOT NULL COMMENT 'id',
    NAME VARCHAR ( 255 ) COMMENT '分类名',
    category_id BIGINT COMMENT '父类',
    PRIMARY KEY ( id )
) DEFAULT charset = utf8mb4 COMMENT = 'ebook_category';

INSERT INTO `ebook` ( id, NAME, description )
VALUES
    ( 1, 'Spring Boot入门教程', '零基础入门Java开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, NAME, description )
VALUES
    ( 2, 'Vue 入门教程', '零基础入门Vue开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, NAME, description )
VALUES
    ( 3, 'Python 入门教程', '零基础入门Python开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, NAME, description )
VALUES
    ( 4, 'MySQL 入门教程', '零基础入门MySQL开发，企业级应用开发最佳首选框架' );
INSERT INTO `ebook` ( id, NAME, description )
VALUES
    ( 5, 'Oracle 入门教程', '零基础入门Oracle开发，企业级应用开发最佳首选框架' );