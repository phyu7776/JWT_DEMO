# API 명세서

Spring Boot + JWT 인증 기반 API 명세입니다.  
현재 지원하는 기능은 회원가입, 로그인입니다.

## 회원가입
- **URL**: `/login/signup`  
- **Method**: `POST`  

### Request Body
```json
{
  "userId": "hamppung",
  "password": "1234"
}
```

## 로그인 (JWT 발급)
- **URL**: `/login`
- **Method**: `POST`
- **설명**: 사용자 로그인 시 ID/PW 검증 후 JWT 토큰을 발급합니다.

### 📥 Request Body
```json
{
  "userId": "hamppung",
  "password": "1234"
}
```
