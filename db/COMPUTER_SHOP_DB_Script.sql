INSERT INTO COMPUTER_SHOP.dbo.account (ID,address,email,fullname,hash_password,image_url,is_deleted,phone_number,[role],status,username) VALUES
	 (N'ACC_CUS1',N'456 Q3, HCM',N'an.nguyen@gmail.com',N'Nguyễn Văn An',N'e10adc3949ba59abbe56e057f20f883e',N'images/users/man-face-emotive-icon-smiling-male-character-in-blue-shirt-flat-illustration-isolated-on-white-happy-human-psychological-portrait-positive-emotions-user-avatar-for-app-web-des.jpg',0,N'0988777666',N'CUSTOMER',N'ACTIVE',N'customer01'),
	 (N'ACC_CUS2',N'789 Q7, HCM',N'binh.pham@gmail.com',N'Phạm Thị Bình',N'e10adc3949ba59abbe56e057f20f883e',N'images/users/man-face-emotive-icon-smiling-male-character-in-blue-shirt-flat-illustration-isolated-on-white-happy-human-psychological-portrait-positive-emotions-user-avatar-for-app-web-des.jpg',0,N'0977111222',N'CUSTOMER',N'ACTIVE',N'customer02'),
	 (N'ACC_CUS3',N'101 Q9, HCM',N'cuong.hoang@gmail.com',N'Hoàng Khắc Cường',N'e10adc3949ba59abbe56e057f20f883e',N'images/users/man-face-emotive-icon-smiling-male-character-in-blue-shirt-flat-illustration-isolated-on-white-happy-human-psychological-portrait-positive-emotions-user-avatar-for-app-web-des.jpg',0,N'0933444555',N'CUSTOMER',N'ACTIVE',N'customer03'),
	 (N'ACC_STAFF1',N'123 Q1, HCM',N'staff1@techshop.com',N'Trần Quản Lý',N'e10adc3949ba59abbe56e057f20f883e',N'images/users/pngtree-user-profile-avatar-png-image_10211467.png',0,N'0901234567',N'STAFF',N'ACTIVE',N'staff01'),
	 (N'ACC_STAFF2',N'45 Q2, HCM',N'staff2@techshop.com',N'Lê Hỗ Trợ',N'e10adc3949ba59abbe56e057f20f883e',N'images/users/pngtree-user-profile-avatar-png-image_10211467.png',0,N'0907654321',N'STAFF',N'ACTIVE',N'staff02');
INSERT INTO COMPUTER_SHOP.dbo.category (ID,description,is_deleted,name) VALUES
	 (N'CAT_HP',N'Tai nghe chụp tai, nhét tai',0,N'Tai nghe'),
	 (N'CAT_KEY',N'Bàn phím cơ, giả cơ',0,N'Bàn phím'),
	 (N'CAT_LAP',N'Máy tính xách tay các loại',0,N'Laptop'),
	 (N'CAT_MON',N'Màn hình máy tính',0,N'Màn hình'),
	 (N'CAT_MOU',N'Chuột gaming, văn phòng',0,N'Chuột'),
	 (N'CAT_PC',N'Máy tính bàn cấu hình cao',0,N'PC Gaming');
INSERT INTO COMPUTER_SHOP.dbo.order_items (ID,is_deleted,order_id,price,product_id,quantity) VALUES
	 (N'ITM_001',0,N'ORD_001',25500000,N'PROD_001',1),
	 (N'ITM_002',0,N'ORD_001',2800000.0,N'PROD_024',1),
	 (N'ITM_003',0,N'ORD_002',18000000,N'PROD_004',1),
	 (N'ITM_004',0,N'ORD_002',3800000.0,N'PROD_019',1),
	 (N'ITM_005',0,N'ORD_003',2400000.0,N'PROD_027',1);
INSERT INTO COMPUTER_SHOP.dbo.orders (ID,account_id,address,created_date,email,fullname,is_deleted,phone_number,status,total_price) VALUES
	 (N'ORD_001',N'ACC_CUS1',N'456 Q3, HCM','2026-03-15 10:30:00.0000000',N'an.nguyen@gmail.com',N'Nguyễn Văn An',0,N'0988777666',N'APPROVED',28300000),
	 (N'ORD_002',N'ACC_CUS2',N'789 Q7, HCM','2026-03-18 14:15:00.0000000',N'binh.pham@gmail.com',N'Phạm Thị Bình',0,N'0977111222',N'PENDING',21850000),
	 (N'ORD_003',N'ACC_CUS3',N'101 Q9, HCM','2026-03-19 09:00:00.0000000',N'cuong.hoang@gmail.com',N'Hoàng Khắc Cường',0,N'0933444555',N'APPROVED',2400000.0);
INSERT INTO COMPUTER_SHOP.dbo.product (ID,brand,category_id,cpu,description,image_url,is_deleted,name,PRICE,ram,stock_quantity,storage) VALUES
	 (N'PROD_001',N'ASUS',N'CAT_LAP',N'Core i7-12700H',N'Laptop gaming quốc dân, tản nhiệt cực mát.',N'images/products/Laptop ASUS ROG Strix G15.png',0,N'Laptop ASUS ROG Strix G15',25500000,N'16GB',15,N'512GB SSD'),
	 (N'PROD_002',N'Dell',N'CAT_LAP',N'Core i9-12900HK',N'Màn hình OLED 4K sắc nét, thiết kế doanh nhân.',N'images/products/Laptop Dell XPS 15 9520.jpg',0,N'Laptop Dell XPS 15 9520',55000000,N'32GB',5,N'1TB SSD'),
	 (N'PROD_003',N'Lenovo',N'CAT_LAP',N'Ryzen 7 5800H',N'Build kim loại, màn hình 165Hz siêu mượt.',N'images/products/Laptop Lenovo Legion 5 Pro.jpg',0,N'Laptop Lenovo Legion 5 Pro',28000000,N'16GB',20,N'512GB SSD'),
	 (N'PROD_004',N'Acer',N'CAT_LAP',N'Core i5-11400H',N'Lựa chọn hàng đầu cho sinh viên ngân sách vừa.',N'images/products/Laptop Acer Nitro 5 Tiger.jpg',0,N'Laptop Acer Nitro 5 Tiger',18000000,N'8GB',30,N'512GB SSD'),
	 (N'PROD_005',N'HP',N'CAT_LAP',N'Ryzen 5 6600H',N'Thiết kế tối giản, thanh lịch.',N'images/products/Laptop HP Victus 16.jpg',0,N'Laptop HP Victus 16',19500000,N'16GB',25,N'512GB SSD'),
	 (N'PROD_006',N'MSI',N'CAT_LAP',N'Core i7-11800H',N'Bàn phím LED đỏ đặc trưng, vũ khí sắc bén.',N'images/products/Laptop MSI Katana GF66.jpg',0,N'Laptop MSI Katana GF66',21000000,N'8GB',12,N'512GB SSD'),
	 (N'PROD_007',N'Custom',N'CAT_PC',N'Core i5-12400F',N'PC Gaming lắp ráp sẵn, chơi mượt Esport.',N'images/products/PC Gaming GVN VIP.png',0,N'PC Gaming GVN VIP',15500000,N'16GB',12,N'500GB SSD'),
	 (N'PROD_008',N'MSI',N'CAT_PC',N'Core i7-13700F',N'PC đồng bộ cực bền bỉ từ MSI.',N'images/products/PC Gaming MSI MAG Infinite.jpg',0,N'PC Gaming MSI MAG Infinite',35000000,N'32GB',8,N'1TB SSD'),
	 (N'PROD_009',N'ASUS',N'CAT_PC',N'Core i9-14900K',N'Cấu hình tối thượng chiến game AAA 4K.',N'images/products/PC Gaming ASUS ROG MKT.jpg',0,N'PC Gaming ASUS ROG MKT',85000000,N'64GB',3,N'2TB NVMe'),
	 (N'PROD_010',N'Custom',N'CAT_PC',N'Ryzen 5 7600X',N'Tối ưu hiệu năng trên giá thành với AMD.',N'images/products/PC Gaming Ryzen AMD.png',0,N'PC Gaming Ryzen AMD',18500000,N'16GB',10,N'512GB SSD');
INSERT INTO COMPUTER_SHOP.dbo.product (ID,brand,category_id,cpu,description,image_url,is_deleted,name,PRICE,ram,stock_quantity,storage) VALUES
	 (N'PROD_011',N'Custom',N'CAT_PC',N'Core i7-12700K',N'Thiết kế vỏ case NZXT tinh tế.',N'images/products/PC Gaming NZXT iCUE.jpg',0,N'PC Gaming NZXT iCUE',42000000,N'32GB',5,N'1TB NVMe'),
	 (N'PROD_012',N'Corsair',N'CAT_PC',N'Core i9-13900K',N'Siêu phẩm PC nhỏ gọn, quái vật hiệu năng.',N'images/products/PC Gaming Corsair One.png',0,N'PC Gaming Corsair One',90000000,N'64GB',2,N'2TB NVMe'),
	 (N'PROD_013',N'LG',N'CAT_MON',N'',N'27 inch 2K, 144Hz, tấm nền IPS 1ms.',N'images/products/LG UltraGear 27 inch.jpg',0,N'Màn hình LG UltraGear 27 inch',6500000.0,N'',25,N''),
	 (N'PROD_014',N'Dell',N'CAT_MON',N'',N'Chuẩn màu đồ họa 100% sRGB, viền siêu mỏng.',N'images/products/Dell UltraSharp 27 inch.jpg',0,N'Màn hình Dell UltraSharp 27 inch',11000000,N'',15,N''),
	 (N'PROD_015',N'Samsung',N'CAT_MON',N'',N'Độ cong 1000R, 165Hz chiến game siêu đã.',N'images/products/Samsung Odyssey G5.jpg',0,N'Màn hình cong Samsung Odyssey G5',7200000.0,N'',18,N''),
	 (N'PROD_016',N'ASUS',N'CAT_MON',N'',N'Màn hình quốc dân, 165Hz cực mượt.',N'images/products/ASUS TUF Gaming 24 inch.jpg',0,N'Màn hình ASUS TUF Gaming 24 inch',4200000.0,N'',30,N''),
	 (N'PROD_017',N'AOC',N'CAT_MON',N'',N'Màn hình giá rẻ có tấm nền IPS và 144Hz.',N'images/products/AOC 24G2 24 inch.jpg',0,N'Màn hình AOC 24G2 24 inch',3800000.0,N'',20,N''),
	 (N'PROD_018',N'E-Dra',N'CAT_KEY',N'',N'Bàn phím cơ giá rẻ, layout gọn gàng.',N'images/products/E-Dra EK384.jpg',0,N'Bàn phím cơ E-Dra EK384',850000.0,N'',40,N''),
	 (N'PROD_019',N'Keychron',N'CAT_KEY',N'',N'Khung nhôm nguyên khối, gõ cực êm.',N'images/products/Keychron Q1 V2.jpg',0,N'Bàn phím cơ Keychron Q1 V2',3800000.0,N'',10,N''),
	 (N'PROD_020',N'Razer',N'CAT_KEY',N'',N'Green Switch clicky sướng tai, LED Chroma.',N'images/products/Razer BlackWidow V3.jpg',0,N'Bàn phím Razer BlackWidow V3',2900000.0,N'',20,N'');
INSERT INTO COMPUTER_SHOP.dbo.product (ID,brand,category_id,cpu,description,image_url,is_deleted,name,PRICE,ram,stock_quantity,storage) VALUES
	 (N'PROD_021',N'Logitech',N'CAT_KEY',N'',N'Thiết kế TKL cho game thủ eSports.',N'images/products/Logitech G Pro X.jpg',0,N'Bàn phím Logitech G Pro X',3100000.0,N'',15,N''),
	 (N'PROD_022',N'Akko',N'CAT_KEY',N'',N'Màu sắc bắt mắt, switch tự làm.',N'images/products/Akko 3098B.jpg',0,N'Bàn phím cơ Akko 3098B',2100000.0,N'',25,N''),
	 (N'PROD_023',N'Logitech',N'CAT_MOU',N'',N'Chuột quốc dân, pin xài nửa năm.',N'images/products/Logitech G304.jpg',0,N'Chuột không dây Logitech G304',850000.0,N'',50,N''),
	 (N'PROD_024',N'Logitech',N'CAT_MOU',N'',N'Siêu nhẹ chỉ 63g, lướt êm ái trên pad.',N'images/products/Logitech G Pro X Superlight.png',0,N'Chuột không dây Logitech G Pro X Superlight',2800000.0,N'',30,N''),
	 (N'PROD_025',N'Razer',N'CAT_MOU',N'',N'Form công thái học, cảm biến 30K DPI.',N'images/products/Razer DeathAdder V3 Pro.png',0,N'Chuột Razer DeathAdder V3 Pro',3200000.0,N'',25,N''),
	 (N'PROD_026',N'Zowie',N'CAT_MOU',N'',N'Chuyên dụng cho dân FPS chuyên nghiệp.',N'images/products/Zowie EC2-CW.png',0,N'Chuột Zowie EC2-CW',3500000.0,N'',10,N''),
	 (N'PROD_027',N'HyperX',N'CAT_HP',N'',N'Đeo êm ái nhất, mic lọc ồn cực tốt.',N'images/products/Tai nghe HyperX Cloud III.jpg',0,N'Tai nghe HyperX Cloud III',2400000.0,N'',30,N''),
	 (N'PROD_028',N'Sony',N'CAT_HP',N'',N'Tối ưu cho PS5 và PC, âm thanh vòm 3D.',N'images/products/Tai nghe Sony Pulse 3D.jpg',0,N'Tai nghe Sony Pulse 3D',2100000.0,N'',15,N''),
	 (N'PROD_029',N'Logitech',N'CAT_HP',N'',N'Tai nghe không dây thời trang, dải LED RGB.',N'images/products/Tai nghe Logitech G733 Lightspeed.jpg',0,N'Tai nghe Logitech G733 Lightspeed',2900000.0,N'',22,N''),
	 (N'PROD_030',N'Razer',N'CAT_HP',N'',N'Chất âm bass sâu lực, thích hợp EDM.',N'images/products/Tai nghe Logitech G733 Lightspeed.jpg',0,N'Tai nghe Razer Kraken V3',2500000.0,N'',18,N'');
INSERT INTO COMPUTER_SHOP.dbo.review (ID,account_id,comment,created_at,is_deleted,product_id,rating) VALUES
	 (N'REV_001',N'ACC_CUS1',N'Laptop xài cực sướng, tản nhiệt tốt, chơi game AAA thoải mái.','2026-03-16 09:00:00.0000000',0,N'PROD_001',5),
	 (N'REV_002',N'ACC_CUS1',N'Chuột siêu nhẹ, lướt êm nhưng form hơi nhỏ.','2026-03-16 09:05:00.0000000',0,N'PROD_024',4),
	 (N'REV_003',N'ACC_CUS3',N'Mic lọc ồn cực tốt, đeo lâu không bị đau tai. Đáng tiền!','2026-03-19 14:00:00.0000000',0,N'PROD_027',5),
	 (N'REV_004',N'ACC_CUS2',N'Hàng nguyên seal, máy chạy mượt, shop đóng gói cẩn thận.','2026-03-10 10:00:00.0000000',0,N'PROD_004',5);
INSERT INTO COMPUTER_SHOP.dbo.wishlist (ID,account_id,created_at,is_deleted,product_id) VALUES
	 (N'WISH_001',N'ACC_CUS1','2026-03-19 08:00:00.0000000',0,N'PROD_008'),
	 (N'WISH_002',N'ACC_CUS1','2026-03-19 08:10:00.0000000',0,N'PROD_014'),
	 (N'WISH_003',N'ACC_CUS2','2026-03-17 20:30:00.0000000',0,N'PROD_002'),
	 (N'WISH_004',N'ACC_CUS2','2026-03-18 10:00:00.0000000',0,N'PROD_025'),
	 (N'WISH_005',N'ACC_CUS3','2026-03-19 15:30:00.0000000',0,N'PROD_019');
