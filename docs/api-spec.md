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
- **설명**: 관리자가 사용자의 가입을 승인합니다.  
  승인된 사용자만 로그인할 수 있습니다.

### 📥 Request Body
```json
{
  "userId": "hamppung",
  "password": "1234"
}
```

## 유저 가입 승인
- **URL**: `/admin/approve/`
- **Method**: `PUT`
- **설명**: 사용자 가입시 가입승인 합니다.

### 🔐 권한
- `ADMIN` 권한 필요 (JWT 토큰 필요)
- 
### 📥 Request Body
```json
["hamppung", "cooldev", "testuser"]
```
