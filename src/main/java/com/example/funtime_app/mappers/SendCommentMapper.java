package com.example.funtime_app.mappers;

import com.example.funtime_app.dto.SendCommentDto;
import com.example.funtime_app.entity.Comment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SendCommentMapper {
    Comment toEntity(SendCommentDto sendCommentDto);

    SendCommentDto toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(SendCommentDto sendCommentDto, @MappingTarget Comment comment);
}