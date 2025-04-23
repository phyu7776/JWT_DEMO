# JWT Authentication Demo Project

Spring Boot 기반의 JWT 인증 시스템을 구현한 데모 프로젝트입니다.

## 프로젝트 구조

```
src/main/java/com/example/jwt/
├── config/                    # 설정 관련 클래스
│   ├── AuthFilter.java        # JWT 인증 필터
│   ├── SecurityConfig.java    # Spring Security 설정
│   ├── JwtTokenProvider.java  # JWT 토큰 생성/검증
│   └── redis/                 # Redis 설정
├── web/                       # API 컨트롤러
│   ├── login/                 # 로그인 관련 API
│   ├── admin/                 # 관리자 API
│   ├── menu/                  # 메뉴 관리 API
│   └── config/                # 설정 API
└── service/                   # 비즈니스 로직
    ├── user/                  # 사용자 관리
    ├── admin/                 # 관리자 기능
    ├── menu/                  # 메뉴 관리
    └── config/                # 설정 관리
```

## 주요 기능

1. JWT 기반 인증 시스템
   - Access Token & Refresh Token 구현
   - Redis를 이용한 토큰 관리
   - 토큰 재발급 기능

2. 사용자 관리
   - 회원가입
   - 로그인/로그아웃
   - 사용자 승인/삭제

3. 메뉴 관리
   - 계층형 메뉴 구조
   - 권한 기반 메뉴 접근 제어

4. 설정 관리
   - 시스템 설정 관리
   - 토큰 유효시간 설정

## API 명세

📌 [API 명세 보기](./docs/api-spec.md)

## 기술 스택

- Java 21
- Spring Boot 3.4.4
- Spring Security
- Spring Data JPA
- Spring Data Redis
- QueryDSL 5.1.0
- MariaDB 3.3.3
- JWT
- Lombok
- Spring Boot DevTools

## 실행 방법

1. 환경 변수 설정
```bash
export ADMIN_ID=admin
export ADMIN_PASSWORD=admin123
```

2. 프로젝트 빌드 및 실행
```bash
./gradlew build
./gradlew bootRun
```

## 보안 설정

- JWT 토큰 유효시간 설정
  - Access Token: 30분
  - Refresh Token: 7일
- 비밀번호 암호화: BCrypt
- CORS 설정
- CSRF 보호 비활성화 (JWT 사용으로 인해)
