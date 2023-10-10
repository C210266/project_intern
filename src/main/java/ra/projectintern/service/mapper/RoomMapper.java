package ra.projectintern.service.mapper;

import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Room;
import ra.projectintern.model.dto.request.RoomRequest;
import ra.projectintern.model.dto.response.RoomResponse;
import ra.projectintern.service.IGenericMapper;

@Component
public class RoomMapper implements IGenericMapper<Room, RoomRequest, RoomResponse> {
    @Override
    public Room toEntity(RoomRequest roomRequest)  {
        return Room.builder()
                .name(roomRequest.getName())
                .status(roomRequest.isStatus())
                .quantity(roomRequest.getQuantity())
                .build();
    }

    @Override
    public RoomResponse toResponse(Room room)  {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .quantity(room.getQuantity())
                .status(room.isStatus())
                .build();
    }
}
