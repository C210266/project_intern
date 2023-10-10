package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Coupon;
import ra.projectintern.model.domain.Orders;
import ra.projectintern.model.domain.PaymentMethod;
import ra.projectintern.model.dto.request.OrderRequest;
import ra.projectintern.model.dto.response.BookingResponse;
import ra.projectintern.model.dto.response.OrderResponse;
import ra.projectintern.repository.ICouponRepository;
import ra.projectintern.repository.IOrderRepository;
import ra.projectintern.repository.IPaymentMethodRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.OrderMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IGenericService<OrderResponse, OrderRequest, Long> {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ICouponRepository couponRepository;
    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;

    @Override
    public List<OrderResponse> findAll() {
        return null;
    }

    @Override
    public OrderResponse findById(Long aLong) throws CustomException {
        return null;
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) throws CustomException {
        return null;
    }

    @Override
    public OrderResponse update(OrderRequest orderRequest, Long id) throws CustomException {
        return null;
    }

    @Override
    public OrderResponse delete(Long aLong) throws CustomException {
        return null;
    }

    public OrderResponse order(OrderRequest orderRequest, List<Long> couponId, Long paymentId) throws CustomException {
//        Optional<Users> u = userRepository.findByUsername(String.valueOf(user));
        PaymentMethod paymentMethod = findPaymentById(paymentId);

        List<Coupon> coupons = couponRepository.findAllById(couponId);

        List<BookingResponse> cartItemList = bookingService.findAll();
        if (cartItemList.isEmpty()) {
            throw new CustomException("Cart isEmpty!!!");
        }
        // Tạo một đơn hàng
        double totalPrice = cartItemList.stream()
                .mapToDouble(BookingResponse::getTotalPrice) // Chuyển đổi từ CartItemResponse thành giá tiền (double)
                .sum();
        double totalPriceNew = totalPrice - (totalPrice * coupons.stream()
                .mapToDouble(Coupon::getPromotion).sum() / 100);
        Orders order = Orders.builder()
                .order_at(new Date())
                .paymentMethod(paymentMethod)
                .totalPrice(totalPriceNew)
                .coupons(coupons)
                .status(true)
                .email(orderRequest.getEmail())
                .fullName(orderRequest.getFullName())
                .phoneNumber(orderRequest.getPhoneNumber())
                .build();
        orderRepository.save(order);

//        Xet sau
//        coupon.get().setStatus(false);
//        coupon.get().setStock(0);
//        couponRepository.save(coupon.get());


        return orderMapper.toResponse(order);
    }

    public PaymentMethod findPaymentById(Long paymentId) throws CustomException {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException("Payment not found"));
        return paymentMethod;
    }
}
