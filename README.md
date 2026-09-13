# CrowdFund — 복합 커서 기반 페이징과 모듈형 아키텍처를 적용한 크라우드 펀딩 RESTful API

- 2026.05.08 ~ 2026.06.15(1차 개발 완료)

[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=flat&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/gradle-%2302303A.svg?style=flat&logo=gradle&logoColor=white)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=flat&logo=docker&logoColor=white)](https://www.docker.com/)
[![MariaDB](https://img.shields.io/badge/mariadb-%23003545.svg?style=flat&logo=mariadb&logoColor=white)](https://mariadb.org/)

## QR 코드 및 API Swagger 이미지

<div >
    <a href="https://github.com/changmin6362/CrowdFund">
      <img width="256" height="256" alt="qr코드" src="https://github.com/user-attachments/assets/1e49eeb2-8d41-4bf4-9b7a-c1188e84a8e5" />
    </a>
</div>

<details >
    <summary>API Swagger 펼치기</summary>
    <img alt="swagger-ui-125%" src="https://github.com/user-attachments/assets/c82b4cd8-5698-4d2e-b0e2-f3c94f371c9b" />
</details>

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

## ERD

<img width="5222" height="3856" alt="Image" src="https://github.com/user-attachments/assets/ed0ba577-fabb-4b34-8727-9e71e2e6448d" />


## 📂 패키지 분리 전략

- `io.github.crowdfund.global`: 전역 설정 (Security, 전역 예외 핸들러, 공통 응답 DTO, 페이지네이션처럼 재사용되는 공통 코드)
- `io.github.crowdfund.domain`: Spring Data JDBC 엔티티, 도메인 인터페이스, MyBatis 도메인 매퍼
- `io.github.crowdfund.feature`: 각 기능별 서비스 로직 및 API 컨트롤러 모듈화


## 💡 핵심 구현 기술

### 1. 성능 최적화를 위한 복합 커서 기반 페이지네이션

    도입 배경:

    - 프론트엔드에서 무한스크롤을 구현하고 싶은데, 기존 OFFSET / LIMIT 방식을 사용하면 동일 데이터에 대한 중복 요청이 발생해서 성능 문제가 생김
    
    구현 방식:

    - 복합 커서 조건식: (created_at < :cursorCreatedAt OR (created_at = :cursorCreatedAt AND id < :cursorId))을 적용해 대용량 데이터에서도 일정한 조회 성능(O(1)) 및 데이터 정합성 보장
    - hasNext 계산 방식: Limit + 1 전략: 다음 페이지 존재 여부(hasNext)를 별도의 COUNT 쿼리 없이 판단하여 DB 부하 최소화
    - 공통 모듈화: 제네릭과 함수형 인터페이스(cursorExtractor)를 활용한 CursorPaginationProcessor로 복합 커서 주체를 주입해서 재사용 할 수 있게 별도 모듈로 분리

### 2. Spring Data JDBC & MyBatis 혼용 도입

    도입 배경:

    - JPA와 MyBatis 혼용 설계를 구현해보고 싶었지만, 프로젝트 일정 내에 높은 학습 비용과 복잡한 영속성 컨텍스트 관리가 요구되는 JPA를 도입하기는 어려울 것이라고 판단이 들었음. 그래서 배우기 쉽고 향후 JPA로 마이그레이션이 용이한 Spring Data JDBC를 징검다리 기술로 채택함
    
    도입 효과: 
    - 개발 생산성 극대화: 단순 반복적인 CRUD 및 단건 조회는 Spring Data JDBC의 메서드 이름 기반 쿼리로 빠르게 쳐내고, 복잡한 통계나 다중 조인(Join)이 필요한 핵심 비즈니스 로직에만 MyBatis를 사용하여 전체적인 개발 기간을 단축했습니다.
    - 안정적인 데이터 관리: Spring Data JDBC는 JPA와 달리 영속성 컨텍스트가 없어, MyBatis와 동일한 데이터베이스 세션 및 트랜잭션 범위 내에서 예기치 못한 데이터 정합성 오류나 동기화 문제없이 안정적으로 트랜잭션을 제어할 수 있었습니다.
    - 성장 지향적 아키텍처 구축: 엔티티 중심의 도메인 설계와 리포지토리 패턴을 미리 적용해 둠으로써, 향후 학습 숙련도에 따라 JPA(Spring Data JPA)로 매끄럽게 마이그레이션할 수 있는 기술적 발판을 마련했습니다.

### 3. 무상태(Stateless) JWT 기반 인증/인가 및 세분화된 리소스 권한 제어

    도입 배경:

    - RESTful API 서버의 확장성을 위해 세션 클러스터링 의존 없이 무상태(Stateless) 아키텍처를 유지하면서, 사용자/창작자/관리자 간의 명확한 인가(Authorization) 처리가 필요함.

    구현 방식:

    - Spring Security + JWT 필터 체인: JwtAuthenticationFilter를 커스텀 구현하여 Access Token 검증 및 SecurityContext 주입
    - 도메인 레벨 소유권 검증 (Ownership Validation): URL Path 파라미터 변조(IDOR 공격)를 방지하기 위해 SecurityUser.isOwner()를 통해 본인의 후원/프로젝트만 조회/취소/수정 가능하도록 서비스 계층에서 2차 인가 검증 수행
    - 예외 핸들링 표준화: JwtAuthenticationEntryPoint, JwtAccessDeniedHandler를 통해 401/403 에러 응답 규격을 통일

    효과:

    - 서버 수평 확장(Scale-out) 시 세션 동기화 문제 제거 및 안전한 사용자 리소스 격리 보장
    - CD 구현 방식: GitHub Actions과 Docker Hub Repository 사용


- **배포 프로세스**: `master` 브랜치로의 Pull Request가 `merged` 되는 시점에 GitHub Actions가 자동으로 실행됩니다.
  1. 소스 코드 체크아웃 및 Java 17 환경 설정
  2. Docker 이미지 빌드 및 Docker Hub 푸시
  3. Amazon EC2 원격 접속 (SSH)
  4. 기존 컨테이너 중지/삭제 및 최신 이미지 Pull 후 컨테이너 실행




