package com.example.testcontainers.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostDto {

    @Null
    private Long id;

    @Length(
            min = 5,
            max = 1024,
            message = "Title length must be between {min} and {max}."
    )
    @NotNull(message = "Title must be not null.")
    private String title;

    @Length(
            min = 5,
            max = 16384,
            message = "Text length must be between {min} and {max}."
    )
    @NotNull(message = "Text must be not null.")
    private String text;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long viewsAmount;

    public PostDto(String title, String text) {
        this.title = title;
        this.text = text;
    }

}
