/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-10 10:17:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for au_user
-- ----------------------------
DROP TABLE IF EXISTS `au_user`;
CREATE TABLE `au_user` (
  `USER_ID` int(16) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USER_NAME` varchar(60) DEFAULT NULL COMMENT '用户名称',
  `ACCOUNT` varchar(60) DEFAULT NULL COMMENT '登录账户',
  `PASSWORD` varchar(60) DEFAULT NULL COMMENT '密码',
  `USER_TYPE` varchar(60) DEFAULT NULL COMMENT '用户类型',
  `VALID_TIME` varchar(20) DEFAULT NULL COMMENT '有效开始时间',
  `ORG_ID` int(16) DEFAULT NULL COMMENT '所属部门ID',
  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '所属部门名称',
  `REMARK` varchar(2000) DEFAULT NULL COMMENT '备注',
  `USER_STATUS` varchar(6) DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of au_user
-- ----------------------------
INSERT INTO `au_user` VALUES ('1', '超级管理员', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', null, '2018-12-05 ', null, null, '<h1 style=\"text-align: center;\"><span style=\"color: rgb(194, 79, 74);\">TEST CONTENT</span></h1><div style=\"text-align: center;\"><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><th>&nbsp;T</th><th>T&nbsp;</th><th>T&nbsp;</th><th>&nbsp;T</th><th>&nbsp;T</th></tr><tr><td>success&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr></tbody></table><p style=\"text-align: left;\"><br></p></div>', '100001');
INSERT INTO `au_user` VALUES ('2', '超级管理员2', 'admin2', '4297F44B13955235245B2497399D7A93', null, '2018-12-05 ', null, null, '<h2><span style=\"color: rgb(70, 172, 200);\">TEST</span></h2>', '100001');
INSERT INTO `au_user` VALUES ('4', 'chaoji', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('5', 'erwer', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('7', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('8', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('9', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('10', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('11', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('12', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('13', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('14', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('15', 'test', null, null, null, null, null, null, null, '100001');
INSERT INTO `au_user` VALUES ('16', '', null, null, null, '', null, null, '<p><br></p>', '100001');
INSERT INTO `au_user` VALUES ('17', '', null, null, null, '', null, null, '<p><br></p>', '100001');
INSERT INTO `au_user` VALUES ('18', '', null, null, null, '', null, null, '<p><br></p>', '100001');

-- ----------------------------
-- Table structure for dic_tree
-- ----------------------------
DROP TABLE IF EXISTS `dic_tree`;
CREATE TABLE `dic_tree` (
  `dic_id` int(16) NOT NULL,
  `dic_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`dic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dic_tree
-- ----------------------------
INSERT INTO `dic_tree` VALUES ('100001', '有效');
