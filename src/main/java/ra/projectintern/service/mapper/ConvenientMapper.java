package ra.projectintern.service.mapper;

import org.springframework.stereotype.Component;
import ra.projectintern.model.domain.Convenient;
import ra.projectintern.model.dto.request.ConvenientRequest;
import ra.projectintern.model.dto.response.ConvenientResponse;
import ra.projectintern.service.IGenericMapper;

@Component
public class ConvenientMapper implements IGenericMapper<Convenient, ConvenientRequest, ConvenientResponse> {
    @Override
    public Convenient toEntity(ConvenientRequest convenientRequest) {
        return Convenient.builder()
                .name(convenientRequest.getName())
                .build();
    }



    @Override
    public ConvenientResponse toResponse(Convenient convenient) {
        return ConvenientResponse.builder()
                .id(convenient.getId())
                .name(convenient.getName())
                .build();
    }
}
