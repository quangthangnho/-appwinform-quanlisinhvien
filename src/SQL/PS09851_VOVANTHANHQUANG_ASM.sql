use master
go
drop database PS09851_VOVANTHANHQUANG_ASM
go
create database PS09851_VOVANTHANHQUANG_ASM
go
use PS09851_VOVANTHANHQUANG_ASM
go
create table USERS(
	username nvarchar(50) primary key not null,
	password nvarchar(50),
	role nvarchar(50)
)
go
create table STUDENTS(
	MASV varchar(50) primary key not null,
	Hoten nvarchar(50),
	Email varchar(50),
	SoDT varchar(15),
	Gioitinh bit,
	Diachi nvarchar(50),
	hinh varchar(50)
)
go
create table GRADE(
	ID int identity(1,1) primary key not null,
	MASV varchar(50),
	Tienganh int,
	Tinhoc int,
	GDTC int
	foreign key (MASV) references STUDENTS(MASV)
)
go

insert into USERS
	values('admin',123,'admin')
go
insert into USERS
	values('quang',456,'user')
go

-- nhập bảng students
insert into STUDENTS
	values('PS001',N'Thanh Quang','quang.thangnho@gmail.com','0869309863',1,N'Âu cơ p10 Tân Bình','ngoctrinh3.jpg')
go
insert into STUDENTS
	values('PS002',N'Tường Vy','vytuong@gmail.com','09855257',0,N'Tân Bình','ngoctrinh1.jpg')
go
insert into STUDENTS
	values('PS003',N'Tường','tuong@gmail.com','09547257',0,N'Tân Phú','ngoctrinh2.jpg')
go
insert into STUDENTS
	values('PS004',N'Minh','minh@gmail.com','04747257',1,N'Phú Thọ','ngoctrinh4.jpg')
go

-- nhaapk bảng grade
insert into GRADE
	values('PS001',7,8,9)
go
insert into GRADE
	values('PS002',9,10,5)
go
insert into GRADE
	values('PS003',8,4,9)
go
insert into GRADE
	values('PS004',9,7,9)
go


----- 3. thêm vào bảng users
--insert into USERS
--	values('teo',123,'admin')
--go
---- 4. xoa 1 dòng từ bảng users
--delete from USERS
--	where username = 'teo'
--go
----5. sửa 1 dòng trng bảng
--update USERS
--set password = '123456', role ='admin'
--where username = 'quang'





--select GRADE.MASV, Hoten, Tienganh, Tinhoc, GDTC, (Tienganh + Tinhoc + GDTC)/3 as diemTB 
--from GRADE, STUDENTS 
--where STUDENTS.MASV = GRADE.MASV

-- tạo store
--drop proc sp_STUDENTS_get
--go
--create proc sp_STUDENTS_get
--as
--select * from STUDENTS
--go
----exec  sp_STUDENTS_get
---- store theem sinh vieen
--drop proc sp_STUDENTS_Insert
--go
--create proc sp_STUDENTS_Insert
--	@MASV varchar(50),
--	@Hoten nvarchar(50),
--	@Email varchar(50),
--	@SoDT varchar(15),
--	@Gioitinh bit,
--	@Diachi nvarchar(50),
--	@hinh varchar(50)
--as
--	insert into STUDENTS 
--		values(@MASV,@Hoten,@Email,@SoDT,@Gioitinh,@Diachi,@hinh)
--go
--exec  sp_STUDENTS_Insert 'PS007',N'Hà','ha@gmail.com',085982554,0,N'Hà Nội','ha.jpg'

-- store update sinh vieen
--drop proc sp_STUDENTS_update
--go
--create proc sp_STUDENTS_update
--	@MASV varchar(50),
--	@Hoten nvarchar(50),
--	@Email varchar(50),
--	@SoDT varchar(15),
--	@Gioitinh bit,
--	@Diachi nvarchar(50),
--	@hinh varchar(50)
--as
--	update STUDENTS 
--	set Hoten =	@Hoten,
--		Email =	@Email,
--		SoDT  = @SoDT,
--		Gioitinh = 	@Gioitinh,
--		Diachi =	@Diachi,
--		hinh = 	@hinh
--	where MASV = @MASV
--go
--exec sp_STUDENTS_update 'PS004',N'Minh Ngách','minh@gmail.com',0855896,1,N'Bình Thuận','ngoctrinh3.jpg'

-- viết store xoá sinh viên

--create proc sp_STUDENTS_delete
--	@MASV varchar(50)
--as
--	delete from STUDENTS
--	where MASV = @MASV
--	exec sp_STUDENTS_delete 'PS005'
