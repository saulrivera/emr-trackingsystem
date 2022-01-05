package outlandh.emr.tracking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import outlandh.emr.tracking.models.mongo.AveragedLecture;

public interface AveragedLectureRepository extends MongoRepository<AveragedLecture, String> {
}
