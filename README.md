# POLY_ASSIGNMENT

## FEATURE
* [ ] 회원가입
* [ ] 게시글 작성
* [ ] 게시글 수정
* [ ] 게시글 삭제
* [ ] 로그인
* [ ] 로그아웃

---

## FEATURE_TO_IMPLEMENT

### 회원가입

약식의 회원가입으로 [ 아이디, 비밀번호, 이름, 닉네임 ] 만 받아서,

아이디와 비밀번호 체크 후 JWT TOKEN을 발급 예정

### 게시글 작성

회원만이 게시글을 작성 할 수 있으며, TOKEN 체크 후 게시글 작성 버튼이 보이도록 함

### 게시글 수정

본인의 게시글임을 인증 후 수정할 수 있도록 변경

### 게시글 삭제

본인의 게시글임을 인증 후 삭제 할 수 있도록 변경

### 로그인

아이디 비밀번호 체크 후 TOKEN 발급

스프링 세션에서 TOKEN 유지

### 로그아웃

TOKEN 만료처리 및 세션 삭제