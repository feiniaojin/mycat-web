package io.mycat.myweb.core.exception;

import cn.gingo.global.result.handler.annotation.ExceptionMapper;

/**
 * 账号相关的异常
 *
 * @author yujie
 */
public class AccountException {

  @ExceptionMapper(code = 1404, msg = "Account Not Exists!")
  public static class AccountNotExists extends RuntimeException {
  }

  @ExceptionMapper(code = 1406, msg = "Password Incorrect!")
  public static class PasswordIncorrect extends RuntimeException {
  }

}
