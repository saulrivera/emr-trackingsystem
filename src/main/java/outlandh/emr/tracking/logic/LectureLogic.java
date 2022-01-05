package outlandh.emr.tracking.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import outlandh.emr.tracking.models.Lecture;
import outlandh.emr.tracking.models.LectureType;
import outlandh.emr.tracking.models.mongo.BatteryInfo;
import outlandh.emr.tracking.repositories.BatteryInfoRepository;
import outlandh.emr.tracking.repositories.BeaconRepository;
import outlandh.emr.tracking.repositories.LectureRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class LectureLogic {
    private final BeaconRepository _beaconRepository;
    private final BatteryInfoRepository _batteryInfoRepository;
    private final LectureRepository _lectureRepository;

    public void handleLectures(List<Lecture> lectures) {
        processBeacons(lectures);
        processBatteryInfo(lectures);
    }

    private void processBeacons(List<Lecture> lectures) {
        Lecture gateway = getGatewayFromLectures(lectures);
        if (gateway == null) {
            log.error("Gateway no present. Cannot infer source.");
            return;
        }

        List<Lecture> beacons = getBeaconsFromLectures(lectures);
        beacons
                .stream()
                .filter(beacon -> _beaconRepository.existsByMac(beacon.getMac()))
                .forEach(beacon -> {
                    outlandh.emr.tracking.models.mongo.Lecture lecture = outlandh.emr.tracking.models.mongo.Lecture
                            .builder()
                            .gatewayMac(gateway.getMac())
                            .beaconMac(beacon.getMac())
                            .rssi(beacon.getRssi())
                            .timestamp(Instant.now())
                            .build();
                    var saved = _lectureRepository.save(lecture);
                    log.info(saved.toString());
                });
    }

    private void processBatteryInfo(List<Lecture> lectures) {
        List<Lecture> batteryInfoLectures = getBatteryInfoFromLectures(lectures);
        List<BatteryInfo> batteryInfo = buildBatteryInfo(batteryInfoLectures);

        batteryInfo
                .stream()
                .filter(battery -> _beaconRepository.existsByMac(battery.getMac()))
                .forEach(battery -> {
                    if (_batteryInfoRepository.existsByMac(battery.getMac())) {
                        BatteryInfo saved = _batteryInfoRepository.findByMac(battery.getMac());
                        saved.setLevel(battery.getLevel());
                        saved.setTimestamp(battery.getTimestamp());
                        _batteryInfoRepository.save(saved);
                        log.info(saved.toString());
                    } else {
                        BatteryInfo saved = _batteryInfoRepository.save(battery);
                        log.info(saved.toString());
                    }
                });
    }

    private Lecture getGatewayFromLectures(List<Lecture> lectures) {
        Optional<Lecture> gatewayOptional = lectures
                .stream()
                .filter(lecture -> lecture.getType().equals(LectureType.Gateway))
                .findFirst();
        if (gatewayOptional.isPresent()) {
            return gatewayOptional.get();
        } else {
            return null;
        }
    }

    private List<Lecture> getBeaconsFromLectures(List<Lecture> lectures) {
        return lectures
                .stream()
                .filter(lecture -> lecture.getType().equals(LectureType.iBeacon))
                .collect(Collectors.toList());
    }

    private List<Lecture> getBatteryInfoFromLectures(List<Lecture> lectures) {
        return lectures
                .stream()
                .filter(lecture ->
                        lecture.getType().equals(LectureType.Unknown) &&
                                lecture.getRawData() != null &&
                                !lecture.getRawData().isEmpty() &&
                                lecture.getRawData().length() > 40)
                .collect(Collectors.toList());
    }

    private List<BatteryInfo> buildBatteryInfo(List<Lecture> lectures) {
        return lectures
                .stream()
                .map(lecture -> {
                    int level = Integer.parseInt(lecture.getRawData().substring(26, 28), 16);
                    return  BatteryInfo
                            .builder()
                            .mac(lecture.getMac())
                            .level(level)
                            .timestamp(Instant.now())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
