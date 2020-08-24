/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : antd_pro

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-08-24 22:13:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '表ID，主键，供其他表做外键',
  `USERNAME` varchar(30) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `NICKNAME` varchar(30) NOT NULL COMMENT '用户名称',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `SEX` int DEFAULT NULL COMMENT '性别：1-男；0-女',
  `ENABLED` int NOT NULL DEFAULT '1' COMMENT '启用标识：1/0',
  `VERSION_NUMBER` int NOT NULL DEFAULT '1' COMMENT '行版本号，用来处理锁',
  `CREATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATE_BY` bigint NOT NULL DEFAULT '-1' COMMENT '创建人',
  `UPDATE_BY` bigint NOT NULL DEFAULT '-1' COMMENT '更新人',
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('3', 'zhangsan', 'zs123', '张三', '2020-07-21', '1', '1', '1', '2020-07-21 18:10:49', '-1', '-1', '2020-07-21 18:10:49', null, null, null, null, null, null, null, null, null, null);
