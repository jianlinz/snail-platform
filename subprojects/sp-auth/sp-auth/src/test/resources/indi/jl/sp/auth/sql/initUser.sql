-- 初始化测试环境admin角色 密码 admin md5加密
INSERT INTO `sp_auth_user`(`id`, `create_time`, `create_user_name`, `update_time`, `update_user_name`, `name`, `password`, `username`) VALUES (1, '2021-09-27 14:51:20.000000', 1, '2021-09-27 14:51:25.000000', 1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin');
INSERT INTO `sp_auth_role`(`id`, `create_time`, `create_user_name`, `update_time`, `update_user_name`, `name`) VALUES (1, '2021-09-27 14:51:59.000000', 1, '2021-09-27 14:52:03.000000', 1, 'admin');

