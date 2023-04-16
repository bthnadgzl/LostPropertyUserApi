
# LostPropertyUserApi


User api written in spring boot and spring security,
where users' registration, login and token operations are     
performed for lost property website.

In order to prevent non-student use and to minimize security vulnerabilities, the verification code is sent to the school email of the student during the registration process. Thus, it is proven that he/she is a student. 




## Features

- Email verification.
- Only accepts students from defined universities.
- Authentication tokens with Spring Boot JWT.
- Provides token to another microservices.

