package com.foonk.Kindergarten_corporate_website.validation.impl;

import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.validation.EmptySubTaskList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SubTaskListValidator implements ConstraintValidator<EmptySubTaskList, List<SubTaskCreateEditDto>> {
    @Override
    public boolean isValid(List<SubTaskCreateEditDto> subTaskCreateEditDtos, ConstraintValidatorContext constraintValidatorContext) {
        return subTaskCreateEditDtos.stream().map(SubTaskCreateEditDto::getSubtask).anyMatch(s -> !s.equals(""));
    }
}
