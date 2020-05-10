CREATE TABLE `red_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `red_packet` varchar(100) NOT NULL COMMENT '红包全局唯一标识串',
  `total` int(11) NOT NULL COMMENT '人数',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '总金额(单位：分)',
  `active` tinyint(4) DEFAULT '1' COMMENT '是否有效，1是 0否，默认1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发红包记录表';

CREATE TABLE `red_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `record_id` int(11) NOT NULL COMMENT '红包记录id',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '金额(单位：分)',
  `active` tinyint(4) DEFAULT '1' COMMENT '是否有效，1是 0否，默认1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='红包明细金额表';

CREATE TABLE `red_rob_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户账号',
  `red_packet` varchar(100) DEFAULT NULL COMMENT '红包标识串',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '红包金额(单位：分)',
  `rob_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '抢到红包时间',
  `active` tinyint(4) DEFAULT '1' COMMENT '是否有效，1是 0否，默认1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抢红包记录表';