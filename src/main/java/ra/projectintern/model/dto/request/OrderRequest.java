package ra.projectintern.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String fullName;
    private String email;
    private String phoneNumber;
    private double totalPrice;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date order_at;
    private boolean status;


    private List<Long> coupons_id;

    private Long paymentMethod_id;
}
