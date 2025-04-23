package citytech.global.repository.user;

import citytech.global.platform.constants.Roles;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends CrudRepository<UserEntity, String> {

    boolean existsByRoles(Roles roles);
    Optional<UserEntity> findByEmailId(String emailId);

}
