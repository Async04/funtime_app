package com.example.funtime_app.mappers;

import com.example.funtime_app.dto.SendCommentDTO;
import com.example.funtime_app.entity.Comment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SendCommentMapper {
    Comment toEntity(SendCommentDTO sendCommentDto);

    SendCommentDTO toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(SendCommentDTO sendCommentDto, @MappingTarget Comment comment);
}