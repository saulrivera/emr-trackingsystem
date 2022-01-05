package outlandh.emr.tracking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import outlandh.emr.tracking.models.mongo.Gateway;

public interface GatewayRepository extends MongoRepository<Gateway, String> {
}
