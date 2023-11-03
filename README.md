# Test containers

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

### Run

To run this application you need to run `docker compose up`.

