package vfd.arca.mm.user_auth.repository;

import vfd.arca.mm.user_auth.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends CrudRepository<Account, String>{
     Account findByUsername(String username);
}
