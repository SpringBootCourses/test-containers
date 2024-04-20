# Test containers

[![codecov](https://codecov.io/gh/IlyaLisov/test-containers/graph/badge.svg?token=2TB4I260W6)](https://codecov.io/gh/SpringBootCourses/test-containers)

Testcontainers tests of Spring Boot application.

This application is a simple post service, that allows to create, get and delete
posts.

It uses Spring Boot 3.2.0, Postgres as database and Testcontainers for testing.

### Environments

You need to provide next variables in `.env` file.

* `HOST` - host with port of Postgres instance
* `POSTGRES_DB` - name of database
* `POSTGRES_USERNAME` - username of user
* `POSTGRES_PASSWORD` - password of user

You can use example `.env.example` file with some predefined environments.

### Run

To run this application you need to run `docker compose up`.