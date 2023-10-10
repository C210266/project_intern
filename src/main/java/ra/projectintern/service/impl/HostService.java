package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Booking;
import ra.projectintern.model.domain.Host;
import ra.projectintern.model.dto.request.HostRequest;
import ra.projectintern.model.dto.response.HostResponse;
import ra.projectintern.repository.IHostRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.HostMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostService implements IGenericService<HostResponse, HostRequest, Long> {
    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private IHostRepository hostRepository;


    @Override
    public List<HostResponse> findAll() {
        return hostRepository.findAll().stream()
                .map(host -> hostMapper.toResponse(host))
                .collect(Collectors.toList());
    }

    @Override
    public HostResponse findById(Long id) throws CustomException {
        return hostRepository.findById(id)
                .map(host -> hostMapper.toResponse(host))
                .orElseThrow(() -> new CustomException("Host not found"));
    }

    @Override
    public HostResponse save(HostRequest hostRequest)  {
        return hostMapper.toResponse(hostRepository.save(hostMapper.toEntity(hostRequest)));

    }

    @Override
    public HostResponse update(HostRequest hostRequest, Long id) throws CustomException {
        Host host = hostMapper.toEntity(hostRequest);
        host.setId(null);
        return hostMapper.toResponse(hostRepository.save(host));
    }

    @Override
    public HostResponse delete(Long id) throws CustomException {
        Optional<Host> host = hostRepository.findById(id);
        if (host.isPresent()) {
            hostRepository.deleteById(id);
            return hostMapper.toResponse(host.get());
        }
        return null;
    }
}
