package beauty_salon.repository;

import beauty_salon.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends GeneralRepository<CategoryEntity,Long> {
    @Query(value = "select c from CategoryEntity c where c.name = :name")
    Optional<CategoryEntity> findByCategoryName(@Param("name") String name);

    List<CategoryEntity> findByNameIn(List<String> names);
}

