package ra.projectintern.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private double totalPrice;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date order_at;
    private boolean status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orders")
    private List<Coupon> coupons;

    // *
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentMethod paymentMethod;

}
