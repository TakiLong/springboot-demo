package com.wtl.system.controller;

import com.wtl.base.BaseController;
import com.wtl.base.Result;
import com.wtl.constants.BaseEnums;
import com.wtl.system.dto.User;
import com.wtl.system.service.UserService;
import com.wtl.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "用户管理")
@RequestMapping
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation("查询所有用户")
    @RequestMapping(value = "/sys/user/queryAll", method = RequestMethod.GET)
    public Result queryAll(){
        List<User> list = userService.selectAll();
        return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

    @ApiOperation("查询单个用户")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "path")
    @GetMapping("/sys/user/queryOne/{userId}")
    public Result queryOne(@PathVariable Long userId){
        User user = userService.get(userId);
        if(user == null){
            return Results.failure("USER_NOT_EXIST","该用户不存在");
        }
        logger.info("userId:{}, userName:{}, birthday:{}", user.getUserId(), user.getUsername(), user.getBirthday());
        return Results.successWithData(user);
    }

    @ApiOperation("新增用户")
    @PostMapping("/sys/user/save")
    public Result save(@ApiParam(name = "user", value = "用户") @Valid @RequestBody User user){
        user = userService.insertSelective(user);
        return Results.successWithData(user);
    }

    /* 可实现批量更新 */
    @ApiOperation("更新用户")
    @PostMapping("/sys/user/update")
    public Result update(@ApiParam(name = "user", value = "用户") @Valid @RequestBody List<User> user){
        user = userService.persistSelective(user);
        return Results.successWithData(user);
    }

    @ApiOperation("删除用户-用户对象")
    @PostMapping("/sys/user/delete")
    public Result delete(@ApiParam(name = "user", value = "用户") @Valid @RequestBody User user){
        userService.delete(user);
        return Results.success("USER_DELETE_SUCCESS","用户删除成功");
    }

    @ApiOperation("删除用户-地址栏追加userId")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "path")
    @GetMapping("/sys/user/delete/{userId}")
    public Result delete(@Valid @PathVariable Long userId){
        userService.delete(userId);
        return Results.success("USER_DELETE_SUCCESS","用户删除成功");
    }

}