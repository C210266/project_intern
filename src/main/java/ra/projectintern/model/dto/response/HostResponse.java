package ra.projectintern.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.projectintern.model.domain.Location;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostResponse {
    private Long id;
    private String name;
    private boolean status;
    private String phoneNumber;

//    private List<String> locations = new ArrayList<>();
}
