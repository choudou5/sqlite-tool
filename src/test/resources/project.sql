
CREATE TABLE `MESSAGE_GROUP` (
  `id` VARCHAR(40) NOT NULL COMMENT '主键',
  `group_key` VARCHAR(64) NOT NULL COMMENT '群组Key',
  `name` VARCHAR(36) NOT NULL COMMENT '群组名',
  `master` VARCHAR(32) NOT NULL COMMENT '管理员ID',
  `userids` VARCHAR(1024) DEFAULT NULL COMMENT '成员ID,逗号隔开',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `env` VARCHAR(10) NOT NULL DEFAULT 'test' COMMENT '环境: test/product',
  `used` CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '是否使用:Y/N',
  PRIMARY KEY (`id`),
  KEY `index_group_key` (`group_key`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='群组表'



CREATE TABLE `MESSAGE_SEND_HI` (
  `id` VARCHAR(32) NOT NULL COMMENT '主键',
  `receive_by` VARCHAR(64) NOT NULL COMMENT '接收人（个人或群组ID）',
  `send_type` CHAR(1) NOT NULL COMMENT '发送类型：p=个人、g=群组',
  `biz_type` VARCHAR(64) NOT NULL COMMENT '业务类型',
  `content` VARCHAR(256) NOT NULL COMMENT '内容',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='消息发送历史';