package com.foonk.Kindergarten_corporate_website.dto;

import lombok.Data;
import lombok.Value;
import org.thymeleaf.expression.Maps;

import java.util.*;

@Data
public class SubTaskEditDto {
List<SubTaskCreateEditDto> subTaskCreateEditDtos=new ArrayList<>(Arrays.asList(new SubTaskCreateEditDto(null, null, false),new SubTaskCreateEditDto(null, null, false),new SubTaskCreateEditDto(null, null, false), new SubTaskCreateEditDto(null, null, false),new SubTaskCreateEditDto(null, null, false)));
}
