package dev.chrisjosue.calendarapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventEntity {
    @Id
    private String id;
    @NotBlank(message = "Title is required.")
    @Length(min = 1, message = "Name must be greater than 1.")
    private String title;
    private String notes;

    @NotNull(message = "Start Date is required.")
    @NotBlank(message = "Start Date is required.")
    private Date start;

    @NotBlank(message = "Title is required.")
    @NotNull(message = "Start Date is required.")
    private Date end;

    @DBRef
    private UserEntity user;
}
