package outlandh.emr.tracking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import outlandh.emr.tracking.models.mongo.Beacon;

public interface BeaconRepository extends MongoRepository<Beacon, String> {
    @Query(value = "{ mac: ?0 }", exists = true)
    boolean existsByMac(String mac);
}
