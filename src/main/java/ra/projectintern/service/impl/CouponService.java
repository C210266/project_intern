package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Coupon;
import ra.projectintern.model.dto.request.ConvenientRequest;
import ra.projectintern.model.dto.request.CouponRequest;
import ra.projectintern.model.dto.response.CouponResponse;

import ra.projectintern.repository.ICouponRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.ConvenientMapper;
import ra.projectintern.service.mapper.CouponMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponService implements IGenericService<CouponResponse, CouponRequest,Long> {
    @Autowired
    private ICouponRepository couponRepository;
    @Autowired
    private CouponMapper couponMapper;

    @Override
    public List<CouponResponse> findAll() {
        return couponRepository.findAll().stream()
                .map(host -> couponMapper.toResponse(host))
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponse findById(Long id) throws CustomException {
        return couponRepository.findById(id)
                .map(host -> couponMapper.toResponse(host))
                .orElseThrow(() -> new CustomException("Coupon not found"));
    }

    @Override
    public CouponResponse save(CouponRequest couponRequest) throws CustomException {
        return couponMapper.toResponse(couponRepository.save(couponMapper.toEntity(couponRequest)));
    }

    @Override
    public CouponResponse update(CouponRequest couponRequest, Long id) throws CustomException {
        Coupon coupon = couponMapper.toEntity(couponRequest);
        coupon.setId(null);
        return couponMapper.toResponse(couponRepository.save(coupon));
    }

    @Override
    public CouponResponse delete(Long id) throws CustomException {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isPresent()) {
            couponRepository.deleteById(id);
            return couponMapper.toResponse(coupon.get());
        }
        return null;
    }
}
