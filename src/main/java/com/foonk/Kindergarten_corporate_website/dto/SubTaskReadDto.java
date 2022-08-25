package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Task;
import liquibase.pro.packaged.B;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;

/*Dto для операций чтения сущности SubTask из базы.*/
@Value
    public class SubTaskReadDto {
        private Long id;
        @NotBlank
        private String subtask;
        @NotBlank
        private Long taskId;

        private Boolean status;

       public static class SubTaskReadDtoComparator implements Comparator<SubTaskReadDto> {

            public int compare(SubTaskReadDto a, SubTaskReadDto b){

                return a.getId().compareTo(b.getId());
            }
        }
    }

