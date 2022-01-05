package outlandh.emr.tracking.demons;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import outlandh.emr.tracking.models.mongo.AveragedLecture;
import outlandh.emr.tracking.models.mongo.Lecture;
import outlandh.emr.tracking.repositories.AveragedLectureRepository;
import outlandh.emr.tracking.repositories.LectureRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class AverageDemon {
    private static final long rate = 4000;
    private final LectureRepository _lectureRepository;
    private final AveragedLectureRepository _averagedLectureRepository;

    @Scheduled(fixedRate = AverageDemon.rate)
    public void start() {
        Date date = new Date();
        date.setTime(date.getTime() - AverageDemon.rate);
        List<Lecture> lectures = _lectureRepository.getLastSecondLectures(date);

        log.info("Averaging results from: {}", date);

        Map<String, List<Lecture>> lecturesByBeaconMac = lectures
                .stream()
                .collect(Collectors.groupingBy(Lecture::getBeaconMac));
        lecturesByBeaconMac.forEach((beaconMac, beaconMacLectures) -> {
            log.info("Filtering {} beacons for {}", beaconMacLectures.size(), beaconMac);
            Map<String, List<Lecture>> lecturesByGateway = beaconMacLectures
                    .stream()
                    .collect(Collectors.groupingBy(Lecture::getGatewayMac));
            lecturesByGateway.forEach((gatewayMac, gatewayBeaconLectures) -> {
                log.info("Filtering {} elements {} by {}", gatewayBeaconLectures.size(), beaconMac, gatewayMac);
                double rssi = gatewayBeaconLectures
                        .stream()
                        .mapToDouble(Lecture::getRssi)
                        .average()
                        .orElse(Double.NaN);
                AveragedLecture lecture = AveragedLecture
                        .builder()
                        .beaconMac(beaconMac)
                        .gatewayMac(gatewayMac)
                        .rssi(rssi)
                        .timestamp(Instant.now())
                        .build();
                _averagedLectureRepository.save(lecture);
            });
        });
    }
}
