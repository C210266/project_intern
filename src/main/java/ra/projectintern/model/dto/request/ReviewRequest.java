package ra.projectintern.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotBlank(message = "Content cannot be null or empty")
    private String description;

    @Min(value = 1, message = "Star must be at least 1")
    @Max(value = 5, message = "Star must be at most 5")
    private int star;

    @NotNull(message = "User ID cannot be null")
    private Long user_id;

    @NotNull(message = "Location ID cannot be null")
    private Long location_id;
}
