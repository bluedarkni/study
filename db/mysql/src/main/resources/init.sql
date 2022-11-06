------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_card` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
