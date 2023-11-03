package com.example.testcontainers.web.mapper;

import com.example.testcontainers.model.Post;
import com.example.testcontainers.web.dto.PostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper
        extends Mappable<Post, PostDto> {
}
