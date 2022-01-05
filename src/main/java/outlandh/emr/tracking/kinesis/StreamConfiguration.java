package outlandh.emr.tracking.kinesis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import outlandh.emr.tracking.logic.LectureLogic;
import outlandh.emr.tracking.models.Lecture;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StreamConfiguration {
    private final LectureLogic _lectureLogic;

    @Bean
    public Consumer<List<Lecture>> processEvent() {
        return _lectureLogic::handleLectures;
    }
}
