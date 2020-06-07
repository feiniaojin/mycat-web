package io.mycat.myweb.core.dao;

import io.mycat.myweb.core.domain.Account;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
public interface AccountDao extends PagingAndSortingRepository<Account, Long> {

  @Query("SELECT * FROM account WHERE username = :name")
  Optional<Account> accountByUsername(@Param("name") String name);
}
