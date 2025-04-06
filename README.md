# Spring Boot JWT 인증 예제

Spring Boot 기반으로 JWT(Json Web Token)를 이용한 인증 시스템 구현 예제입니다.  

📌 [API 명세 보기](./docs/api-spec.md)

---
## 라이브러리
JWT, JPA, Lombok

---
## 파일 구조

```
src
├── config
│   ├── SecurityConfig.java            # Spring Security 설정
│   ├── AuthFilter.java                # JWT 인증 필터
│   │
│   ├── exception                      # 예외 처리 관련 폴더
│   │   ├── ApiException.java
│   │   ├── ErrorCode.java
│   │   └── GlobalExceptionHandler.java
│   │
│   ├── initializer
│   │   └── AdminInitializer.java       # 최초 관리자 계정 생성
│   │
│   └── jwt
│       ├── JwtTokenProvider.java      # JWT 생성/검증 유틸
│       └── JwtAuthentication.java     # 인증 객체
│
├── web
│   └── login
│   │   └── UserController.java        # 유저 정보 API
│   └── admin
|   │   └── AdminController.java       # 관리자 API
|   └── web.menu                
|       └── MenuController.java        # 메뉴 조회 API
│
└── service
    └── user
    │   ├── UserService.java             # 사용자 서비스 인터페이스
    │   ├── UserServiceImpl.java         # 사용자 서비스 구현체
    │   ├── UserRepository.java          # JPA 기반 사용자 Repository
    │   ├── UserEntity.java              # JPA 사용자 Entity
    │   └── UserVO.java                  # 요청/응답용 사용자 DTO
    |
    └── admin                   
    │   ├── AdminService.java              # 관리자 Service 인터페이스
    │   └── AdminServiceImpl.java          # 관리자 Service 구현   
    │
    └── menu
        ├── MenuEntity.java                # 메뉴 Entity
        ├── MenuRepository.java            # 메뉴 Repository
        ├── MenuService.java               # 메뉴 Service 인터페이스
        └── MenuServiceImpl.java           # 메뉴 Service 구현
        └── MenuVO.java                    # 요청/응답용 사용자 DTO
```
---

## 실행 방법
```bash
./gradlew build
./gradlew bootRun
```

## application.yml 
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb                 # H2 메모리 DB (테스트용)
    driver-class-name: org.h2.Driver
    username: sa
    password:

  h2:
    console:
      enabled: true                         # H2 웹 콘솔 활성화 (http://localhost:8080/h2-console)

  jpa:
    hibernate:
      ddl-auto: update                      # 엔티티 기반 테이블 자동 생성/업데이트
    show-sql: true                          # 콘솔에 SQL 출력
    properties:
      hibernate:
        format_sql: true                    # SQL 포맷팅
jwt:
  secret: {secret key}

admin:
  id: ${ADMIN_ID}
  password: ${ADMIN_PASSWORD}
```
