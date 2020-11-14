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
       (11, 2, '192.168.1.30', null, 2);

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
    settings_id   integer
        constraint devices_settings_id_pkey
            references device_settings
);
INSERT INTO devices(id, type_id, brand_id, model, serial_number, description, settings_id)
VALUES (1, 1, 1, 'M1536dnf', 'CNG8G184WR', 'Сервис менеджеры', 1),
       (2, 1, 1, 'M225rdn', 'CNB6J9J45D', 'Отдел продаж (возле кассы)', 2),
       (3, 1, 1, 'M227sdn', 'VNF3R20722', 'Отдел продаж (под лестницей)', 3),
       (4, 1, 1, 'M1536dnf', 'CNC9C4DB2P', 'Бухгалетрия', 4),
       (5, 1, 1, 'M276nw', 'CNF8G2V18Z', 'Гл. бухгалтер', 5),
       (6, 1, 1, 'M1536dnf', 'CNG8G184VW', 'Магазин', 6),
       (7, 1, 1, 'KCodes 802', '00-11-E5-02-28-E0', 'Принтер чеков в магазине', 7),
       (8, 1, 1, 'M426dw', 'PHBLM1Z631', 'Касса', 8),
       (9, 1, 1, 'M227sdn', 'VNCN900472', 'Бухгалтерия', 9),
       (10, 1, 4, 'FS-1025MFP', 'R7S9108354', 'Инженеры по гарантии', 10),
       (11, 1, 1, 'M177fw', 'CNG6H68FJS', 'Списан (на складе)', 11);
