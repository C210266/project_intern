package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Convenient;
import ra.projectintern.model.domain.Host;
import ra.projectintern.model.dto.request.ConvenientRequest;
import ra.projectintern.model.dto.response.ConvenientResponse;
import ra.projectintern.repository.IConvenientRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.ConvenientMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConvenientService implements IGenericService<ConvenientResponse, ConvenientRequest, Long> {
    @Autowired
    private IConvenientRepository convenientRepository;
    @Autowired
    private ConvenientMapper convenientMapper;

    @Override
    public List<ConvenientResponse> findAll() {
        return convenientRepository.findAll().stream()
                .map(host -> convenientMapper.toResponse(host))
                .collect(Collectors.toList());
    }

    @Override
    public ConvenientResponse findById(Long id) throws CustomException {
        return convenientRepository.findById(id)
                .map(host -> convenientMapper.toResponse(host))
                .orElseThrow(() -> new CustomException("Convenient not found"));
    }

    @Override
    public ConvenientResponse save(ConvenientRequest convenientRequest) throws CustomException {
        return convenientMapper.toResponse(convenientRepository.save(convenientMapper.toEntity(convenientRequest)));
    }

    @Override
    public ConvenientResponse update(ConvenientRequest convenientRequest, Long id) throws CustomException {
        Convenient convenient = convenientMapper.toEntity(convenientRequest);
        convenient.setId(null);
        return convenientMapper.toResponse(convenientRepository.save(convenient));
    }

    @Override
    public ConvenientResponse delete(Long id) throws CustomException {
        Optional<Convenient> convenient = convenientRepository.findById(id);
        if (convenient.isPresent()) {
            convenientRepository.deleteById(id);
            return convenientMapper.toResponse(convenient.get());
        }
        return null;
    }
}
