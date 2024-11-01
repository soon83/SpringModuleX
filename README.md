<!-- @formatter:off -->
# jooq

## mariadb

### generate jooq DB
```sql
CREATE SCHEMA spring_module_x_generate;
GRANT ALL PRIVILEGES ON `spring_module_x_generate`.* TO 'spring_module_x_generate'@'%' IDENTIFIED BY 'spring_module_x_generate!@34';
REVOKE DELETE HISTORY ON `spring_module_x_generate`.* FROM 'spring_module_x_generate'@'%';
```

### local test DB
```sql
CREATE SCHEMA spring_module_x_local;
GRANT ALL PRIVILEGES ON `spring_module_x_local`.* TO 'spring_module_x_local'@'%' IDENTIFIED BY 'spring_module_x_local!@34';
REVOKE DELETE HISTORY ON `spring_module_x_local`.* FROM 'spring_module_x_local'@'%';
```
---

## postgresql

### generate jooq DB

사용자 생성
```sql
-- 사용자 생성
CREATE USER spring_module_x_generate WITH PASSWORD  'spring_module_x_generate!@34';
```

데이터베이스 생성
```sql
-- 데이터베이스 생성
CREATE DATABASE spring_module_x_generate;
```

데이터베이스에 연결할 권한 부여
```sql
-- 데이터베이스에 연결할 권한 부여
GRANT CONNECT ON DATABASE spring_module_x_generate TO spring_module_x_generate;
```

스키마 사용 및 생성 권한 부여
[매우 중요] 먼저, 우측 상단의 SelectBox 에서 spring_module_x_generate 데이터베이스를 선택 후, 아래의 SQL 을 날린다
```sql
-- [매우 중요] 먼저, 우측 상단의 SelectBox 에서 spring_module_x_generate 데이터베이스를 선택 후, 아래의 SQL 을 날린다
-- 스키마 사용 및 생성 권한 부여
GRANT CREATE, USAGE ON SCHEMA public TO spring_module_x_generate;
```

테이블에 대한 모든 권한 부여
```sql
-- 테이블에 대한 모든 권한 부여
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO spring_module_x_generate;
```

시퀀스 사용 및 생성 권한 부여
```sql
-- 시퀀스 사용 및 생성 권한 부여
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO spring_module_x_generate;
```

### local test DB

사용자 생성
```sql
-- 사용자 생성
CREATE USER spring_module_x_local WITH PASSWORD  'spring_module_x_local!@34';
```

데이터베이스 생성
```sql
-- 데이터베이스 생성
CREATE DATABASE spring_module_x_local;
```

데이터베이스에 연결할 권한 부여
```sql
-- 데이터베이스에 연결할 권한 부여
GRANT CONNECT ON DATABASE spring_module_x_local TO spring_module_x_local;
```

스키마 사용 및 생성 권한 부여
[매우 중요] 먼저, 우측 상단의 SelectBox 에서 spring_module_x_local 데이터베이스를 선택 후, 아래의 SQL 을 날린다
```sql
-- [매우 중요] 먼저, 우측 상단의 SelectBox 에서 spring_module_x_local 데이터베이스를 선택 후, 아래의 SQL 을 날린다
-- 스키마 사용 및 생성 권한 부여
GRANT CREATE, USAGE ON SCHEMA public TO spring_module_x_local;
```

테이블에 대한 모든 권한 부여
```sql
-- 테이블에 대한 모든 권한 부여
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO spring_module_x_local;
```

시퀀스 사용 및 생성 권한 부여
```sql
-- 시퀀스 사용 및 생성 권한 부여
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO spring_module_x_local;
```
