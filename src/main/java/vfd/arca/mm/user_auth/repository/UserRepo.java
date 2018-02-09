package vfd.arca.mm.user_auth.repository;

import vfd.arca.mm.user_auth.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, String>{
     User findByUsername(String username);
}
