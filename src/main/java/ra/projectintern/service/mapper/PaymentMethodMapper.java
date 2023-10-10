package ra.projectintern.service.mapper;

import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.PaymentMethod;
import ra.projectintern.model.dto.request.PaymentMethodRequest;
import ra.projectintern.model.dto.response.PaymentMethodResponse;
import ra.projectintern.service.IGenericMapper;

@Component
public class PaymentMethodMapper implements IGenericMapper<PaymentMethod, PaymentMethodRequest, PaymentMethodResponse> {
    @Override
    public PaymentMethod toEntity(PaymentMethodRequest paymentMethodRequest) throws CustomException {
        return PaymentMethod.builder()
                .name(paymentMethodRequest.getName())
                .build();
    }

    @Override
    public PaymentMethodResponse toResponse(PaymentMethod paymentMethod) {
        return PaymentMethodResponse.builder()
                .id(paymentMethod.getId())
                .name(paymentMethod.getName())
                .build();
    }
}
