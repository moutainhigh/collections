/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : tzexam

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-10-19 01:29:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tz_exam
-- ----------------------------
DROP TABLE IF EXISTS `tz_exam`;
CREATE TABLE `tz_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `description` text CHARACTER SET utf8,
  `size` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `ext` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '1上交2批改中3已批改',
  `is_delete` int(1) DEFAULT NULL COMMENT '0未删除1删除',
  `folder_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tz_exam
-- ----------------------------

-- ----------------------------
-- Table structure for tz_exam_desc
-- ----------------------------
DROP TABLE IF EXISTS `tz_exam_desc`;
CREATE TABLE `tz_exam_desc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `teacher_content` text CHARACTER SET utf8,
  `student_content` text CHARACTER SET utf8,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tz_exam_desc
-- ----------------------------

-- ----------------------------
-- Table structure for tz_folder
-- ----------------------------
DROP TABLE IF EXISTS `tz_folder`;
CREATE TABLE `tz_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '1进行中2已结束',
  `is_delete` int(1) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text CHARACTER SET utf8,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tz_folder
-- ----------------------------

-- ----------------------------
-- Table structure for tz_log
-- ----------------------------
DROP TABLE IF EXISTS `tz_log`;
CREATE TABLE `tz_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` text CHARACTER SET utf8,
  `user_id` int(11) DEFAULT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `folder_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `ip` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `ip_address` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tz_log
-- ----------------------------

-- ----------------------------
-- Table structure for tz_subject
-- ----------------------------
DROP TABLE IF EXISTS `tz_subject`;
CREATE TABLE `tz_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `description` text CHARACTER SET utf8,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete` int(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '创建者',
  `up_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tz_subject
-- ----------------------------

-- ----------------------------
-- Table structure for tz_user
-- ----------------------------
DROP TABLE IF EXISTS `tz_user`;
CREATE TABLE `tz_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(80) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(80) CHARACTER SET utf8 DEFAULT NULL,
  `account` varchar(60) CHARACTER SET utf8 DEFAULT NULL,
  `telephone` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `idcard` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete` int(1) DEFAULT NULL COMMENT '0未删除1删除',
  `male` int(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL COMMENT '老师id',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科id',
  `age` int(3) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0未发布1发布',
  `type` int(1) DEFAULT NULL COMMENT '1老师(助教）2学生',
  PRIMARY KEY (`id`),
  KEY `password` (`password`,`account`,`telephone`,`is_delete`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tz_user
-- ----------------------------
INSERT INTO `tz_user` VALUES ('1', '柯柯', '4QrcOUm6Wau+VuBX8g+IPg==', 'keke', '15074816437', null, '2015-10-19 00:47:17', '0', '1', '2015-10-19', '153398644@qq.com', '湖南长沙', null, null, '1', '30', '1', '1');
