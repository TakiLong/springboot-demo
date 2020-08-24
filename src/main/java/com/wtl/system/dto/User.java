package com.wtl.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wtl.base.BaseDTO;
import com.wtl.utils.DateTool;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统用户
 *
 * @author wangtianlong 2019/3/19 16:25
 * @version 1.0
 */
/*
CREATE TABLE `sys_user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表ID，主键，供其他表做外键',
  `USERNAME` varchar(30) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `NICKNAME` varchar(30) NOT NULL COMMENT '用户名称',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `SEX` int(1) DEFAULT NULL COMMENT '性别：1-男；0-女',
  `ENABLED` int(1) NOT NULL DEFAULT '1' COMMENT '启用标识：1/0',
  `VERSION_NUMBER` int(11) NOT NULL DEFAULT '1' COMMENT '行版本号，用来处理锁',
  `CREATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATE_BY` bigint(11) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `UPDATE_BY` bigint(11) NOT NULL DEFAULT '-1' COMMENT '更新人',
  `UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `ATTRIBUTE1` varchar(150) DEFAULT NULL,
  `ATTRIBUTE2` varchar(150) DEFAULT NULL,
  `ATTRIBUTE3` varchar(150) DEFAULT NULL,
  `ATTRIBUTE4` varchar(150) DEFAULT NULL,
  `ATTRIBUTE5` varchar(150) DEFAULT NULL,
  `ATTRIBUTE6` varchar(150) DEFAULT NULL,
  `ATTRIBUTE7` varchar(150) DEFAULT NULL,
  `ATTRIBUTE8` varchar(150) DEFAULT NULL,
  `ATTRIBUTE9` varchar(150) DEFAULT NULL,
  `ATTRIBUTE10` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户';
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "SYS_USER")
public class User extends BaseDTO{
    private static final long serialVersionUID = -7395431342743009038L;
    private static final long aa = 1922323L;

    /**
     * 用户ID
     * 主键，供其他表做外键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy("DESC")
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 生日
     */
    @JsonFormat(pattern = DateTool.Pattern.DATE)
    private Date birthday;
    /**
     * 性别：1-男/0-女
     */
    private Integer sex;
    /**
     * 是否启用：1/0
     */
    private Integer enabled;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

}
