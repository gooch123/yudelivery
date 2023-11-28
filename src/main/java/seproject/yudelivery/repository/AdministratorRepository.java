package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.AdministratorEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> {

    List<AdministratorEntity> findAllById(Long id);
    Optional<AdministratorEntity> findById(Long id);

    default void delete(Long id) {
        deleteById(id); // 리뷰 삭제
    }
}
