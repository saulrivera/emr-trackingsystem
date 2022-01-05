package outlandh.emr.tracking.datainit;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import outlandh.emr.tracking.models.mongo.Beacon;
import outlandh.emr.tracking.models.mongo.BeaconType;
import outlandh.emr.tracking.models.mongo.Gateway;
import outlandh.emr.tracking.repositories.BeaconRepository;
import outlandh.emr.tracking.repositories.GatewayRepository;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInit {
    private final BeaconRepository _beaconRepository;
    private final GatewayRepository _gatewayRepository;

    @Bean
    public void beacons() {
        if (_beaconRepository.count() > 0) return;
        List.of(
                Beacon.builder().tag("B1").mac("AC233FA9BEBA").type(BeaconType.Patient).build(),
                Beacon.builder().tag("B2").mac("AC233FA9BED1").type(BeaconType.Patient).build(),
                Beacon.builder().tag("B3").mac("AC233FA9BEDD").type(BeaconType.Patient).build(),
                Beacon.builder().tag("B4").mac("AC233FA9BEE1").type(BeaconType.Patient).build(),
                Beacon.builder().tag("B5").mac("AC233FA9BEDE").type(BeaconType.Patient).build()
        ).forEach(_beaconRepository::save);
    }

    @Bean
    public void gateways() {
        if(_gatewayRepository.count() > 0) return;
        List.of(
            Gateway.builder().tag("BT1").mac("AC233FC0A86E").positionX(2).positionY(4).build()
        ).forEach(_gatewayRepository::save);
    }
}
