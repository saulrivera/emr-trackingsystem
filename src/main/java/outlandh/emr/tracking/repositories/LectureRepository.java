package outlandh.emr.tracking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import outlandh.emr.tracking.models.mongo.Lecture;

public interface LectureRepository extends MongoRepository<Lecture, String> {
}
