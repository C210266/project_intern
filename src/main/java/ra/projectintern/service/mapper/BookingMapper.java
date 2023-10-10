package ra.projectintern.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Booking;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.domain.Users;
import ra.projectintern.model.dto.request.BookingRequest;
import ra.projectintern.model.dto.response.BookingResponse;
import ra.projectintern.repository.ILocationRepository;
import ra.projectintern.repository.IUserRepository;
import ra.projectintern.service.IGenericMapper;

@Component
public class BookingMapper implements IGenericMapper<Booking, BookingRequest, BookingResponse> {
    @Autowired
    private ILocationRepository locationRepository;
    @Autowired
    private IUserRepository userRepository;


    @Override
    public Booking toEntity(BookingRequest bookingRequest) throws CustomException {
        Location location = locationRepository.findById(bookingRequest.getLocation_id())
                .orElseThrow(() -> new CustomException("Location not found"));

        return Booking.builder()
                .location(location)
                .checkIn(bookingRequest.getCheckIn())
                .checkOut(bookingRequest.getCheckOut())
//                .users(users)
                .quantity(bookingRequest.getQuantity())
//                .orderItem(bookingRequest.getOrderItem())
                .totalPrice(bookingRequest.getTotalPrice())
                .build();
    }

    @Override
    public BookingResponse toResponse(Booking booking) {
        long millisecondsPerDay = 24 * 60 * 60 * 1000; // Số mili giây trong 1 ngày
        long checkInTime = booking.getCheckIn().getTime();
        long checkOutTime = booking.getCheckOut().getTime();
        int quantity = (int) ((checkOutTime - checkInTime) / millisecondsPerDay);

        double totalPrice = quantity * booking.getLocation().getPrice();

        return BookingResponse.builder()
                .id(booking.getId())
                .location_name(booking.getLocation().getName())
//                .users_name(booking.getUsers().getUsername())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .quantity(booking.getQuantity())
                .totalPrice(totalPrice)
                .build();
    }
}
