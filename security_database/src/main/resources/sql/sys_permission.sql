/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 22/05/2020 17:33:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称 权限的名字可以随意起名',
  `descritpion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'url 通配符为两颗星，比如说 /user下的所有url，应该写成 /user/**',
  `pid` int(11) DEFAULT NULL COMMENT '上级节点',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求的方式，适配restful风格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 'ROLE_HOME', 'home', '/', NULL, 'GET');
INSERT INTO `sys_permission` VALUES (2, 'ROLE_ADMIN', 'admin', '/admin', NULL, 'POST');
INSERT INTO `sys_permission` VALUES (3, 'ROLE_USER_GET', 'user', '/user/**', NULL, 'GET');
INSERT INTO `sys_permission` VALUES (4, 'ROLE_USER_POST', 'user', '/user/**', NULL, 'POST');
INSERT INTO `sys_permission` VALUES (5, 'ROLE_USER_PUT', 'user', '/user/**', NULL, 'PUT');
INSERT INTO `sys_permission` VALUES (6, 'ROLE_USER_ALL', 'user', '/user/**', NULL, 'ALL');

SET FOREIGN_KEY_CHECKS = 1;
