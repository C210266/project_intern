package ra.projectintern.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.model.domain.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Long id;
    private String name;
    private String address;
    private String type;

    private float price;

    private String main_image;


    private List<String> images;

    private boolean status;


    private String host_name;

    private String title;

    private String description;

//    private List<String> reviews_name;

//    private List<String> favourites_name;

//    private List<String> bookings_name;

    private List<String> rooms_name;


    private List<String> convenient_names;

}
