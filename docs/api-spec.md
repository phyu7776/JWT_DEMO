# API 명세서

## 1. 사용자 관련 API

### 회원가입
- **URL**: `/users/signup`
- **Method**: POST
- **Request Body**:
```json
{
    "userId": "user123",
    "password": "password123",
    "name": "홍길동",
    "nickname": "길동이",
    "birthday": "1990-01-01"
}
```

### 로그인
- **URL**: `/users/login`
- **Method**: POST
- **Request Body**:
```json
{
    "userId": "user123",
    "password": "password123"
}
```
- **Response**:
```json
{
    "uid": "user_uid",
    "userId": "user123",
    "name": "홍길동",
    "nickname": "길동이",
    "role": "USER",
    "state": "W",
    "token": {
        "accessToken": "access_token",
        "refreshToken": "refresh_token"
    }
}
```

### 로그아웃
- **URL**: `/users/logout`
- **Method**: POST
- **Request Body**:
```json
{
    "token": {
        "accessToken": "access_token"
    }
}
```

### 토큰 재발급
- **URL**: `/users/reissue`
- **Method**: POST
- **Request Body**:
```json
{
    "token": {
        "refreshToken": "refresh_token"
    }
}
```

### 사용자 정보 수정
- **URL**: `/users/update`
- **Method**: PATCH
- **Request Body**:
```json
{
    "uid": "user_uid",
    "name": "홍길동",
    "nickname": "길동이",
    "birthday": "1990-01-01",
    "role": "USER",
    "state": "U"
}
```

### 비밀번호 변경
- **URL**: `/users/changePassword`
- **Method**: PATCH
- **Request Body**:
```json
{
    "oldPassword": "old123",
    "newPassword": "new123"
}
```

### 사용자 삭제
- **URL**: `/users/delete`
- **Method**: DELETE
- **Request Body**:
```json
[
    {
        "uid": "user_uid"
    }
]
```

## 2. 관리자 API

### 사용자 승인
- **URL**: `/admin/approve`
- **Method**: PUT
- **Request Body**:
```json
[
    {
        "uid": "user_uid",
        "state": "U"
    }
]
```

### 사용자 목록 조회
- **URL**: `/admin/getUsers`
- **Method**: GET
- **Response**:
```json
{
    "totalCount": 10,
    "users": [
        {
            "uid": "user_uid",
            "userId": "user123",
            "name": "홍길동",
            "nickname": "길동이",
            "role": "USER",
            "state": "W"
        }
    ]
}
```

## 3. 메뉴 API

### 메뉴 생성
- **URL**: `/menu/create`
- **Method**: POST
- **Request Body**:
```json
{
    "name": "게시판",
    "description": "게시판 메뉴",
    "url": "/board",
    "restricted": "USER,ADMIN",
    "parentUID": null,
    "menuOrder": 1
}
```

### 메인 메뉴 조회
- **URL**: `/menu/getMain`
- **Method**: GET
- **Response**:
```json
[
    {
        "uid": "menu_uid",
        "name": "게시판",
        "description": "게시판 메뉴",
        "url": "/board",
        "restricted": "USER,ADMIN"
    }
]
```

### 하위 메뉴 조회
- **URL**: `/menu/get/{uid}`
- **Method**: GET
- **Response**:
```json
[
    {
        "uid": "menu_uid",
        "name": "게시판",
        "description": "게시판 메뉴",
        "url": "/board",
        "restricted": "USER,ADMIN"
    }
]
```

### 전체 메뉴 트리 조회
- **URL**: `/menu/getFullMenuTree`
- **Method**: GET
- **Response**:
```json
[
    {
        "uid": "menu_uid",
        "name": "게시판",
        "description": "게시판 메뉴",
        "url": "/board",
        "restricted": "USER,ADMIN",
        "children": [
            {
                "uid": "submenu_uid",
                "name": "공지사항",
                "description": "공지사항 메뉴",
                "url": "/board/notice",
                "restricted": "USER,ADMIN"
            }
        ]
    }
]
```

## 4. 설정 API

### 설정 조회
- **URL**: `/config/getConfig`
- **Method**: GET
- **Response**:
```json
{
    "liveAccessToken": 1800000,
    "liveRefreshToken": 604800000
}
```

### 설정 생성
- **URL**: `/config/create`
- **Method**: POST
- **Request Body**:
```json
{
    "type": "TOKEN",
    "configValue": "1800000",
    "subType": "ACCESS",
    "name": "Access Token 유효시간"
}
```

### 설정 조회 (타입별)
- **URL**: `/config/get/{type}/{subType}`
- **Method**: GET
- **Response**:
```json
[
    {
        "uid": "config_uid",
        "type": "TOKEN",
        "configValue": "1800000",
        "subType": "ACCESS",
        "name": "Access Token 유효시간"
    }
]
```
