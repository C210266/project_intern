package ra.projectintern.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.model.domain.Location;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageLocationRequest {

    @JsonIgnore
    private String image;

    private Location location;
}
