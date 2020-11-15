--liquibase formatted sql

--changeset an.krasnov:1

DROP TABLE IF EXISTS devices;
DROP TABLE IF EXISTS device_settings;
DROP TABLE IF EXISTS protocols;
DROP TABLE IF EXISTS device_types;
DROP TABLE IF EXISTS device_brands;
DROP TABLE IF EXISTS page_count_url;


create table protocols
(
    id    serial not null
        constraint protocols_pkey
            primary key,
    value varchar(255) unique
);
INSERT INTO protocols (id, value)
VALUES (1, 'https://'),
       (2, 'http://'),
       (3, 'ftp://');

create table device_types
(
    id          serial not null
        constraint device_types_pkey
            primary key,
    name        varchar(255) unique,
    description varchar(255),
    icon        varchar(255)
);
INSERT INTO device_types (id, name, description, icon)
VALUES (1, 'mfu', 'МФУ', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c04818531.png'),
       (2, 'phone', 'IP телефон', 'https://yealink.ru/upload/iblock/110/yealink-w52p.jpg'),
       (3, 'notebook', 'Ноутбук',
        'https://www.www8-hp.com/ru/ru/images/i_02_elitebook_1040_tcm172_2515827_tcm172_2515729_tcm172-2515827.jpg'),
       (4, 'pc', 'ПК',
        'https://www.www8-hp.com/ru/ru/images/i_03_elitedesk_800_desktop_tower_tcm172_2653496_tcm172_2520351_tcm172-2653496.jpg'),
       (5, 'server', 'Сервер', 'http://www.supermicro.ru/pics/products/small/21'),
       (6, 'router', 'Маршрутизатор',
        'https://mikrotik.ru/upload/resize_cache/iblock/cf5/400_400_1/cf549bf61dafede5130ed5073225305f.jpg'),
       (7, 'camera', 'IP камера', 'https://www.itech-cctv.ru/upload/iblock/74c/picture_big_n0000134486_ofb_big.jpg');

create table device_brands
(
    id   serial not null
        constraint device_brands_pkey
            primary key,
    name varchar(255) unique,
    site varchar(255),
    icon varchar(255)
);
INSERT INTO device_brands (id, name, site, icon)
VALUES (1, 'HP', 'https://www8.hp.com/', 'https://1000logos.net/wp-content/uploads/2017/02/Colors-HP-Logo.jpg'),
       (2, 'Mikrotik', 'https://mikrotik.ru/',
        'https://sun9-43.userapi.com/GOU_IvOf3MI78VlRCuMI6-vmtMw76On2ITikqQ/3A7WDMVT5P4.jpg'),
       (3, 'Yealink', 'https://www.yealink.ru/', 'https://rrc.ru/uploads/manuf_svg/Yealink.svg'),
       (4, 'Kyocera', 'https://russia.kyocera.com/', 'https://russia.kyocera.com/_assets/img/common/logo.svg'),
       (5, 'Dlink', 'http://www.dlink.ru/', 'http://www.dlink.ru/i/logo.svg'),
       (6, 'Zyxel', 'https://www.zyxel.com/ru/ru/', 'https://www.zyxel.com/library/assets/coverpage/zyxel-logo.png'),
       (7, 'ASUS', 'https://www.asus.com/ru/', 'https://cdn.worldvectorlogo.com/logos/asus-6630.svg'),
       (8, 'ACER', 'https://www.acer.com/ac/ru/RU/content/home',
        'https://static.acer.com/up/Resource/Libraries/Acer/4436/images/logo.svg'),
       (9, 'АйТек ПРО', 'https://www.itech-cctv.ru/',
        'https://www.itech-cctv.ru/local/templates/itechpro_v3/images/logo.png');

create table page_count_url
(
    id      serial not null
        constraint page_count_url_pkey
            primary key,
    value   varchar(255),
    pattern varchar(255)
);
INSERT INTO page_count_url (id, value, pattern)
VALUES (1, '/info_configuration.html?tab=Status&menu=DevConfig', 'Всего оттисков:'),
       (2, '/SSI/info_configuration.htm', 'Всего отпечатков:');

create table device_settings
(
    id                serial not null
        constraint device_settings_pkey
            primary key,
    protocol_id       integer
        constraint device_settings_protocol_id_pkey
            references protocols,
    host              varchar(255),
    port              integer,
    page_count_url_id integer
        constraint device_settings_page_count_url_id_pkey
            references page_count_url
);
INSERT INTO device_settings(id, protocol_id, host, port, page_count_url_id)
VALUES (1, 2, '192.168.1.20', null, 1),
       (2, 2, '192.168.1.21', null, 1),
       (3, 2, '192.168.1.22', null, 1),
       (4, 2, '192.168.1.23', null, 1),
       (5, 2, '192.168.1.24', null, 1),
       (6, 2, '192.168.1.25', null, 1),
       (7, 2, '192.168.1.26', null, null),
       (8, 2, '192.168.1.27', null, 1),
       (9, 2, '192.168.1.28', null, 1),
       (10, 2, '192.168.1.29', null, 1),
       (11, 2, '192.168.1.30', null, 2),

       (12, 2, '192.168.1.190', null, null),
       (13, 2, '192.168.1.191', null, null),
       (14, 2, '192.168.1.192', null, null),
       (15, 2, '192.168.1.193', null, null),
       (16, 2, '192.168.1.194', null, null),
       (17, 2, '192.168.1.195', null, null),
       (18, 2, '192.168.1.196', null, null),
       (19, 2, '192.168.1.197', null, null),
       (20, 2, '192.168.1.198', null, null),
       (21, 2, '192.168.1.199', null, null),
       (22, 2, '192.168.1.200', null, null),
       (23, 2, '192.168.1.201', null, null),
       (24, 2, '192.168.1.202', null, null),
       (25, 2, '192.168.1.203', null, null),
       (26, 2, '192.168.1.204', null, null),
       (27, 2, '192.168.1.205', null, null),
       (28, 2, '192.168.1.206', null, null),
       (29, 2, '192.168.1.207', null, null),
       (30, 2, '192.168.1.208', null, null),

       (31, 2, '192.168.1.150', null, null),
       (32, 2, '192.168.1.151', null, null),
       (33, 2, '192.168.1.152', null, null),
       (34, 2, '192.168.1.153', null, null),
       (35, 2, '192.168.1.154', null, null),
       (36, 2, '192.168.1.155', null, null),
       (37, 2, '192.168.1.156', null, null),
       (38, 2, '192.168.1.157', null, null),
       (39, 2, '192.168.1.158', null, null),
       (40, 2, '192.168.1.159', null, null),
       (41, 2, '192.168.1.160', null, null),

       (42, 2, '192.168.1.170', null, null),
       (43, 2, '192.168.1.171', null, null),
       (44, 2, '192.168.1.172', null, null),
       (45, 2, '192.168.1.173', null, null),
       (46, 2, '192.168.1.174', null, null),
       (47, 2, '192.168.1.175', null, null),
       (48, 2, '192.168.1.176', null, null),
       (49, 2, '192.168.1.177', null, null),
       (50, 2, '192.168.1.178', null, null),
       (51, 2, '192.168.1.179', null, null),
       (52, 2, '192.168.1.180', null, null),
       (53, 2, '192.168.1.181', null, null),
       (54, 2, '192.168.1.182', null, null),
       (55, 2, '192.168.1.183', null, null)
       ;

create table devices
(
    id            serial not null
        constraint devices_pkey
            primary key,
    type_id       integer
        constraint devices_type_id_pkey
            references device_types,
    brand_id      integer
        constraint devices_brand_id_pkey
            references device_brands,
    model         varchar(255),
    serial_number varchar(255),
    description   varchar(255),
    image    varchar(255),
    settings_id   integer
        constraint devices_settings_id_pkey
            references device_settings,
    check_available boolean default true,
    check_page_count boolean default false,
    notify_tlg boolean default false,
    notify_email boolean default false
);
INSERT INTO devices(id, type_id, brand_id, check_page_count, model, serial_number, description, image, settings_id)
VALUES (1, 1, 1, true, 'M1536dnf', 'CNG8G184WR', 'Сервис менеджеры', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c02944267.png', 1),
       (2, 1, 1, true, 'M225rdn', 'CNB6J9J45D', 'Отдел продаж (возле кассы)', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c04411794.png', 2),
       (3, 1, 1, true, 'M227sdn', 'VNF3R20722', 'Отдел продаж (под лестницей)', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c05300810.png', 3),
       (4, 1, 1, true, 'M1536dnf', 'CNC9C4DB2P', 'Бухгалетрия', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c02944267.png', 4),
       (5, 1, 1, true, 'M276nw', 'CNF8G2V18Z', 'Гл. бухгалтер', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c03122579.png', 5),
       (6, 1, 1, true, 'M1536dnf', 'CNG8G184VW', 'Магазин', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c02944267.png', 6),
       (7, 1, 1, false , 'Argox OS-2130D', '00-11-E5-02-28-E0', 'Принтер этикеток в магазине', 'https://orenburg.smartcode.ru/uploads/items/image/smedium/14249.jpg', 7),
       (8, 1, 1, true, 'M426dw', 'PHBLM1Z631', 'Касса', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c04868996.png', 8),
       (9, 1, 1, true, 'M227sdn', 'VNCN900472', 'Бухгалтерия', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c05300810.png', 9),
       (10, 1, 4, false, 'FS-1025MFP', 'R7S9108354', 'Инженеры по гарантии', 'https://c.dns-shop.ru/thumb/st4/fit/320/250/6b3e421dbbe6a7d3119ae7b78ff978bb/501652b5d9e0b2432ca5160423918c1dd77d0785116b887d560076a19a562f53.jpg', 10),
       (11, 1, 1, false, 'M177fw', 'CNG6H68FJS', 'Списан (на складе)', 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c03773181.png', 11),

       (12, 2, 3, false, 'SIP-T19 E2', 'b/n', '106 [Бухгалтерия]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 12),
       (13, 2, 3, false, 'SIP-T19 E2', 'b/n', '121 [Учебный класс]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 13),
       (14, 2, 3, false, 'SIP-T19 E2', 'b/n', '103 [Гл. бух]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 14),
       (15, 2, 3, false, 'SIP-T19 E2', 'b/n', '102 [Приемная]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 15),
       (16, 2, 3, false, 'SIP-T19 E2', 'b/n', '101 [Директор]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 16),
       (17, 2, 3, false, 'SIP-T19 E2', 'b/n', '104 [Маркетолог]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 17),
       (18, 2, 3, false, 'SIP-T19 E2', 'b/n', '110 [Ресепшн]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 18),
       (19, 2, 3, false, 'SIP-T19 E2', 'b/n', '115 [Отдел кредитования]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 19),
       (20, 2, 3, false, 'SIP-T19 E2', 'b/n', '109 [Касса]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 20),
       (21, 2, 3, false, 'W52P', 'b/n', '119 [Сервис менеджеры]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 21),
       (22, 2, 3, false, 'W52P', 'b/n', '113 [Сервис менеджеры]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 22),
       (23, 2, 3, false, 'SIP-T19 E2', 'b/n', '124 [РОП]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 23),
       (24, 2, 3, false, 'W52P', 'b/n', '122 [Отдел продаж]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 24),
       (25, 2, 3, false, 'W52P', 'b/n', '120 [Отдел продаж]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 25),
       (26, 2, 3, false, 'W52P', 'b/n', '118 [Мастер цеха]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 26),
       (27, 2, 3, false, 'SIP-T19 E2', 'b/n', '107 [Склад]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 27),
       (28, 2, 3, false, 'SIP-T19 E2', 'b/n', '117 [Инженер по гарантии]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink/yealink-t19-e2-34-gizon-ru-400x500.jpg', 28),
       (29, 2, 3, false, 'W52P', 'b/n', '105 [Бухгалтерия]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 29),
       (30, 2, 3, false, 'W52P', 'b/n', '108 [Магазин]', 'https://www.yealink-russia.ru/image/cache/catalog/data/Yealink-w52p-yealink-russia-ru-1-400x500.jpg', 30),

       (31, 7, 9, false, 'Купольная камера', 'b/n', 'Camera 1', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 31),
       (32, 7, 9, false, 'Купольная камера', 'b/n', 'Camera 2', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 32),
       (33, 7, 9, false, 'Купольная камера', 'b/n', 'Camera 3', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 33),
       (34, 7, 9, false, 'Купольная камера', 'b/n', 'Camera 4', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 34),
       (35, 7, 9, false, 'Купольная камера', 'b/n', 'TraidIn', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 35),
       (36, 7, 9, false, 'Купольная камера', 'b/n', 'TraidIn магазин', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 36),
       (37, 7, 9, false, 'Купольная камера', 'b/n', 'Доп. оборудование', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 37),
       (38, 7, 9, false, 'Купольная камера', 'b/n', 'Шиномонтаж', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 38),
       (39, 7, 9, false, 'Купольная камера', 'b/n', 'Автозапчасти', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 39),
       (40, 7, 9, false, 'Купольная камера', 'b/n', 'Магазин торец', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 40),
       (41, 7, 9, false, 'Купольная камера', 'b/n', 'Автомойка', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 41),

       (42, 7, 9, false, 'Купольная камера', 'b/n', 'Ворота', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 42),
       (43, 7, 9, false, 'Купольная камера', 'b/n', 'Касса', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 43),
       (44, 7, 9, false, 'Купольная камера', 'b/n', 'Сервис менеджеры', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 44),
       (45, 7, 9, false, 'Купольная камера', 'b/n', 'Главный вход', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 45),
       (46, 7, 9, false, 'Купольная камера', 'b/n', 'Над лестницей', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 46),
       (47, 7, 9, false, 'Купольная камера', 'b/n', 'Подъемник', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 47),
       (48, 7, 9, false, 'Купольная камера', 'b/n', 'Въезд в сервис', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 48),
       (49, 7, 9, false, 'Купольная камера', 'b/n', 'Вход', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 49),
       (50, 7, 9, false, 'Купольная камера', 'b/n', 'Касса внутри', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 50),
       (51, 7, 9, false, 'Купольная камера', 'b/n', 'Выдача автомобилей', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 51),
       (52, 7, 9, false, 'Купольная камера', 'b/n', 'Зона отдыха', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 52),
       (53, 7, 9, false, 'Купольная камера', 'b/n', 'Магазин', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 53),
       (54, 7, 9, false, 'Купольная камера', 'b/n', 'Въезд в сервис', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 54),
       (55, 7, 9, false, 'Купольная камера', 'b/n', 'Инженеры', 'https://www.itech-cctv.ru/upload/resize_cache/iblock/283/200_200_0/picture_small_n0000042438_ipe_dp-small.jpg', 55)
       ;