package beauty_salon.repository;

import beauty_salon.entities.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends GeneralRepository<ServiceEntity, Long> {
    @Query("select s.id as id, s.name as name, s.description as description, s.price as price, " +
            "s.duration as duration, s.category.id as categoryId, c.name as categoryName, " +
            "count(a.id) as appointmentCount " +
            "from AppointmentServiceEntity a " +
            "join a.service s " +
            "join s.category c " +
            "where a.dateStart between :startDate and :endDate " +
            "group by s.id, s.name, s.description, s.price, s.duration, s.category.id, c.name " +
            "order by appointmentCount desc")
    List<Object[]> findTopServices(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<ServiceEntity> findAll(Pageable pageable);

    Page<ServiceEntity> findByNameIgnoreCaseContaining(String searchTerm, Pageable pageable);

    @Query("select s from ServiceEntity s where s.category.id = :categoryId")
    Page<ServiceEntity> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("select s from ServiceEntity s where s.id = :id")
    Optional<ServiceEntity> findById(@Param("id") Long id);
}
