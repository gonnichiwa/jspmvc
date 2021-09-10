create table Board(
                      id int not null primary key,    			  -- 글 id
                      author VARCHAR(200) not null,   			  -- 작성자 이름
                      subject VARCHAR(300) not null,  			  -- 글 제목
                      content VARCHAR(2000) not null, 			  -- 글 컨텐츠
                      writeDate Date not null,        			  -- 작성 날짜
                      writeTime Time not null,        			  -- 작성 시간
                      readCount int not null,         			  -- 조회수
                      commentCount int not null,      			  -- 댓글 갯수
                      password VARCHAR(300) not null default '0000' -- 글 수정삭제 비밀번호
)

-- 게시글
CREATE TABLE Board (
                       id           INT           primary key COMMENT '게시글id', -- 게시글id
                       author       VARCHAR(200)  NOT NULL COMMENT '작성자', -- 작성자
                       subject      VARCHAR(300)  NOT NULL COMMENT '제목', -- 제목
                       content      VARCHAR(2000) NOT NULL COMMENT '내용', -- 내용
                       writeDate    DATE          NOT NULL COMMENT '작성일', -- 작성일
                       writeTime    TIME          NOT NULL COMMENT '작성시각', -- 작성시각
                       readCount    INT           NOT NULL COMMENT '조회수', -- 조회수
                       commentCount INT           NOT NULL COMMENT '댓글수', -- 댓글수
                       password     VARCHAR(300)  NOT NULL default '0000' COMMENT '수정삭제비밀번호' -- 수정삭제비밀번호
                           replyRootId  INT           not null default 0,      -- 답글이 달리는 root원글
                       depth        int           not null default 0,      -- 답글 깊이
                       orderNum     int           not null default 0       -- root원글, 답글깊이에 따른 답글 순서
)
    COMMENT '게시글';

-- 댓글
CREATE TABLE Comment (
                         cid       INT           primary key COMMENT '댓글id', -- 댓글id
                         id        INT           NOT NULL COMMENT '게시글id', -- 게시글id
                         author    VARCHAR(200)  NOT NULL COMMENT '작성자', -- 작성자
                         content   VARCHAR(2000) NOT NULL COMMENT '댓글내용', -- 댓글내용
                         writeDate DATE          NOT NULL COMMENT '작성일', -- 작성일
                         writeTime TIME          NOT NULL COMMENT '작성시각' -- 작성시각
)
    COMMENT '댓글';

-- 댓글
ALTER TABLE Comment
    ADD CONSTRAINT FK_Board_TO_Comment -- 게시글 -> 댓글
        FOREIGN KEY (
                     id -- 게시글id
            )
            REFERENCES Board ( -- 게시글
                              id -- 게시글id
                );

create table Member(
                       mId int not null primary key,
                       id varchar(300) not null,
                       password varchar(1024) not null,
                       salt varchar(36) not null
)