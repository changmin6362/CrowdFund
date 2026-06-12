# CrowdFund 프로젝트
2026.05.08 ~ 2026.06.12(개발중)

## 💡 주요 기능 및 유저 플로우 (추후 데모 영상 추가 예정)

용어 설명:
- **프로젝트**: 창작자가 자신의 창작물을 공개하는 공간
- **리워드**: 후원자들이 자금을 지원한 대가로 받게 되는 보상

### 1. 회원 인증 및 사용자 관리
- **회원가입/로그인**: JWT(JSON Web Token) 기반의 인증 시스템을 통해 안전한 회원가입 및 로그인 처리를 수행합니다.
- **마이페이지**: 사용자는 개인 정보를 조회/수정 및 삭제(회원 탈퇴)할 수 있습니다.
- **내 후원 내역**: 사용자는 자신의 후원 내역을 **복합 커서 기반 최신순 페이지네이션**과 **상태 필터(후원 이행, 후원 상태)**를 사용하여 조회할 수 있습니다.
- **내 배송지 관리**: 사용자는 자신의 배송지 정보를 조회/수정 및 삭제할 수 있습니다.
- **내 프로젝트 내역**: 사용자는 자신의 프로젝트 내역을 조회/수정 및 삭제할 수 있습니다.


### 2. 프로젝트 관리 (창작자)
프로젝트는 생성한 본인만 관리할 수 있습니다. 프로젝트를 생성한 사용자를 창작자로 부릅니다.

- **프로젝트 생성**: 사용자는 특정 카테고리에 속한 프로젝트를 생성하여 창작자가 될 수 있습니다.
- **프로젝트 관리**: 창작자는 본인의 프로젝트 정보를 수정하거나 삭제할 수 있습니다.  
- **리워드 생성**: 창작자는 본인의 프로젝트에 리워드를 생성할 수 있습니다.
- **리워드 관리**: 창작자는 본인의 프로젝트에 생성된 리워드를 수정하거나 삭제할 수 있습니다.

### 3. 프로젝트 후원 및 커뮤니티
- **프로젝트 조회**: 모든 사용자는 **복합 커서 기반 최신순 페이지네이션**과 **카테고리/상태 필터**를 통해 프로젝트를 탐색하고 상세 정보를 조회할 수 있습니다.
- **후원하기**: 사용자는 프로젝트의 리워드를 선택하고 후원 및 결제를 진행합니다.
- **후원 및 배송지 관리**: 후원자는 자신의 후원 내역을 조회하고 리워드를 배송받을 배송지를 수정할 수 있습니다.
- **댓글 기능**: 프로젝트 상세 페이지에서 후원자들은 프로젝트에 대한 댓글을 남길 수 있습니다.
- **내 댓글 관리**: 사용자는 자신이 작성한 댓글을 **커서 기반 최신순 페이지네이션**을 통해 조회하며, 수정 및 삭제할 수 있습니다.

### 4. 리워드 및 결제
- **리워드 관리**: 창작자는 프로젝트에 맞는 리워드를 생성하고 관리합니다.
- **결제 내역 조회**: 후원자는 마이페이지에서 결제 내역을 조회할 수 있습니다.

### 5. 관리자 기능
- **카테고리 관리**: 관리자는 시스템 전반에서 사용되는 프로젝트 카테고리를 생성하고 관리합니다.
- **후원 관리**: 관리자는 **복합 커서 기반 최신순 페이지네이션**과 **상태 필터(후원 이행, 후원 상태)**를 통해 전체 후원 내역을 조회하고 상세 정보를 확인할 수 있습니다.


## 🛠 기술 스택
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.14
- **ORM/DB**: Spring Data JDBC, MyBatis, MariaDB
- **Security**: Spring Security, JWT (jjwt)
- **Build Tool**: Gradle, Docker
- **API Documentation**: SpringDoc OpenAPI (Swagger)💡 [API SwaggerLink](http://3.34.181.49/swagger-ui/index.html#)

## 시스템 아키텍처(System Architecture)

```mermaid
graph TD
    classDef client fill:#3498db,stroke:#2980b9,stroke-width:2px,color:#fff;
    classDef server fill:#2ecc71,stroke:#27ae60,stroke-width:2px,color:#fff;
    classDef db fill:#e67e22,stroke:#d35400,stroke-width:2px,color:#fff;
    classDef infra fill:#9b59b6,stroke:#8e44ad,stroke-width:2px,color:#fff;

    subgraph Client ["📱 Client Layer"]
        Web["💻 React / Next.js"]:::client
    end

    subgraph Infra ["🌐 Infrastructure & CI/CD"]
        GH["🐙 GitHub Actions"]:::infra
        EC2["🖥️ AWS EC2 (Docker)"]:::infra
    end

    subgraph App ["⚙️ Application Layer (Backend)"]
        Server["🚀 Spring Boot 3.5.14"]:::server
    end

    subgraph Storage ["💾 Data Storage Layer"]
        RDS[("🗄️ AWS RDS (MariaDB)")]:::db
    end

    Web -->|HTTPS Requests| EC2
    EC2 -->|Runs Container| Server
    Server -->|SQL Queries via SSH Tunnel| RDS
    GH -->|Deploy/Build| EC2
```


## 🚀 CD 및 배포 방식


CD 구현 방식: GitHub Actions과 Docker Hub Repository 사용


- **배포 프로세스**: `master` 브랜치로의 Pull Request가 `merged` 되는 시점에 GitHub Actions가 자동으로 실행됩니다.
  1. 소스 코드 체크아웃 및 Java 17 환경 설정
  2. Docker 이미지 빌드 및 Docker Hub 푸시
  3. Amazon EC2 원격 접속 (SSH)
  4. 기존 컨테이너 중지/삭제 및 최신 이미지 Pull 후 컨테이너 실행

## 📂 패키지 구조

- `io.github.crowdfund.global`: 전역 설정 (Security, 전역 예외 핸들러, 공통 응답 DTO, 페이지네이션 등)
- `io.github.crowdfund.domain`: Spring Data JDBC를 위한 도메인 엔티티와 도메인 인터페이스, 그리고 MyBatis를 위한 도메인 매퍼
- `io.github.crowdfund.feature`: 각 기능별 서비스 로직 및 API 컨트롤러 (auth, project, reward, payment 등)



## ERD

<img width="5222" height="3856" alt="Image" src="https://github.com/user-attachments/assets/ed0ba577-fabb-4b34-8727-9e71e2e6448d" />

## 🌐 API Overall Architecture 

> 💡 [API SwaggerLink](http://3.34.181.49/swagger-ui/index.html#)


#### 1. 인증 및 사용자
```mermaid
graph TD
    %% 스타일 정의
    classDef main fill:#3498db,stroke:#2980b9,stroke-width:2px,color:#fff;
    classDef domain fill:#f1c40f,stroke:#f39c12,stroke-width:2px,color:#000;
    classDef api fill:#ecf0f1,stroke:#bdc3c7,stroke-width:1px,color:#000;

    %% 메인 루트
    Root["🚀 Base URL: /api"]:::main

    Auth["🔐 Auth (인증)"]:::domain
    User["👤 User (사용자)"]:::domain
    UserAddress["📦 UserAddress (사용자 배송지)"]:::domain

    Root --> Auth
    Root --> User
    Root --> UserAddress

    %% 2단계: 엔드포인트 연결
    Auth --> A1["POST /auth/signup"]:::api
    Auth --> A2["POST /auth/login"]:::api

    User --> U1["GET /users/me/nickname"]:::api
    User --> U2["GET /users/me/data"]:::api
    User --> U3["PUT /users/me"]:::api
    User --> U4["DELETE /users/me"]:::api

    UserAddress --> UA1["POST /users/me/address"]:::api
    UserAddress --> UA2["GET /users/me/addresses"]:::api
    UserAddress --> UA3["PATCH /users/me/address/{addressId}"]:::api
    UserAddress --> UA4["PATCH /users/me/address/{addressId}/default"]:::api
    UserAddress --> UA5["DELETE /users/me/address/{addressId}"]:::api
```

#### 2. 프로젝트, 카테고리 및 댓글
```mermaid
graph TD
    %% 스타일 정의
    classDef main fill:#3498db,stroke:#2980b9,stroke-width:2px,color:#fff;
    classDef domain fill:#f1c40f,stroke:#f39c12,stroke-width:2px,color:#000;
    classDef api fill:#ecf0f1,stroke:#bdc3c7,stroke-width:1px,color:#000;

    %% 메인 루트
    Root["🚀 Base URL: /api"]:::main

    CreatorProject["🏗️ Creator Project (창작자 프로젝트)"]:::domain
    Category["📂 Category (카테고리)"]:::domain
    Comment["💬 Comment (댓글)"]:::domain
    MyComment["👤 My Comment (내 댓글)"]:::domain
    UserProject["👤 User Project (사용자 프로젝트)"]:::domain

    Root --> CreatorProject
    Root --> Category
    Root --> Comment
    Root --> MyComment
    Root --> UserProject

    %% 2단계: 엔드포인트 연결
    CreatorProject --> P1["POST /creator/projects"]:::api
    CreatorProject --> P2["GET /creator/projects/me"]:::api
    CreatorProject --> P3["PATCH /creator/projects/{id}"]:::api

    Category --> C1["POST /admin/categories"]:::api
    Category --> C2["GET /categories"]:::api

    Comment --> CM1["POST /projects/{id}/comments"]:::api
    Comment --> CM2["GET /projects/{id}/comments"]:::api

    MyComment --> MC1["GET /users/me/comments"]:::api
    MyComment --> MC2["PATCH /users/me/comments/{commentId}"]:::api
    MyComment --> MC3["DELETE /users/me/comments/{commentId}"]:::api

    UserProject --> UP1["GET /projects"]:::api
    UserProject --> UP2["GET /projects/{projectId}"]:::api
```

#### 3. 결제, 후원 및 리워드
```mermaid
graph TD
    %% 스타일 정의
    classDef main fill:#3498db,stroke:#2980b9,stroke-width:2px,color:#fff;
    classDef domain fill:#f1c40f,stroke:#f39c12,stroke-width:2px,color:#000;
    classDef api fill:#ecf0f1,stroke:#bdc3c7,stroke-width:1px,color:#000;

    %% 메인 루트
    Root["🚀 Base URL: /api"]:::main

    Payment["💳 Payment (결제)"]:::domain
    Reward["🎁 Reward (리워드)"]:::domain
    Pledge["💰 Pledge (후원)"]:::domain
    PledgeAddress["📦 PledgeAddress (후원 배송지)"]:::domain
    CreatorPledge["🏗️ Creator Pledge (창작자 후원)"]:::domain

    Root --> Payment
    Root --> Reward
    Root --> Pledge
    Root --> PledgeAddress
    Root --> CreatorPledge

    %% 2단계: 엔드포인트 연결
    Payment --> PY1["POST /payments"]:::api
    Payment --> PY2["GET /payments/{id}/history"]:::api

    Reward --> RW1["POST /creator/projects/{id}/rewards"]:::api
    Reward --> RW2["GET /user/projects/{id}/rewards"]:::api

    Pledge --> PL1["POST /pledges/me"]:::api
    Pledge --> PL2["GET /pledges/me/{pledgeId}"]:::api
    Pledge --> PL3["DELETE /pledges/me/{pledgeId}"]:::api
    Pledge --> PL4["GET /pledges/me"]:::api
    Pledge --> PL5["GET /admin/pledges"]:::api
    Pledge --> PL6["GET /admin/pledges/{pledgeId}"]:::api

    PledgeAddress --> PLA1["PUT /pledges/{pledgeId}/address"]:::api

    CreatorPledge --> CP1["PATCH /creator/pledges/{pledgeId}/fulfill"]:::api
```