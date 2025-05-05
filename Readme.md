# How About Games

“GameReview”는 일반 게이머들의 솔직한 경험을 바탕으로 그래픽, 스토리, 게임플레이, 사운드 등 각 요소별 세부 평점을 부여하고
리뷰어 신뢰도 평점을 통해 더욱 객관적인 정보를 제공하는 커뮤니티 기반 게임 평론 사이트입니다. RAWG API를 활용해최신 게임 메타데이터(출시일, 개발사, 유통사,
이미지 등)를 자동으로 불러오며, Spring Boot + Spring Data JPA + Thymeleaf + Spring Security 조합으로 안전하고 확장성 있는 웹 서비스를 구현했습니다.

## 주요 기능
- **회원가입 / 로그인 / 로그아웃 / 내 정보 조회**  
  Spring Security를 통한 인증·인가, 비밀번호 암호화, 중복검사(AJAX) 지원  
- **게임 검색 & 상세 조회**  
  RAWG API 연동으로 게임 리스트와 상세 정보(표지, 플랫폼, 출시일 등) 제공  
- **리뷰 CRUD**  
  ● 최대 200자 텍스트 리뷰  
  ● 그래픽·스토리·게임플레이·사운드 0.5 단위 평가(0~5점)  
  ● 세부 평점 합산 후 ×5로 0~100 총점 자동 계산  
  ● 한 게임당 사용자당 1회 작성 제한  
- **좋아요/싫어요**  
  리뷰별 좋아요·싫어요 토글 기능  
- **프론트엔드**  
  Thymeleaf + HTML/CSS/JavaScript로 UI 구현, 슬라이더·AJAX 중복검사·경고 메시지 처리  

## 기술 스택
- **Backend**: Spring Boot 3.4.1, Spring Data JPA, Spring Security  
- **Template**: Thymeleaf (Thymeleaf Extras for Spring Security)  
- **Database**: H2 (개발용) 
- **External API**: RAWG Video Games Database API  
- **Dev Tools**: Spring Boot DevTools, Lombok  

## Branches

- **main** (default)  
  - 프로젝트 전반에 대한 문서 및 분석 자료  
  - Conceptualization, Analysis, Design

- **master**  
  - 실제 구현 소스코드
