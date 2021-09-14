--==========================================
-- 관리자 계정
--==========================================
-- student 계정 생성

alter session set "_oracle_script" = true;

create user student
identified by student
default tablespace users;

alter user student quota unlimited on users;

grant connect, resource to student;


--===========================================
-- student 계정
--===========================================
-- member 테이블 생성
-- jdbc 연동시 date타입의 시분초는 사용불가 -> 시분초가 필요한 경우 timestamp 사용할 것
create table member (
	id varchar2(20),
	name varchar2(100) not null,
	gender char(1),
	birthday date,
	email varchar2(500),
	address varchar2(1000),
	reg_date timestamp default systimestamp,

	constraints pk_member_id primary key(id),
	constraints uq_member_email unique(email),
	constraints ck_member_gender check(gender in ('M', 'F'))
);

insert into member 
values ('honggd', '홍길동', 'M', '09-Sep-1990', 'honggd@naver.com', '서울시 강남구 테헤란로 123', default);

insert into member 
values ('gogd', '고길동', 'M', '07-Jul-1970', 'gogd@naver.com', '경기도 구리시 소동 1000', default);

insert into member
values ('sinsa', '신사임당', 'F', '19-Oct-1954', 'sinsa@naver.com', '경상남도 함양시 안의면 1234', default);

insert into member
values ('donghhh', '이동휘', 'M', '12-Feb-1977', 'donghhh@naver.com', '서울시 종로구 풍동 123', default);

select * from member;
commit;
