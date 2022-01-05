package outlandh.emr.tracking.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "Lectures")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecture {
    private String id;
    @Indexed
    private String gatewayMac;
    @Indexed
    private String beaconMac;
    private float rssi;
    private Instant timestamp;
}
