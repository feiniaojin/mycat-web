package io.mycat.myweb.core.rest;

import io.mycat.myweb.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	UserService userService;

}