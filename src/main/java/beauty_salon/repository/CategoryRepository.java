package beauty_salon.repository;

import beauty_salon.entities.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends GeneralRepository<CategoryEntity, Long> {
}

