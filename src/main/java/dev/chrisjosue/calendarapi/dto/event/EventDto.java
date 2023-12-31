package dev.chrisjosue.calendarapi.dto.event;

import dev.chrisjosue.calendarapi.utils.annotations.EndDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EndDate
public class EventDto {
    @NotBlank(message = "Title Field is required.")
    @Length(min = 1, message = "Title Field must be greater than 1.")
    private String title;
    private String notes;

    @NotNull(message = "Start Date Field is required.")
    private Date start;

    @NotNull(message = "End Date Field is required.")
    private Date end;
}
