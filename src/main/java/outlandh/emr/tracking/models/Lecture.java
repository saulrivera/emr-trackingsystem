package outlandh.emr.tracking.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class Lecture {

    // General data shared by all beacons
    private String mac;
    private Instant timestamp;
    private LectureType type;

    // Data from beacons
    private String ibeaconUuid;
    private int rssi;
    private int ibeaconTxPower;

    // Data from battery info
    private String rawData;
}
