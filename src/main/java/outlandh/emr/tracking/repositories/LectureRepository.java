package outlandh.emr.tracking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import outlandh.emr.tracking.models.mongo.Lecture;

import java.util.Date;
import java.util.List;

public interface LectureRepository extends MongoRepository<Lecture, String> {
    @Query(value = "{ 'timestamp' : { $gte: ?0 } }")
    List<Lecture> getLastSecondLectures(Date date);
}
