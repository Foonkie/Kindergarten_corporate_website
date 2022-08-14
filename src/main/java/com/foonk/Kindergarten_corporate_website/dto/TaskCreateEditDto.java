package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Type;
import com.foonk.Kindergarten_corporate_website.validation.group.CreateAction;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class TaskCreateEditDto {

   private final List<SubTaskCreateEditDto> subTaskCreateEditDtos=new ArrayList(Arrays.asList(new SubTaskCreateEditDto("", null, false), new SubTaskCreateEditDto("", null, false), new SubTaskCreateEditDto("", null, false), new SubTaskCreateEditDto("", null, false), new SubTaskCreateEditDto("", null, false)));

    @Enumerated(EnumType.STRING)
    @NotBlank(groups = CreateAction.class)
    private final Type type;

    @NotBlank(groups = CreateAction.class)
    private final String task_header;

    private final LocalDateTime endTime;

    @NotBlank(groups = CreateAction.class)
    private final Long userId;

    public TaskCreateEditDto(@NotBlank(groups = CreateAction.class) Type type, @NotBlank(groups = CreateAction.class) String task_header, LocalDateTime endTime, @NotBlank(groups = CreateAction.class) Long userId) {
        this.type = type;
        this.task_header = task_header;
        this.endTime = endTime;
        this.userId = userId;
    }

    public void add(SubTaskCreateEditDto subTaskCreateEditDto){
        subTaskCreateEditDtos.add(subTaskCreateEditDto);
    }

    public List<SubTaskCreateEditDto> getSubTaskCreateEditDtos() {
        return this.subTaskCreateEditDtos;
    }

    public @NotBlank(groups = CreateAction.class) Type getType() {
        return this.type;
    }

    public @NotBlank(groups = CreateAction.class) String getTask_header() {
        return this.task_header;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public @NotBlank(groups = CreateAction.class) Long getUserId() {
        return this.userId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TaskCreateEditDto)) return false;
        final TaskCreateEditDto other = (TaskCreateEditDto) o;
        final Object this$subTaskCreateEditDtos = this.getSubTaskCreateEditDtos();
        final Object other$subTaskCreateEditDtos = other.getSubTaskCreateEditDtos();
        if (this$subTaskCreateEditDtos == null ? other$subTaskCreateEditDtos != null : !this$subTaskCreateEditDtos.equals(other$subTaskCreateEditDtos))
            return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$task_header = this.getTask_header();
        final Object other$task_header = other.getTask_header();
        if (this$task_header == null ? other$task_header != null : !this$task_header.equals(other$task_header))
            return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $subTaskCreateEditDtos = this.getSubTaskCreateEditDtos();
        result = result * PRIME + ($subTaskCreateEditDtos == null ? 43 : $subTaskCreateEditDtos.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $task_header = this.getTask_header();
        result = result * PRIME + ($task_header == null ? 43 : $task_header.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        return result;
    }

    public String toString() {
        return "TaskCreateEditDto(subTaskCreateEditDtos=" + this.getSubTaskCreateEditDtos() + ", type=" + this.getType() + ", task_header=" + this.getTask_header() + ", endTime=" + this.getEndTime() + ", userId=" + this.getUserId() + ")";
    }
}
