package outlandh.emr.tracking.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Gateways")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gateway {
    private String id;
    private String tag;
    @Indexed
    private String mac;
    private float positionX;
    private float positionY;
}
