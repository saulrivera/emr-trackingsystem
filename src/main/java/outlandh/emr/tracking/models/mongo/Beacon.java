package outlandh.emr.tracking.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Beacons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Beacon {
    private String id;
    private String tag;
    @Indexed
    private String mac;
    private BeaconType type;
}
