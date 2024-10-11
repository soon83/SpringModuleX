# jooq
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
