package ra.projectintern.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.model.domain.Location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HostRequest {

    private String name;
    private boolean status;
    private String phoneNumber;

//    private List<Location> locations = new ArrayList<>();
}
