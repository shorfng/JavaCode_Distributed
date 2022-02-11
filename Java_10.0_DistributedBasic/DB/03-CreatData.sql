# 插入初始数据
insert into DISTRIBUTE_ID(createtime) values (NOW());

# 使用：查询目前的可用的分布式 id
select LAST_INSERT_ID();
