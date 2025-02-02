create database webfilm
use webfilm
create table session (
	sessionId nvarchar(255) Primary key,
	userId bigint not null,
	foreign key (userId) references users(id)
)
alter table session add constraint fk5 foreign key (userId) references users(id)

select * from session


create table users (
	id bigint Identity(1,1) Primary key,
	email nvarchar(100) unique not null,
	password nvarchar(255) not null,
	firstName nvarchar(25)not null,
	lastName nvarchar(25) not null,
	gender int not null,
	address nvarchar(100),
	role int
)
alter table users add constraint UQ_users_email unique(email)
select * from users

create table movies(
	id bigint identity(1,1) primary key,
	title nvarchar(100) unique not null,
	description nvarchar(max),
	category nvarchar(30),
	releaseDate int,
	country nvarchar(30),
	imageURL nvarchar(max)
)
alter table movies add constraint un_movies_title unique(title)

select * from movies
EXEC sp_rename 'movies.genre', 'category', 'column'
create table episodes (
	id bigint identity(1,1),
	movieId bigint not null,
	episode int not null,
	[view] bigint default 0,
	createAt date default GETDATE(),
	videoURL nvarchar(max),
	version DateTime default GETDATE(),
	Primary key( movieId,episode),
	Foreign key (movieId) references movies(id),
)
ALTER TABLE episodes 
ADD CONSTRAINT version DEFAULT GETDATE() FOR version;
ALTER TABLE episodes 
ADD CONSTRAINT DF_episodes_view DEFAULT 0 FOR [view];
alter table episodes add constraint fk_movie_id_from_movies foreign key (movieId) references movies(id)
alter table episodes drop column duration
alter table episodes alter column movieId bigint
select * from episodes
CREATE TABLE genres (
	id int identity(1,1) primary key,
	name nvarchar(30) unique
)
select * from genres


create table watchLater (
	userId bigint,
	movieId bigint
	primary key(userId,movieId),
	foreign key (userId) references users(id),
	foreign key (movieId) references movies(id)
)
alter table watchLater add constraint fk3 foreign key (movieId) references movies(id)
alter table watchLater add constraint fk4 foreign key (userId) references users(id)
drop table watchLater
alter table watchLater alter column episodeId bigint
select * from watchLater
CREATE TABLE movies_genres (
    movieId bigINT not null,
    genreId INT not null,
    PRIMARY KEY (movieId, genreId),
    FOREIGN KEY (movieId) REFERENCES movies(id),
    FOREIGN KEY (genreId) REFERENCES genres(id)
);
alter table movies_genres add constraint fk1 foreign key (movieId) references movies(id)
alter table movies_genres add constraint fk2 foreign key (genreId) references genres(id)
--
alter table movies_genres alter column movieId bigint
insert into	movies values(N'Bạch Nguyệt Phạn Tinh',
N'Chuyển thể từ tiểu thuyết Bạch Thước Thượng Thần Của tác giả Tinh Linh. Bạch Tuân Nguyên soái Tướng quân là đại thần quyền uy nhất Kinh thành, không kết giao bằng hữu, chỉ một lòng muốn răng long đầu bạc với Bạch phu nhân của mình. Sau 7 năm thành hôn họ mới sinh được một cặp song sinh. Trưởng nữ tên Bạch Hi, thứ nữ là Bạch Thước (Bạch Lộc). Trưởng nữ vừa sinh ra đã được chọn làm Thái tử phi. Thứ nữ năm lên 3 tuổi cũng được hứa hôn cho ấu tử nhà Hữu tướng. Vào dịp lễ mỗi năm, nhà họ đều cho hai cô con gái cho ra khỏi phủ ngắm hoa đăng. Nhưng vào cái năm định mệnh đó tỷ muội Bạch Thước bị Yêu tộc bắt đi đến Hoàng lăng, chúng muốn hút linh khí trong người Bạch Hi. Nàng vì không muốn bọn yêu quái đạt được mục đích đã tự vẫn trước mặt muội muội. May mà lúc này Thần Quân Thiên Khải xé rách hư không vào thẳng Quỷ giới, vốn định tìm nguyên thần lưu lạc của nữ thần Nguyệt Di, nhưng tình cờ lại thấy cảnh nguy cấp này. Bạch Thước may mắn thoát chết.
Từ đó về sau, nguyện vọng lớn nhất đời nàng là phi thăng thành Thần, phổ độ chúng sinh. Cha nàng cứ tưởng nàng bị ngốc, nhưng nàng như cái đuôi nhỏ suốt ngày quấn lấy cha và một mực muốn lên Thiên giới.',
N'Phim bộ',2025,N'Trung Quốc','https://static2.vieon.vn/vieplay-image/thumbnail_v4/2025/01/08/p2zkoubi_zr19ytn2_1920x1080-bachnguyenphantinh_296_168.webp')
insert into movies values
(N'Thảm Họa Húi Beakdu',N'Núi Baekdu – ngọn núi lửa cao nhất bán đảo Triều Tiên nằm ở biên giới giữa Triều Tiên và Trung Quốc bất ngờ phun trào, khiến toàn bộ người dân trên bán đảo kinh hoàng. Thảm họa chưa dừng lại ở đó, tiếp theo sau sẽ là những lần phun trào được dự báo là dữ dội hơn nữa, đe dọa sẽ nhấn chìm toàn bộ bán đảo Triều Tiên trong tro tàn tận thế. Một kế hoạch hợp tác giữa Hàn Quốc và Triều Tiên được đề ra để ngăn cản núi lửa Baekdu phun trào, liệu nhiệm vụ dường như bất khả thi này có thể thành công?',N'Phim lẻ',2025,N'Hàn Quốc','https://static2.vieon.vn/vieplay-image/thumbnail_v4/2023/08/07/ybihi714_1920x1080-thamhoanuibeakdu_296_168.webp'),
(N'Bàn Tay Diệt Quỷ',N'Võ sĩ MMA Yong Hoo (Park Seo Joon) đi theo con đường trừ tà trục quỷ sau khi bỗng dưng sở hữu "Bàn tay diệt quỷ". Đối đầu với anh là Giám mục bóng tối - tên quỷ Satan đội lốt người. Dần dần, sự thật về cái chết của cha Yong Hoo và nguyên nhân anh trở thành "người được chọn" được hé lộ.',N'Phim Lẻ',2019,N'Hàn Quốc','https://static2.vieon.vn/vieplay-image/thumbnail_v4/2023/03/28/f9io1ts1_1920x1080-bantaydietquy_296_168.webp'),
(N'Thư Sinh Xinh Đẹp', N'Thư Sinh Xinh Đẹp xoay quanh về Tuyết Văn Hi do cha mất sớm và người em trai đau ốm, đã cải trang thành em trai để kiếm tiền nuôi sống gia đình. Cô đã làm qua rất nhiều công việc, chủ yếu là tại hiệu sách, bao gồm cả việc chép sách cấm, chép tài liệu dùng để gian lận cho nho sinh trường Vân Thượng. Nhưng do nợ Binh phán 100 lượng và có nguy cơ phải bán thân làm thiếp. Vì một lần nhầm lẫn vị trí mà Văn Hi bị bắt quả tang bởi Phong Thừa Tuấn, người sau này thừa nhận tài năng của cô và sau đó khiến cô tham gia kỳ thi khoa bảng. Kết quả là cả hai cùng được vào học tại và ở chung một phòng.',N'Phim bộ',2019,N'Trung Quốc','https://static2.vieon.vn/vieplay-image/thumbnail_v4/2022/11/14/c5uvfwni_1920x1080thusinhxinhdep1_296_168.webp'),
(N'Hôn Nhân Hợp Đồng',N'Hợp đồng hôn nhân vàng giữa nữ nhân gia giáo nhưng ngỗ nghịch thời Joseon Park Yeon Woo và tài phiệt vô cảm Kang Tae Ha ở thế kỷ 21.',N'Phim bộ',2023,N'Hàn Quốc','https://static2.vieon.vn/vieplay-image/thumbnail_v4/2023/10/20/74iyyz6b_1920x1080-honnhanhd_296_168.webp')

INSERT INTO episodes (movieId, episode, [view], createAt,videoURL)  
VALUES 
(1, 1, 0, '2004-01-29','https://www.youtube.com/embed/uvkLwS2VEHs?si=jiiurJbDjEr0QfFg'),
(2, 1, 0, '2005-01-29','https://www.youtube.com/embed/x8QelgCUvTk?si=wflmneb0zctgl7me'),
(3, 1, 0, '2006-01-29','https://www.youtube.com/embed/8von2mPg908?si=FkgbJeTia3B0BO9D'),
(4, 1, 0, '2007-01-29','https://www.youtube.com/embed/sV-c3kd8k-I?si=Y3EEUen9PWx5TPv0'),
(5, 1, 0, '2008-01-29','https://www.youtube.com/embed/t_wvWD-5H5s?si=fY0DP9vZmKq3HUhv'),
(1, 2, 1, '2004-01-29','https://www.youtube.com/embed/uvkLwS2VEHs?si=jiiurJbDjEr0QfFg');

where id in (select top 20 movieId from (select movieId,sum([view]) as [view] 
from episodes 
group by movieId
) as countView 
order by [view] desc)
--
select a.* from movies a
join (select movieId,max(createAt) as createAt from episodes group by movieId) as b on a.id = b.movieId
where a.genre = N'phim bộ'
order by createAt desc
--
select * from movies a
join episodes b on a.id = b.movieId
--
alter table episodes drop column imageURL
--
insert into users values ('admin@gmail.com','1','admin','host',1,'vietnam',1)
insert into users values ('delete@gmail.com','1','admin','host',1,'vietnam',1)

insert into genres values 
(N'Tình Cảm'),--1
(N'Hành Động'),--2
(N'Cổ Trang'),--3
(N'Hài Hước'),--4
(N'Tâm Lý'),--5
(N'Hình Sự'),--6
(N'Khoa Học'),--7
(N'Kinh Dị'),--8
(N'Chiến Tranh'),--9
(N'Viễn Tưởng'),--10
(N'Phiêu Lưu'),--11
(N'Anime'),--12
(N'Âm Nhạc')--13
delete from genres where id in (14,13)
insert into movies_genres values
(1,1),(1,3),(1,5),
(2,2),
(3,2),(3,8),
(4,1),(4,4),(4,5),(4,3),
(5,5),(5,3),(5,1)

select c.name from movies a
join movies_genres b on a.id = b.movieId 
join genres c on b.genreId = c.id 
where a.title = N'Bạch Nguyệt Phạn Tinh'

select b.[view],a.title from movies a
join episodes b on a.id = b.movieId
where a.title = N'Bạch Nguyệt Phạn Tinh' and b.episode = 1

select a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL,count(b.movieId) as totalEpisode from movies a 
join episodes b on a.id = b.movieId
where a.title = N'Bạch Nguyệt Phạn Tinh'
group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL
update episodes set version = '2005/01/29'
update episodes set [view] = [view] +1, version = CURRENT_TIMESTAMP where version = '2025-01-30 00:27:34.163' and movieId = 1 and episode = 2

select * from movies where id in (select Top 12 a.movieId from movies_genres a
join genres b on a.genreId = b.id 
where b.name in (N'Tình cảm',N'Cổ trang')
group by a.movieId
order by count(b.name) desc)
select * from users
select * from genres
select * from movies 
select * from movies_genres
select * from episodes 
select * from watchLater
insert into watchLater values(9,1)
 
select sub.*,sum([view]) as [view] from (select Distinct a.* from movies a
join movies_genres b on a.id = b.movieId
join genres c on b.genreId = c.id
where title COLLATE SQL_Latin1_General_CP1_CI_AI like '%bach%' and c.name = N'Cổ Trang' and a.country = N'Trung Quốc') as sub
join episodes b on b.movieId = sub.id
group by sub.id,sub.title,sub.description,sub.category,sub.releaseDate,sub.country,sub.imageURL

select sub.*,sum([view]) as [view] from (select Distinct a.* from movies a) as sub
join episodes b on b.movieId = sub.id
group by sub.id,sub.title,sub.description,sub.category,sub.releaseDate,sub.country,sub.imageURL

select sub.* ,count(b.movieId) as totalEpisode,sum(b.[view]) as [view] from (select a.* from movies a
join watchLater b on b.movieId  = a.id
where b.userId = 9) as sub
join episodes b on sub.id = b.movieId
group by sub.id,sub.title,sub.description,sub.category,sub.releaseDate,sub.country,sub.imageURL

delete from watchLater where userId = 9 and movieId =1
select * from watchLater
--1

--2
https://www.youtube.com/watch?v=x8QelgCUvTk
--3
https://www.youtube.com/watch?v=8von2mPg908
--4
https://www.youtube.com/watch?v=OeKFGdMtei4&pp=ygUSdGh1IHNpbmggeGluaCBkZXAg
--5
https://www.youtube.com/watch?v=t_wvWD-5H5s&pp=ygUZaG9uIG5oYW4gaG9wIGRvbmcgdHJhaWxlcg%3D%3D
select * from episodes
update episodes set videoURL = 'https://www.youtube.com/embed/uvkLwS2VEHs?si=jiiurJbDjEr0QfFg' where id in (8,13)
update episodes set videoURL = 'https://www.youtube.com/embed/x8QelgCUvTk?si=wflmneb0zctgl7me' where id =9
update episodes set videoURL = 'https://www.youtube.com/embed/8von2mPg908?si=FkgbJeTia3B0BO9D' where id = 10
update episodes set videoURL = 'https://www.youtube.com/embed/sV-c3kd8k-I?si=Y3EEUen9PWx5TPv0' where id = 11
update episodes set videoURL = 'https://www.youtube.com/embed/t_wvWD-5H5s?si=fY0DP9vZmKq3HUhv' where id = 12

-- max min avg
with 
TopMostView as (
	select TOP 5 a.*,sum(b.[view]) as [view],avg(b.[view]) as totalEpisode from movies a 
join episodes b on a.id = b.movieId
group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL
order by sum(b.[view]) desc
),
TopLeastView as (
	select TOP 5 a.*,sum(b.[view]) as [view],avg(b.[view]) as totalEpisode from movies a 
join episodes b on a.id = b.movieId
group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL
order by sum(b.[view]) asc
)

select * from TopMostView
union all
select * from TopLeastView



--count movie, sum view
select count(id) as totalMovie, sum([view]) as totalView from (select a.*,sum(b.[view]) as [view],avg(b.[view]) as avgView from movies a 
join episodes b on a.id = b.movieId
group by a.id,a.title,a.[description],a.category,a.releaseDate,a.country,a.imageURL) as sub

alter table episodes alter column movieId bigint not null

alter table episodes add constraint pk_episodes
Primary key (movieId,episode)

alter table movies_genres alter column genreId int not null
alter table movies_genres add constraint pk_movies_genres primary key(movieId,genreId)
select * from movies
select * from episodes
delete from movies_genres where movieId = 6
select * from movies_genres where movieId = 6
select id from genres where name in (N'Tình Cảm',N'Anime') 
select a.*,c.name from movies a
join movies_genres b on b.movieId = a.id
join genres c on c.id = b.genreId