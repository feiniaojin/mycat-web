package io.mycat.myweb.core.rest;

import io.mycat.myweb.core.domain.Account;
import io.mycat.myweb.core.exception.AccountException;
import io.mycat.myweb.core.service.AccountService;
import io.mycat.myweb.core.service.TokenService;
import io.mycat.myweb.core.util.TokenUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:qinyujie@gingo.cn">Yujie</a>
 * @version 0.1
 */
@RestController
public class AccountController {

  @Resource
  private AccountService accountService;

  @Autowired
  private TokenService tokenService;

  /**
   * login by username
   *
   * @param user
   * @return
   */
  @PostMapping("/login")
  public Map<String, Object> login(@RequestBody Account user) {

    Optional<Account> userForBase = accountService.findByUserName(user.getUsername());

    if (!userForBase.isPresent()) {
      throw new AccountException.AccountNotExists();
    }

    Account dbuser = userForBase.get();

    if (!dbuser.getPassword().equals(user.getPassword())) {
      throw new AccountException.PasswordIncorrect();
    }

    Map<String, Object> data = new HashMap<>();
    String token = tokenService.getToken(dbuser);
    data.put("token", token);
    data.put("user", userForBase);
    data.put("expireTime", getExpireTime());

    return data;
  }

  /**
   * 30分钟的过期时间
   * @return
   */
  private String getExpireTime() {
    LocalDateTime dateTime = LocalDateTime.now().plusMinutes(30);
    return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(dateTime);
  }


  @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
  public String getMessage() {

    // 取出token中带的用户id 进行操作
    System.out.println(TokenUtil.getTokenUserId());

    return "您已通过验证";
  }
}
