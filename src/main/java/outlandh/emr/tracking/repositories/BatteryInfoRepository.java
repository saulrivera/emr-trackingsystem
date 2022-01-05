package outlandh.emr.tracking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import outlandh.emr.tracking.models.mongo.BatteryInfo;

public interface BatteryInfoRepository extends MongoRepository<BatteryInfo, String> {
    @Query(value = "{ mac: ?0 }", exists = true)
    boolean existsByMac(String mac);
    BatteryInfo findByMac(String mac);
}
