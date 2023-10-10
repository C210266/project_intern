package ra.projectintern.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Booking = CartItem
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date checkIn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date checkOut;
    private int quantity;
    private double totalPrice;

    private Location location;


}
