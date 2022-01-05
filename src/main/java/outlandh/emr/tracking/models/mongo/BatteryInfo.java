package outlandh.emr.tracking.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "BatteryInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatteryInfo {
    private String id;
    @Indexed
    private String mac;
    private int level;
    private Instant timestamp;
}
