package ra.projectintern.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String type;

    private float price;

    private String main_image;

    private String title;
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.ALL)
    private List<ImageLocation> images;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host host;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favourite> favourites;

//    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Booking> bookings;


    @ManyToMany
//            (mappedBy = "locations")
    private List<Room> rooms;

    @ManyToMany
//            (mappedBy = "locations")
    private List<Convenient> convenients;


}
