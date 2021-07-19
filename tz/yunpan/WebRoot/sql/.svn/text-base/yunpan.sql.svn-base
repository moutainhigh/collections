/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : yunpan

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-11-28 14:00:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tm_folder
-- ----------------------------
DROP TABLE IF EXISTS `tm_folder`;
CREATE TABLE `tm_folder` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_delete` int(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `parent_id` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tm_folder
-- ----------------------------

-- ----------------------------
-- Table structure for tm_resource
-- ----------------------------
DROP TABLE IF EXISTS `tm_resource`;
CREATE TABLE `tm_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表的主键ID',
  `name` varchar(300) DEFAULT NULL COMMENT '源文件名',
  `size` int(11) DEFAULT NULL COMMENT '文件大小',
  `sizeString` varchar(50) DEFAULT NULL COMMENT '文件转换的大小比如:1MB',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '删除状态 (0未删除 1删除)',
  `status` int(1) DEFAULT NULL COMMENT '发布状态(0未发布1已发布)',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `new_name` varchar(300) DEFAULT NULL COMMENT '新的文件名',
  `ext` varchar(20) DEFAULT NULL COMMENT '文件的后缀',
  `user_id` int(11) DEFAULT NULL COMMENT '上传者',
  `width` int(11) DEFAULT NULL COMMENT '图片的宽度',
  `height` int(11) DEFAULT NULL COMMENT '图片的高度',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `folder_id` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL COMMENT '1文件2图片3视频4其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=696 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tm_resource
-- ----------------------------
INSERT INTO `tm_resource` VALUES ('695', '新建文本文档 (2).txt', '485', '485.00B', '2014-11-28 01:04:54', '0', '1', null, 'a1d89370-f861-4d88-99bc-a8891ccfd2e4.txt', 'txt', '1', '100', '100', '', '1', 'upload/a1d89370-f861-4d88-99bc-a8891ccfd2e4.txt', '1');

-- ----------------------------
-- Table structure for tm_user
-- ----------------------------
DROP TABLE IF EXISTS `tm_user`;
CREATE TABLE `tm_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime DEFAULT NULL,
  `is_delete` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tm_user
-- ----------------------------
INSERT INTO `tm_user` VALUES ('1', 'keke', 'JzVwwurTla7ZEMk77iWDzw==', '柯柯', '2014-11-27 22:46:03', null, '0');
INSERT INTO `tm_user` VALUES ('2', 'shanchen', 'JzVwwurTla7ZEMk77iWDzw==', '单晨', '2014-11-28 00:20:18', null, '0');
