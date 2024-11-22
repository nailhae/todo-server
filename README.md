# Todo API Server Application

SpringBoot Framework을 익히기 위한 할 일 관리 RESTful API 서버입니다.

## 주요 기능

- 할 일 추가, 삭제, 수정, 조회
- 할 일의 상태 변경
- 할 일 검색 기능
- 기한별 할 일 조회

## API 스펙

| Method | Endpoint                 | Request Body                                                                                 | Response Body                                                                                                                                                                                                                  | Description                             |
|--------|--------------------------|----------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------|
| POST   | `/tasks`                 | `{ "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01" }`              | `{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO"}`                                                                                                                       | 새로운 할 일 생성                              |
| GET    | `/tasks`                 | `dueDate` (optional)                                                                         | `[{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO"}, {"id": 2, "title": "Task 2", "description": "Do something else", "dueDate": "2023-05-01", "status": "IN_PROGRESS"}]` | 모든 할 일 조회(마감일이 있을 경우, 마감일에 해당하는 할 일 조회) |
| GET    | `/tasks/{id}`            | N/A                                                                                          | `{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO"}`                                                                                                                       | 특정 ID 에 해당하는 할 일 조회                     |
| GET    | `/tasks/status/{status}` | N/A                                                                                          | `[{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO"}, {"id": 3, "title": "Task 3", "description": "Do something else", "dueDate": "2023-05-02", "status": "TODO"}]`        | 특정 상태에 해당하는 할 일 모두 조회                   |
| PUT    | `/tasks/{id}`            | `{ "title": "Updated Task 1", "description": "Do something else", "dueDate": "2023-05-02" }` | `{"id": 1, "title": "Updated Task 1", "description": "Do something else", "dueDate": "2023-05-02", "status": "TODO"}`                                                                                                          | 특정 ID 에 해당하는 할 일 수정                     |
| PATCH  | `/tasks/{id}/status`     | `{ "status": "IN_PROGRESS" }`                                                                | `{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "IN_PROGRESS"}`                                                                                                                | 특정 ID 에 해당하는 할 일의 상태 변경                 |
| DELETE | `/tasks/{id}`            | N/A                                                                                          | `{ "success": true }`                                                                                                                                                                                                          | 특정 ID 에 해당하는 할 일 삭제                     |

## 필요 도구

- JDK 17 이상
- Gradle 7.3 이상

## 개발환경

- Spring Boot 3.0.4
- H2 인메모리 데이터베이스
- JPA
- Lombok

## 테스트환경

- Spring Boot Test
- Mockito
- JUnit 5