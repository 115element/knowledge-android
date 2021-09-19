--'版本表version'
create table version(
storeId varchar(8) not null,--'店号'
posNo int not null,--'收款机台号'
version varchar(10) not null,--'pos程序版本号'
masterVersion datetime not null,--'pos主档版本号'
updateDate datetime not null,--'更新时间：pos上传时间'
constraint PK_union primary key(storeId,posNo,version)

);