package seproject.yudelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.LocationDTO;
import seproject.yudelivery.entity.RiderEntity;
import seproject.yudelivery.repository.RiderRepository;

import java.util.Optional;

@Service
public class RiderService {

    private final RiderRepository riderRepository;

    @Value("${store.latitude}")
    private double storeLatitude;

    @Value("${store.longitude}")
    private double storeLongitude;

    @Autowired
    public RiderService(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    // 나머지 메서드들은 동일하게 유지합니다.
    public Optional<RiderEntity> getRiderByOrderId(Long orderId) {
        return riderRepository.findByCustomerId(orderId);
    }

    public void completeDelivery(Long riderId) {
        Optional<RiderEntity> optionalRider = riderRepository.findById(riderId);
        if (optionalRider.isPresent()) {
            RiderEntity rider = optionalRider.get();
            rider.setDeliveryStatus("DELIVERED");
            riderRepository.save(rider);
        }
    }

    public void updateRiderLocation(Long riderId, LocationDTO locationDTO) {
        Optional<RiderEntity> optionalRider = riderRepository.findById(riderId);
        if (optionalRider.isPresent()) {
            RiderEntity rider = optionalRider.get();
            rider.setLatitude(locationDTO.getLatitude());
            rider.setLongitude(locationDTO.getLongitude());
            riderRepository.save(rider);
            checkAndCancelOrdersOutOfRange(rider);
        }
    }

    public void cancelDelivery(Long riderId) {
        Optional<RiderEntity> optionalRider = riderRepository.findById(riderId);
        if (optionalRider.isPresent()) {
            RiderEntity rider = optionalRider.get();
            rider.setDeliveryStatus("CANCELLED");
            riderRepository.save(rider);
            // 여기에서 추가적인 주문 취소 로직을 수행할 수 있습니다.
        }
    }

    private void checkAndCancelOrdersOutOfRange(RiderEntity rider) {
        double distance = calculateDistance(rider.getLatitude(), rider.getLongitude(), storeLatitude, storeLongitude);

        if (distance > 5.0) {
            cancelDelivery(rider.getId());
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515; // mile 단위로 변환

        dist = dist * 1.609344;

        return dist;
    }
}