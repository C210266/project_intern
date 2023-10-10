package ra.projectintern.service.mapper;

import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Host;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.dto.request.HostRequest;
import ra.projectintern.model.dto.response.HostResponse;
import ra.projectintern.service.IGenericMapper;

import java.util.stream.Collectors;

@Component
public class HostMapper implements IGenericMapper<Host, HostRequest, HostResponse> {
    @Override
    public Host toEntity(HostRequest hostRequest) {
        return Host.builder()
//                .locations(hostRequest.getLocations())
                .name(hostRequest.getName())
                .phoneNumber(hostRequest.getPhoneNumber())
                .status(hostRequest.isStatus())
                .build();
    }

    @Override
    public HostResponse toResponse(Host host)  {
        return HostResponse.builder()
                .id(host.getId())
                .name(host.getName())
//                .locations(host.getLocations().stream()
//                        .map(Location::getName)
//                        .collect(Collectors.toList()))
                .phoneNumber(host.getPhoneNumber())
                .status(host.isStatus())
                .build();
    }
}
