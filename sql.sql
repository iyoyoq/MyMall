-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.41 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 server_db 的数据库结构
CREATE DATABASE IF NOT EXISTS `server_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `server_db`;

-- 导出  表 server_db.auth_address 结构
CREATE TABLE IF NOT EXISTS `auth_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联auth_user表',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人手机号',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '城市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区/县',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
  `is_default` int NOT NULL DEFAULT (0) COMMENT '是否默认地址（0否 1是）',
  `status` int NOT NULL DEFAULT (1) COMMENT '状态（1正常 -1删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1898711949414199298 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户地址表';

-- 正在导出表  server_db.auth_address 的数据：~4 rows (大约)
INSERT INTO `auth_address` (`id`, `user_id`, `receiver_name`, `receiver_phone`, `province`, `city`, `district`, `detail_address`, `is_default`, `status`, `create_time`, `update_time`) VALUES
	(1898711862344642562, 25, '委有再', '17666475156', '辽宁省', '厦门市', '铁东区', '云南省厦安市民乐县', 0, 1, '2025-03-09 20:25:59', '2025-03-09 20:25:59'),
	(1898711941637959681, 25, '委有再', '17666475156', '辽宁省', '厦门市', '铁东区', '云南省厦安市民乐县', 0, 1, '2025-03-09 20:26:17', '2025-03-09 20:26:17'),
	(1898711945299587073, 25, '委有再', '17666475156', '辽宁省', '厦门市', '铁东区', '云南省厦安市民乐县', 0, 1, '2025-03-09 20:26:18', '2025-03-09 20:26:18'),
	(1898711949414199297, 25, '委有再', '17666475156', '辽宁省', '厦门市', '铁东区', '云南省厦安市民乐县', 0, 1, '2025-03-09 20:26:19', '2025-03-09 20:26:19');

-- 导出  表 server_db.auth_favorite 结构
CREATE TABLE IF NOT EXISTS `auth_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联auth_user表',
  `product_id` bigint NOT NULL COMMENT '商品ID，关联商品表',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户收藏表';

-- 正在导出表  server_db.auth_favorite 的数据：~0 rows (大约)

-- 导出  表 server_db.auth_user 结构
CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '平台编号',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `avatar` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `check_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '待审核昵称',
  `check_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '待审核简介',
  `check_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '待审核头像',
  `check_status` int(10) unsigned zerofill DEFAULT NULL COMMENT '审核状态 1待审核 2审核失败 3审核成功',
  `status` int DEFAULT NULL COMMENT '状态 0禁用 1启用  -1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1898201882843598850 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- 正在导出表  server_db.auth_user 的数据：~1 rows (大约)
INSERT INTO `auth_user` (`id`, `code`, `nick_name`, `intro`, `avatar`, `phone`, `password`, `check_nick_name`, `check_intro`, `check_avatar`, `check_status`, `status`, `create_time`, `update_time`) VALUES
	(1898201882843598849, '882994238', '用户882994238', NULL, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '1', NULL, NULL, NULL, NULL, NULL, 1, '2025-03-08 10:39:30', '2025-03-15 09:48:52');

-- 导出  表 server_db.cart 结构
CREATE TABLE IF NOT EXISTS `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联auth_user表',
  `sku_id` bigint NOT NULL COMMENT 'SKU ID，关联product_sku表',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '购买数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='购物车表';

-- 正在导出表  server_db.cart 的数据：~0 rows (大约)

-- 导出  表 server_db.order 结构
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号（唯一）',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联auth_user表',
  `total_amount` int NOT NULL COMMENT '订单总金额（单位：分）',
  `status` int NOT NULL DEFAULT '0' COMMENT '订单状态（0待支付 1待发货 2待收货 3已完成 -1已取消）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单主表';

-- 正在导出表  server_db.order 的数据：~0 rows (大约)

-- 导出  表 server_db.order_detail 结构
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_id` bigint NOT NULL COMMENT '订单ID，关联order表',
  `sku_id` bigint NOT NULL COMMENT 'SKU ID，关联product_sku表',
  `price` int NOT NULL COMMENT '单价（下单时的价格/分）',
  `quantity` int NOT NULL COMMENT '购买数量',
  `total_price` int NOT NULL COMMENT '总金额（单价 * 数量）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单明细表';

-- 正在导出表  server_db.order_detail 的数据：~0 rows (大约)

-- 导出  表 server_db.pay 结构
CREATE TABLE IF NOT EXISTS `pay` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付ID',
  `order_id` bigint NOT NULL COMMENT '订单ID，关联order表',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联auth_user表',
  `amount` int NOT NULL COMMENT '支付金额（单位：分）',
  `pay_method` int NOT NULL COMMENT '支付方式（ 1 余额 2微信 3银行卡 4支付宝）',
  `pay_status` int NOT NULL DEFAULT '0' COMMENT '支付状态（0未支付 1已支付 2支付失败）',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='支付表';

-- 正在导出表  server_db.pay 的数据：~0 rows (大约)

-- 导出  表 server_db.pay_refund 结构
CREATE TABLE IF NOT EXISTS `pay_refund` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款ID',
  `order_id` bigint NOT NULL COMMENT '订单ID，关联order表',
  `payment_id` bigint NOT NULL COMMENT '支付ID，关联payment表',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联auth_user表',
  `refund_amount` int NOT NULL COMMENT '退款金额（单位：分）',
  `refund_reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '退款原因',
  `refund_status` int NOT NULL DEFAULT '0' COMMENT '退款状态（0待审核 1退款中 2退款成功 3退款失败）',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='退款表';

-- 正在导出表  server_db.pay_refund 的数据：~0 rows (大约)

-- 导出  表 server_db.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品描述',
  `main_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主图URL',
  `images` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片JSON数组',
  `status` int NOT NULL DEFAULT (1) COMMENT '状态(-1删除 0下架 1上架)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品表';

-- 正在导出表  server_db.product 的数据：~0 rows (大约)

-- 导出  表 server_db.product_category 结构
CREATE TABLE IF NOT EXISTS `product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类编码',
  `parent_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父分类编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态(-1删除 0下架 1上架)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品分类表';

-- 正在导出表  server_db.product_category 的数据：~0 rows (大约)

-- 导出  表 server_db.product_sku 结构
CREATE TABLE IF NOT EXISTS `product_sku` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SKU编号',
  `product_id` bigint NOT NULL COMMENT '所属商品ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SKU名称',
  `price` int NOT NULL DEFAULT (0) COMMENT '销售价格/分',
  `stock_quantity` int NOT NULL DEFAULT '0' COMMENT '库存数量',
  `locked_stock` int NOT NULL DEFAULT '0' COMMENT '锁定库存（待付款订单）',
  `sales_count` int NOT NULL DEFAULT '0' COMMENT '销量',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态(-1删除  0 下架 1上架)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品SKU表';

-- 正在导出表  server_db.product_sku 的数据：~0 rows (大约)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
