package ra.projectintern.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date checkIn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date checkOut;
    private int quantity;
    private double totalPrice;

//    @NotNull(message = "User not null")
//    private Long users_id;

    @NotNull(message = "Location not null")
    private Long location_id;

//    private OrderItem orderItem;
}
