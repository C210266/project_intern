package ra.projectintern.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Booking;
import ra.projectintern.model.domain.Location;
import ra.projectintern.model.dto.request.BookingRequest;
import ra.projectintern.model.dto.response.BookingResponse;
import ra.projectintern.repository.ILocationRepository;
import ra.projectintern.service.mapper.BookingMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private ILocationRepository locationRepository;

    private List<Booking> cartItemList = new ArrayList<>();

    public void clearCartList() {
        cartItemList = new ArrayList<>();
    }

    public List<BookingResponse> findAll() {
        return cartItemList.stream()
                .map(c -> {
                    try {
                        return bookingMapper.toResponse(c);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }


    public void save(Booking booking) throws CustomException {
        BookingResponse existingCartItem = findById(booking.getId());

//                Neu ko tim thay cartItem
        if (existingCartItem == null) {
            cartItemList.add(booking);
        } else {
            int index = cartItemList.indexOf(existingCartItem);
            if (index != -1) {
                cartItemList.set(index, booking);
            }
        }
    }

    public Long getNewId() {
        Long idmax = 0L;
        for (Booking c : cartItemList) {
            if (idmax < c.getId()) {
                idmax = c.getId();
            }
        }
        return idmax + 1;
    }

    public BookingResponse findById(Long id) {
        for (BookingResponse c : findAll()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    //Kiem tra trong Cart co Booking chứa Location trong Cart chưa?
    public Booking findByIdLocation(Long id) {
        for (Booking c : cartItemList) {
            if (c.getLocation().getId() == id) {
                return c;
            }
        }
        return null;
    }

    //    Check Date trong CartItem co trung hay ko
    public boolean existCheckDate(Date checkInOut) {
        for (Booking c : cartItemList) {
            if (c.getCheckIn().equals(checkInOut) || c.getCheckOut().equals(checkInOut)) {
                return true;
            }
        }
        return false;
    }

    public BookingResponse addCart(BookingRequest bookingRequest, Long idLocation) throws CustomException {
        Optional<Location> locationOptional = locationRepository.findById(idLocation);

        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();

            if (location.isStatus()) {
                Booking booking = findByIdLocation(idLocation);

                if (booking == null) {

                    Date checkIn = bookingRequest.getCheckIn();
                    Date checkOut = bookingRequest.getCheckOut();
                    long millisecondsPerDay = 24 * 60 * 60 * 1000; // Số mili giây trong 1 ngày
                    int quantity = (int) ((checkOut.getTime() - checkIn.getTime()) / millisecondsPerDay);

                    Booking newBooking = new Booking();
                    newBooking.setCheckIn(bookingRequest.getCheckIn());
                    newBooking.setCheckOut(bookingRequest.getCheckOut());
                    newBooking.setId(getNewId());
                    newBooking.setLocation(location);
                    newBooking.setTotalPrice(quantity * location.getPrice());
                    newBooking.setQuantity(1);
                    save(newBooking);
                    return bookingMapper.toResponse(newBooking);
                } else {
//                    if (!existCheckDate(booking.getCheckIn()) || !existCheckDate(booking.getCheckOut()) ||
//                            (!existCheckDate(booking.getCheckIn()) && !existCheckDate(booking.getCheckOut()))
//                    ) {
//
//                        Date checkIn = bookingRequest.getCheckIn();
//                        Date checkOut = bookingRequest.getCheckOut();
//                        long millisecondsPerDay = 24 * 60 * 60 * 1000; // Số mili giây trong 1 ngày
//                        int quantity = (int) ((checkOut.getTime() - checkIn.getTime()) / millisecondsPerDay);
//
//                        Booking newBooking1 = new Booking();
//                        newBooking1.setCheckIn(checkIn);
//                        newBooking1.setCheckOut(checkOut);
//                        newBooking1.setId(getNewId());
//                        newBooking1.setLocation(location);
//                        newBooking1.setTotalPrice(quantity * location.getPrice());
//                        newBooking1.setQuantity(1);
//                        save(newBooking1);
//                        return bookingMapper.toResponse(newBooking1);
//                    } else {
                    booking.setQuantity(booking.getQuantity() + 1);
                    save(booking);
                    return bookingMapper.toResponse(booking);
                }
            } else {
                throw new CustomException("Location is not available");
            }
        } else {
            throw new CustomException("Location not found");
        }

    }

    public void deleteById(Long id) throws CustomException {
        Booking booking = findByIdLocation(id);
        if (booking != null) {
            booking.setQuantity(booking.getQuantity() - 1);
            if (booking.getQuantity() <= 0) {
                // Xóa sản phẩm khỏi giỏ hàng nếu quantity <= 0
                cartItemList.remove(booking);
            } else {
                save(booking); // Chỉ lưu lại nếu quantity > 0
            }
        } else {
            throw new CustomException("Booking not found");
        }
    }


}
