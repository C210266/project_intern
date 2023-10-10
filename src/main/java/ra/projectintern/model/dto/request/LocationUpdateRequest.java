package ra.projectintern.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.projectintern.model.domain.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationUpdateRequest {
    @NotBlank(message = "Location name cannot be blank")
    @NotEmpty(message = "Location name cannot be empty!!!")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @NotEmpty(message = "Address name cannot be empty!!!")
    private String address;

    @NotBlank(message = "Type cannot be blank")
    @NotEmpty(message = "Type name cannot be empty!!!")
    private String type;

    private float price;


    private boolean status;

    private Long host_id;

    private String title;
    private String description;

//    private List<Review> reviews;

//    private List<Favourite> favourites;

//    private List<Booking> bookings;

    private List<Long> rooms;

    private List<Long> convenients;
}
