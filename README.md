## Library

### Short about the project
Simple implementation of library of books and authors. Implemented N-tier architecture.
CRUD operations plus some select queries.

### Technologies used
- Spring Boot 2.7.2
- Swagger
- Lombok
- Hibernate
- JSON
- MySQL

### Entity
1. Book;
- bookName
- List of authors
- publishedAmount (amount of published book’s instances)
- soldAmount (amount of sold book’s instances)
2. Author;
- authorName
- birthDate
- phone

Tables connection is many-to-many.

### How to run
1. Clone
2. Enable Lombok annotation
3. Configure DB connection in resources/application.properties
4. Optional. Some initial author provided in resources/data.sql
5. Run
```
http://localhost:8080/swagger-ui/#/
```


