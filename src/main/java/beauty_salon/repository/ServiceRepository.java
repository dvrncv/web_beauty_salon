package beauty_salon.repository;

import beauty_salon.entities.CategoryEntity;
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

    @Query(value = "select sr from ServiceEntity sr where sr.category =: category")
    List<ServiceEntity> findByCategory(@Param("category") CategoryEntity category);

    Optional<ServiceEntity> findByName(String name);

    @Query("SELECT s.id, s.name, s.description, s.price, s.duration, s.category.id, c.name, COUNT(DISTINCT ase.client.id)\n" +
            "FROM AppointmentServiceEntity ase \n" +
            "JOIN ase.service s\n" +
            "JOIN s.category c\n" +
            "WHERE ase.dateStart BETWEEN :startDate AND :endDate\n" +
            "GROUP BY s.id, s.name, s.description, s.price, s.duration, s.category.id, c.name\n" +
            "ORDER BY COUNT(DISTINCT ase.client.id) DESC")
    List<Object[]> findTopServices(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<ServiceEntity> findByName(String name, Pageable pageable);

    List<ServiceEntity> findByCategoryId(Long categoryId);

    Page<ServiceEntity> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    Page<ServiceEntity> findByNameStartsWithIgnoreCase(String name, Pageable pageable);

    Page<ServiceEntity> findAll(Pageable pageable);

    Page<ServiceEntity> findByCategoryId(Long categoryId, Pageable pageable);

    Page<ServiceEntity> findByNameStartsWithIgnoreCaseAndCategoryId(String name, Long categoryId, Pageable pageable);
}
