package ra.projectintern.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.domain.Users;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String description;
    private int star;

    private String users_name;

    private String location_name;
}
