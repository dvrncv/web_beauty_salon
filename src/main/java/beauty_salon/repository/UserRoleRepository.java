package beauty_salon.repository;

import beauty_salon.entities.Role;
import beauty_salon.enums.StaffRoles;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends GeneralRepository<Role, String> {
    Optional<Role> findRoleByName(StaffRoles role);

    boolean existsRoleByName(StaffRoles role);
}
