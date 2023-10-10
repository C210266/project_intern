package ra.projectintern.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Coupon;

import ra.projectintern.model.domain.Orders;
import ra.projectintern.model.domain.PaymentMethod;
import ra.projectintern.model.dto.request.OrderRequest;
import ra.projectintern.model.dto.response.OrderResponse;
import ra.projectintern.repository.ICouponRepository;
import ra.projectintern.repository.IPaymentMethodRepository;
import ra.projectintern.service.IGenericMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper implements IGenericMapper<Orders, OrderRequest, OrderResponse> {
    @Autowired
    private ICouponRepository couponRepository;

    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;

    @Override
    public Orders toEntity(OrderRequest orderRequest) throws CustomException {
        List<Coupon> coupons = couponRepository.findAllById(orderRequest.getCoupons_id());
        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequest.getPaymentMethod_id())
                .orElseThrow(() -> new CustomException("Payment cant found"));

        return Orders.builder()
                .email(orderRequest.getEmail())
                .fullName(orderRequest.getFullName())
                .order_at(new Date())
                .coupons(coupons)
                .phoneNumber(orderRequest.getPhoneNumber())
                .status(orderRequest.isStatus())
                .paymentMethod(paymentMethod)

                .totalPrice(orderRequest.getTotalPrice())

                .build();
    }

    @Override
    public OrderResponse toResponse(Orders orders) {

        return OrderResponse.builder()
                .email(orders.getEmail())
                .fullName(orders.getFullName())
                .order_at(new Date())
                .coupons_name(orders.getCoupons().stream().map(Coupon::getName)
                        .collect(Collectors.toList()))
                .phoneNumber(orders.getPhoneNumber())
                .status(orders.isStatus())
                .paymentMethod_name(orders.getPaymentMethod().getName())

                .totalPrice(orders.getTotalPrice())

                .build();
    }
}
