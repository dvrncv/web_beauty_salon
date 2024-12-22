package beauty_salon.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GeneralRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);

    List<T> findAll();

    Optional<T> findById(ID id);
}
