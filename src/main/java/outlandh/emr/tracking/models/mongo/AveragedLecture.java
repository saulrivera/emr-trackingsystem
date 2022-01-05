package outlandh.emr.tracking.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "AveragedLecture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AveragedLecture {
    private String beaconMac;
    private String gatewayMac;
    private double rssi;
    private Instant timestamp;
}
