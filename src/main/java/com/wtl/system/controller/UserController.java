package com.wtl.system.controller;

import com.wtl.base.BaseController;
import com.wtl.base.Result;
import com.wtl.constants.BaseEnums;
import com.wtl.system.dto.User;
import com.wtl.system.service.UserService;
import com.wtl.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统用户controller
 *
 * @author wangtianlong 2019/3/19 16:28
 * @version 1.0
 */
@RequestMapping
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/sys/user/queryAll")
    public Result queryAll(){
        List<User> list = userService.selectAll();
        return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

    @RequestMapping("/sys/user/queryOne/{userId}")
    public Result queryOne(@PathVariable Long userId){
        User user = userService.get(userId);

        logger.info("userId:{}, userName:{}, birthday:{}", user.getUserId(), user.getUsername(), user.getBirthday());
        logger.warn("userId:{}, userName:{}, birthday:{}", user.getUserId(), user.getUsername(), user.getBirthday());
        logger.debug("userId:{}, userName:{}, birthday:{}", user.getUserId(), user.getUsername(), user.getBirthday());
        logger.error("userId:{}, userName:{}, birthday:{}", user.getUserId(), user.getUsername(), user.getBirthday());

        return Results.successWithData(user);
    }

    @PostMapping("/sys/user/save")
    public Result save(@Valid @RequestBody User user){
        user = userService.insertSelective(user);
        return Results.successWithData(user);
    }

    @PostMapping("/sys/user/update")
    public Result update(@Valid @RequestBody List<User> user){
        user = userService.persistSelective(user);
        return Results.successWithData(user);
    }

    @RequestMapping("/sys/user/delete")
    public Result delete(User user){
        userService.delete(user);
        return Results.success();
    }

    @RequestMapping("/sys/user/delete/{userId}")
    public Result delete(@PathVariable Long userId){
        userService.delete(userId);
        return Results.success();
    }

}