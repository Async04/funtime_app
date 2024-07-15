package com.example.funtime_app.mappers;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.entity.Category;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface CategoryMapper {
    @Mapping(source = "attachmentId", target = "attachment.id")
    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(source = "attachment.id", target = "attachmentId")
    CategoryDTO toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
            @Mapping(source = "attachmentId", target = "attachment.id")
    Category partialUpdate(CategoryDTO categoryDTO, @MappingTarget Category category);
}