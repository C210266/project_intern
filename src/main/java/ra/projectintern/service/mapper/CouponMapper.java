package ra.projectintern.service.mapper;


import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Coupon;
import ra.projectintern.model.dto.request.CouponRequest;
import ra.projectintern.model.dto.response.CouponResponse;
import ra.projectintern.service.IGenericMapper;

@Component
public class CouponMapper implements IGenericMapper<Coupon, CouponRequest, CouponResponse> {
    @Override
    public Coupon toEntity(CouponRequest couponRequest) throws CustomException {

        return Coupon.builder()
                .description(couponRequest.getDescription())
                .name(couponRequest.getName())
                .status(couponRequest.isStatus())
                .startDate(couponRequest.getStartDate())
                .endDate(couponRequest.getEndDate())
                .stock(couponRequest.getStock())
                .promotion(couponRequest.getPromotion())
                .build();
    }

    @Override
    public CouponResponse toResponse(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .description(coupon.getDescription())
                .name(coupon.getName())
                .status(coupon.isStatus())
                .startDate(coupon.getStartDate())
                .endDate(coupon.getEndDate())
                .stock(coupon.getStock())
                .promotion(coupon.getPromotion())
//                .orderItem(coupon.getOrderItem())
                .build();
    }
}
