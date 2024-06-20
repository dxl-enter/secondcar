create table car_brand
(
    car_brand_id int auto_increment comment '汽车品牌id'
        primary key,
    brand_name   varchar(25)  null comment '品牌名称',
    brand_log    varchar(150) null comment '品牌logo',
    brand_remark varchar(255) null comment '品牌简介',
    car_series   varchar(30)  null comment '车系',
    car_type     varchar(30)  null comment '车型（suv、小型车。。。）'
);

create table car_brand_all
(
    id          int(10) auto_increment
        primary key,
    name        varchar(255) not null,
    img         varchar(255) not null,
    firstletter char(3)      not null
)
    comment '品牌' engine = MyISAM;

create table car_group
(
    group_id       int auto_increment
        primary key,
    group_name     int          null,
    group_describe varchar(155) null,
    create_time    datetime     null,
    update_time    datetime     null
);

create table car_info
(
    car_id            int auto_increment comment '车辆id'
        primary key,
    car_brand         varchar(11)       null comment '汽车品牌',
    car_series        varchar(50)       null comment '车系',
    car_type          varchar(11)       null comment '车型（suv、小型车。。。）',
    car_price         double(10, 2)     null comment '价格',
    car_count         int     default 1 null comment '汽车数量',
    car_user_time     int               null comment '使用年限（单位/年）',
    transfer_count    int               null comment '过户次数',
    car_car_mileage   double(11, 2)     null comment '里程（万公里）',
    car_color         varchar(15)       null comment '汽车颜色',
    car_picture_id    int               null comment '汽车图片地址',
    car_gear_box      varchar(15)       null comment '变数箱（手动、自动、不限）',
    car_displacement  double(11, 1)     null comment '排量',
    produce_time      date              null comment '汽车生产日期',
    car_configuration varchar(255)      null comment '配置（天窗、GPS、真皮坐垫）',
    car_seat          int               null comment '座位数',
    car_fuel_type     varchar(11)       null comment '燃料类型（柴油、机油、电力。。。）',
    car_quality_time  int               null comment '质保时间（单位/年）',
    car_region_id     int               null comment '国别',
    car_front_tyre    varchar(30)       null comment '前轮胎尺寸',
    rear_tyre         varchar(30)       null comment '后轮胎尺寸',
    car_describe      varchar(255)      null comment '其他描述',
    create_time       datetime          null comment '创建时间',
    update_time       datetime          null on update CURRENT_TIMESTAMP comment '更新时间',
    car_status        tinyint default 1 not null comment '汽车状态   0-未上架；1-已下架',
    car_title         varchar(255)      null comment '汽车标题',
    user_id           int               null comment '所属买家id',
    car_picture       varchar(255)      null comment '汽车主图'
);

create table car_info_all
(
    id          int(10) auto_increment
        primary key,
    brand_id    int(10)      not null comment '品牌ID',
    group_id    int(10)      not null comment '车系组ID',
    series_id   int(10)      not null comment '车系id',
    full_name   varchar(255) not null comment '全称',
    name        varchar(255) not null comment '名字 ',
    brand_name  varchar(255) not null comment '品牌名称',
    group_name  varchar(255) not null comment '厂家名称',
    series_name varchar(255) not null comment '车系名称',
    state       int(10)      not null,
    minprice    int(10)      not null comment '最低价',
    maxprice    int(10)      not null comment '最高价',
    year        varchar(255) not null comment '年份'
)
    engine = MyISAM;

create table car_picture
(
    car_picture_id       int auto_increment comment '汽车图片id'
        primary key,
    car_url              varchar(255) null comment '汽车图片URL',
    car_picture_location int          null comment '汽车图片位置，0-显示主图；1-内部；2-外部；3-其他；4-轮播图',
    car_id               int          null comment '汽车id'
);

create table log_login
(
    id           bigint(11) auto_increment comment '登录日志id'
        primary key,
    username     varchar(50) not null comment '用户名',
    login_time   datetime    not null comment '登录时间',
    logout_time  datetime    null comment '退出时间',
    location     varchar(50) null comment '登录地点',
    ip           varchar(50) null comment 'IP地址',
    user_system  varchar(50) null comment '操作系统',
    user_browser varchar(50) null comment '浏览器'
)
    comment '登录日志表';

create table persistent_logins
(
    username  varchar(64)                         not null comment '登录的用户名',
    series    varchar(64)                         not null comment '记住我主键'
        primary key,
    token     varchar(64)                         not null comment 'token',
    last_used timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后一次使用时间'
);

create table region
(
    id       int          not null comment '区域主键'
        primary key,
    name     varchar(40)  null comment '区域名称',
    pid      int          null comment '区域上级标识',
    sname    varchar(40)  null comment '地名简称',
    level    int          null comment '区域等级',
    citycode varchar(20)  null comment '区域编码',
    yzcode   varchar(20)  null comment '邮政编码',
    mername  varchar(100) null comment '组合名称',
    Lng      float        null comment '经度',
    Lat      float        null comment '纬度',
    pinyin   varchar(100) null comment '拼音'
);

create table seller_car
(
    user_id     int                  null comment '卖家id',
    car_id      int                  null comment '车辆id',
    status      tinyint(2) default 1 not null comment '汽车状态   0-未上架；1-已下架',
    create_time datetime             null comment '创建时间',
    update_time timestamp            null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '卖家拥有的车辆';

create table sh_area
(
    id          int auto_increment comment 'ID'
        primary key,
    pid         int          null comment '父id',
    shortname   varchar(100) null comment '简称',
    name        varchar(100) null comment '名称',
    merger_name varchar(255) null comment '全称',
    level       tinyint      null comment '层级 0 1 2 省市区县',
    pinyin      varchar(100) null comment '拼音',
    code        varchar(100) null comment '长途区号',
    zip_code    varchar(100) null comment '邮编',
    first       varchar(50)  null comment '首字母',
    lng         varchar(100) null comment '经度',
    lat         varchar(100) null comment '纬度'
);

create table sys_menu
(
    menu_id     bigint auto_increment comment '菜单/按钮ID'
        primary key,
    parent_id   bigint           null comment '上级菜单ID',
    menu_name   varchar(50)      not null comment '菜单/按钮名称',
    url         varchar(50)      null comment '菜单URL',
    perms       text             null comment '权限标识',
    icon        varchar(50)      null comment '图标',
    type        char(2)          not null comment '类型 0菜单 1按钮',
    order_num   bigint           null comment '排序',
    create_time datetime         not null comment '创建时间',
    update_time datetime         null comment '修改时间',
    available   int    default 1 null comment '0：不可用，1：可用',
    open        int(1) default 1 null comment '0:不展开，1：展开'
)
    comment '菜单表';

create table sys_order
(
    order_id       int(30) auto_increment comment '订单id'
        primary key,
    order_number   varchar(100)  null comment '订单编号',
    buy_id         int           null comment '买家ID',
    sell_id        int           null comment '买家Id',
    car_id         int           null comment '汽车Id',
    car_price      double(10, 2) null comment '汽车价格',
    status         tinyint(2)    null comment '订单状态（0-未支付  1-已支付  2-取消支付 3- 创建）',
    order_describe varchar(255)  null comment '描述',
    create_time    datetime      null comment '创建时间',
    update_time    datetime      null comment '更新时间'
);

create table sys_permission
(
    permission_id int auto_increment comment '权限id'
        primary key,
    parent_id     int          not null comment '父权限id',
    name          varchar(50)  not null comment '权限名',
    url           varchar(255) null comment '权限的地址',
    type          tinyint(1)   not null comment '一级还是二级菜单',
    permission    varchar(50)  null comment '权限'
)
    charset = utf8mb4;

create table sys_region
(
    region_id int auto_increment comment '地址编号id'
        primary key,
    country   varchar(25)  null comment '国家',
    province  varchar(25)  null comment '省份/地区',
    city      varchar(25)  null comment '城市',
    district  varchar(25)  null comment '区/县',
    twon      varchar(25)  null comment '街道/镇',
    address   varchar(255) null comment '详细地址'
)
    charset = gbk;

create table sys_role
(
    role_id     int auto_increment comment '角色id'
        primary key,
    role_name   varchar(50)  not null comment '角色名称',
    description varchar(100) null comment '角色描述',
    create_time datetime     null comment '创建角色时间',
    update_Time datetime     null comment '更新角色时间',
    constraint name
        unique (role_name)
)
    charset = utf8mb4;

create table sys_role_permission
(
    role_id       int not null,
    permission_id int not null,
    primary key (role_id, permission_id)
)
    charset = utf8mb4;

create table sys_role_user
(
    user_id int not null comment '用户id',
    role_id int not null comment '角色id'
)
    charset = utf8mb4;

create table sys_user
(
    user_id          int auto_increment comment '用户id'
        primary key,
    username         varchar(15)  not null comment '用户名',
    password         varchar(150) not null comment '密码',
    name             varchar(11)  null comment '姓名',
    sex              tinyint(1)   null comment '性别，（ 0 男， 1 女）',
    avatar           varchar(255) null comment '用户头像url',
    address_id       int          null comment '用户地址id',
    province         varchar(11)  null comment '省份',
    city             varchar(11)  null comment '城市',
    area             varchar(11)  null comment '区县',
    detailed_address varchar(255) null comment '详细地址',
    phone            varchar(15)  null comment '电话',
    email            varchar(255) null comment '邮箱',
    birthday         date         null comment '生日',
    is_lock          tinyint(1)   null comment '是否锁定（0被锁定   1正常   2作废）',
    create_time      datetime     null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time      datetime     null comment '更新时间',
    constraint username
        unique (username)
)
    charset = utf8mb4;

create table sys_user_address
(
    sys_user_address_id int auto_increment comment '用户地址id'
        primary key,
    province_id         int          null comment '省份id',
    city_id             int          null comment '城市id',
    area_id             int          null comment '区县 id',
    detailed            varchar(255) null comment '详细地址',
    is_default          tinyint(2)   null,
    remark              varchar(100) null comment '备注',
    create_time         datetime     null comment '创建时间',
    update_time         datetime     null comment '修改时间'
);

create table sys_user_chat_message
(
    id          int auto_increment comment '聊天表id'
        primary key,
    from_id     int         null comment 'from_id',
    from_name   varchar(50) null comment 'from的名字/昵称',
    to_id       int         null comment 'to_id',
    to_name     varchar(50) null comment 'to的名字/昵称',
    content     text        null comment '内容',
    create_time datetime    null comment '创建时间',
    update_time datetime    null comment '更新时间',
    status      tinyint(2)  null comment '消息状态（0-已读；1-未读）'
);

create table sys_user_collect_cars
(
    user_id     int      null comment '用户id',
    car_id      int      null comment '汽车id',
    create_time datetime null comment '收藏时间'
);

create table user_address
(
    address_id       int auto_increment comment '用户地址id'
        primary key,
    region_id        int                  null comment '地址id（region_id）',
    remark           varchar(255)         null comment '备注',
    isdefault        tinyint(1) default 0 null comment '是否是默认地址（1:是 0否）',
    create_time      datetime             null comment '创建时间',
    update_time      datetime             null comment '更新时间',
    detailed_address varchar(255)         null comment '详细地址',
    user_id          int                  null comment '用户id'
);

create table user_info
(
    id       bigint(23) auto_increment comment '主键'
        primary key,
    username varchar(11)  not null comment '用户名',
    password varchar(255) not null comment '密码',
    name     varchar(15)  null comment '用户姓名',
    avatar   varchar(255) null comment '用户头像'
);

