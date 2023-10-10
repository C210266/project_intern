package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Host;
import ra.projectintern.model.domain.PaymentMethod;
import ra.projectintern.model.dto.request.HostRequest;
import ra.projectintern.model.dto.request.PaymentMethodRequest;
import ra.projectintern.model.dto.response.PaymentMethodResponse;
import ra.projectintern.repository.IHostRepository;
import ra.projectintern.repository.IPaymentMethodRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.PaymentMethodMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService implements IGenericService<PaymentMethodResponse, PaymentMethodRequest,Long> {
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;


    @Override
    public List<PaymentMethodResponse> findAll() {
        return paymentMethodRepository.findAll().stream()
                .map(host -> paymentMethodMapper.toResponse(host))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodResponse findById(Long id) throws CustomException {
        return paymentMethodRepository.findById(id)
                .map(host -> paymentMethodMapper.toResponse(host))
                .orElseThrow(() -> new CustomException("Payment Method not found"));
    }

    @Override
    public PaymentMethodResponse save(PaymentMethodRequest request) throws CustomException {
        return paymentMethodMapper.toResponse(paymentMethodRepository.save(paymentMethodMapper.toEntity(request)));

    }

    @Override
    public PaymentMethodResponse update(PaymentMethodRequest request, Long id) throws CustomException {
        PaymentMethod paymentmethod = paymentMethodMapper.toEntity(request);
        paymentmethod.setId(null);
        return paymentMethodMapper.toResponse(paymentMethodRepository.save(paymentmethod));
    }

    @Override
    public PaymentMethodResponse delete(Long id) throws CustomException {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        if (paymentMethod.isPresent()) {
            paymentMethodRepository.deleteById(id);
            return paymentMethodMapper.toResponse(paymentMethod.get());
        }
        return null;
    }
}
