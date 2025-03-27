# Spring Boot JWT 인증 예제

Spring Boot 기반으로 JWT(Json Web Token)를 이용한 인증 시스템 구현 예제입니다.  

---
## 파일 구조
```
src
├── config
│   ├── SecurityConfig.java            # Spring Security 설정
│   ├── AuthFilter.java                # JWT 인증 필터
│   ├── exception                      # 예외 처리 관련 폴더
│   │   ├── ApiException.java
│   │   ├── ErrorCode.java
│   │   └── GlobalExceptionHandler.java
│   └── jwt
│       ├── JwtTokenProvider.java      # JWT 생성/검증 유틸
│       └── JwtAuthentication.java     # 인증 객체
│
├── web
│   └── login
│       └── AuthController.java        # 로그인 API 컨트롤러
│
└── service
    └── user
        ├── UserService.java
        ├── UserServiceImpl.java
        └── UserVO.java
```
---

## 실행 방법
```bash
./gradlew build
./gradlew bootRun
```

## application.yml 안에 JWT에 사용할 secret 키 작성

jwt:
  secret:
