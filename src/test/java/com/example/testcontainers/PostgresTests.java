package com.example.testcontainers;

import com.example.testcontainers.model.Post;
import com.example.testcontainers.repository.PostRepository;
import com.example.testcontainers.web.dto.PostDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@TestConfiguration(proxyBeanMethods = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostgresTests {

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15.1-alpine")
    );

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        postRepository.deleteAll();
    }

    @Test
    public void testEmptyGetAll() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/posts")
                .then()
                .statusCode(
                        HttpStatus.OK.value()
                )
                .body(
                        ".",
                        Matchers.empty()
                );
    }

    @Test
    public void testNotEmptyGetAll() {
        List<Post> posts = List.of(
                new Post("First title", "First text"),
                new Post("Second title", "Second text")
        );
        postRepository.saveAll(posts);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/posts")
                .then()
                .statusCode(
                        HttpStatus.OK.value()
                )
                .body(
                        ".",
                        Matchers.hasSize(2)
                );
    }

    @Test
    public void testEmptyGetById() {
        long id = 1L;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/posts/{id}", id)
                .then()
                .statusCode(
                        HttpStatus.NOT_FOUND.value()
                )
                .body(
                        "message",
                        Matchers.containsString("not found")
                );
    }

    @Test
    public void testNotEmptyGetById() {
        Post post = new Post("First title", "First text");
        post = postRepository.save(post);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/posts/{id}", post.getId())
                .then()
                .statusCode(
                        HttpStatus.OK.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(post.getId().intValue())
                )
                .body(
                        "title",
                        Matchers.equalTo(post.getTitle())
                )
                .body(
                        "text",
                        Matchers.equalTo(post.getText())
                );
    }

    @Test
    public void testViewsAmountIncrement() {
        Post post = new Post("First title", "First text");
        post = postRepository.save(post);
        int counter = 5;

        for (int i = 0; i < counter; i++) {
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/posts/{id}", post.getId())
                    .then()
                    .statusCode(
                            HttpStatus.OK.value()
                    )
                    .body(
                            "viewsAmount",
                            Matchers.equalTo(i)
                    );
        }
    }

    @Test
    public void testCreate() {
        PostDto post = new PostDto("First title", "First text");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("/api/v1/posts")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.notNullValue()
                )
                .body(
                        "viewsAmount",
                        Matchers.equalTo(0)
                );
    }

    @Test
    public void testIncorrectCreate() {
        PostDto post = new PostDto("First title", null);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("/api/v1/posts")
                .then()
                .statusCode(
                        HttpStatus.BAD_REQUEST.value()
                )
                .body(
                        "message",
                        Matchers.containsString("not null")
                );
    }

    @Test
    public void testDelete() {
        long id = 1L;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/posts/{id}", id)
                .then()
                .statusCode(
                        HttpStatus.NO_CONTENT.value()
                );
    }

}
