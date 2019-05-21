/*
Navicat MySQL Data Transfer

Source Server         : 3306-5.5-1
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : portrait

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-05-21 18:17:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) DEFAULT NULL,
  `product_id` int(20) NOT NULL,
  `product_type_id` int(20) NOT NULL,
  `num` int(20) DEFAULT NULL,
  `total_amount` double(20,2) DEFAULT NULL,
  `init_amount` double(20,2) DEFAULT NULL,
  `refund_amount` double(20,2) DEFAULT NULL,
  `coupon_amount` double(20,2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `pay_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `pay_type` int(2) DEFAULT NULL,
  `pay_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `product_type_id` int(20) DEFAULT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `product_description` varchar(1500) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `num` int(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `mechart_id` int(20) DEFAULT NULL,
  `product_url` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) DEFAULT NULL,
  `type_description` varchar(200) DEFAULT NULL,
  `type_level` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_type
-- ----------------------------

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail` (
  `user_detail_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) DEFAULT NULL,
  `education` varchar(20) DEFAULT NULL,
  `profession` varchar(20) DEFAULT NULL,
  `marriage` int(1) DEFAULT NULL COMMENT '0-未婚 1-已婚 2-离异 3-未知',
  `has_child` int(1) DEFAULT NULL COMMENT '0-无 1-有 2-未知',
  `has_house` int(1) DEFAULT NULL COMMENT '0-无 1-有 2-未知',
  `telphone_brand` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_detail
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL COMMENT '女:0 男:1',
  `telphone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `age` int(20) DEFAULT NULL,
  `register_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `use_type` varchar(1) NOT NULL COMMENT '0-pc 1-移动端 2-小程序',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
