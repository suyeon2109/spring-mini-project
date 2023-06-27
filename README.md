# spring-mini-project

[프로젝트 개요]
1.	Spring boot 개발 환경 셋팅
* Java11, Spring Boot 2. 대로 선택
*	Build 툴(gradle / maven) 선택
*	라이브러리 사용은 제한 없음
2.	기능 구현
*	회원 생성 / 조회 기능
*	로그인 (JWT 인증방식), 로그아웃, 토큰 재발행
*	공지사항 생성 / 수정 / 삭제(단건, 복수건) / 리스트 조회 / 상세 조회
*	mybatis, jpa 등 평소 하시던 개발스킬 및 스타일로 작업하시면 됩니다.
3.	API 설계 및 개발 문서정리(엑셀, 노션 등)
 
[요구사항]
0.	In-memory DB(h2 등) 사용하고, 테이블 명세서는 요청해주세요.
1.	사용자 회원 가입
*	사용자 아이디, 패스워드, 이름은 암호화하여 저장한다.
*	암호화 방식은 AES/CBC/PKCS5Padding 을 사용한다.
2.	사용자 로그인 
*	accessToken, refreshToken 를 생성하고, userToken 테이블에 입력한다.
*	accessToken 만료시간은 30분, refreshToken 만료시간은 2시간으로 설정한다.
3.	토큰 재발행
*	accessToken이 만료되었을떄, refreshToken 을 이용하여 토큰을 재발행 한다.
*	refreshToken이 만료되었을떄는 접근이 불가능함을 응답한다.
4.	사용자 리스트 조회
5.	공지사항을 생성한다.
*	제목, 내용은 필수값 입니다.
*	비고는 필수값이 아닙니다.
*	생성시각과 생성한 사용자의 아이디를 기록합니다.
6.	 공지사항을 수정한다.
*	제목, 내용, 비고를 수정합니다.
*	수정시각 및 수정한 사용자의 아이디를 기록합니다.
7.	공지사항을 단건 삭제한다. 
* notice ID 를 이용하여 hard delete 처리
8.	공지사항을 여러건 삭제한다. 
*	notice ID 를 이용하여 hard delete 처리
8. 공지사항 리스트를 조회한다.
	1. 검색어로 제목을 사용하여, 제목의 일부분이 포함된 결과를 조회할 수 있습니다.  
	검색어가 없으면 전체 리스트를 조회하면 됩니다.
	2. 기본 정렬조건은 생성시각(createAt)으로 내림차순으로 정렬합니다.(최신순으로 정렬)
	3. 정렬조건에 따라 제목순, 생성시각 순으로 정렬할 수 있습니다.
	4. 정렬순서에 따라 오름차순 또는 내림차순을 선택하여 정렬할 수 있습니다.
	5. 페이징 처리를 할 수 있습니다. 
    *	기본값으로는  1page이며 page당 조회건수 10건입니다.
    *	page당 조회건수는 최대 20건으로 제한해주세요.
9. 공지사항 단건을 조회한다.
