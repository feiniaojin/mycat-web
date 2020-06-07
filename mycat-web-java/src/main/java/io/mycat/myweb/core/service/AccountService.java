package io.mycat.myweb.core.service;

import io.mycat.myweb.core.dao.AccountDao;
import io.mycat.myweb.core.domain.Account;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
@Service
public class AccountService {

  @Autowired
  private AccountDao accountDao;

  public Optional<Account> findByUserName(String userName) {
    return accountDao.accountByUsername(userName);
  }

}
