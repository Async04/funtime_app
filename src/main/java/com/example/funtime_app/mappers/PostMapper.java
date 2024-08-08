package com.example.funtime_app.mappers;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post toEntity(PostDTO postDto);

    PostDTO toDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post partialUpdate(PostDTO postDto, @MappingTarget Post post);
}