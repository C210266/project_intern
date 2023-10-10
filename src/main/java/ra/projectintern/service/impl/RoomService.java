package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Host;
import ra.projectintern.model.domain.Room;
import ra.projectintern.model.dto.request.RoomRequest;
import ra.projectintern.model.dto.response.RoomResponse;
import ra.projectintern.repository.IRoomRepository;
import ra.projectintern.service.IGenericService;
import ra.projectintern.service.mapper.RoomMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IGenericService<RoomResponse, RoomRequest, Long> {
    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<RoomResponse> findAll() {
        return roomRepository.findAll().stream()
                .map(p -> roomMapper.toResponse(p)).collect(Collectors.toList());
    }

    @Override
    public RoomResponse findById(Long id) throws CustomException {
        return roomRepository.findById(id)
                .map(host -> roomMapper.toResponse(host))
                .orElseThrow(() -> new CustomException("Host not found"));
    }

    @Override
    public RoomResponse save(RoomRequest roomRequest) throws CustomException {
        return roomMapper.toResponse(roomRepository.save(roomMapper.toEntity(roomRequest)));
    }

    @Override
    public RoomResponse update(RoomRequest roomRequest, Long id) throws CustomException {
        Room room = roomMapper.toEntity(roomRequest);
        room.setId(null);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse delete(Long id) throws CustomException {
        Optional<Room> room1 = roomRepository.findById(id);
        if (room1.isPresent()) {
            roomRepository.deleteById(id);
            return roomMapper.toResponse(room1.get());
        }
        return null;
    }
}
