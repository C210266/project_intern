package ra.projectintern.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date checkIn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date checkOut;

    private int quantity;
    private double totalPrice;

//    private String users_name;

    private String location_name;

}
