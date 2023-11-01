package dev.chrisjosue.calendarapi.dto.event;

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
public class EventDto {
    @NotBlank(message = "Title is required.")
    @Length(min = 1, message = "Name must be greater than 1.")
    private String title;
    private String notes;

    @NotNull(message = "Start Date is required.")
    @NotBlank(message = "Start Date is required.")
    private Date start;

    @NotBlank(message = "Title is required.")
    @NotNull(message = "Start Date is required.")
    /// TODO: validate end must be greater than start
    private Date end;
}
