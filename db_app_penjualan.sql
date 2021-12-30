/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : 127.0.0.1:3306
 Source Schema         : db_app_penjualan

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 31/12/2021 02:37:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_barang
-- ----------------------------
DROP TABLE IF EXISTS `tb_barang`;
CREATE TABLE `tb_barang`  (
  `id` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `harga_beli` int(10) NULL DEFAULT NULL,
  `id_supplier` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_barang
-- ----------------------------
INSERT INTO `tb_barang` VALUES ('4545423235566', 'Kapal Api', 11000, 'S001');
INSERT INTO `tb_barang` VALUES ('847757773299', 'Surya', 195000, 'S001');
INSERT INTO `tb_barang` VALUES ('9383884849', 'Gudang Garam', 198000, 'S001');

-- ----------------------------
-- Table structure for tb_barang_rusak
-- ----------------------------
DROP TABLE IF EXISTS `tb_barang_rusak`;
CREATE TABLE `tb_barang_rusak`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_barang` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `satuan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `jumlah` double NULL DEFAULT NULL,
  `keterangan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_barang_rusak
-- ----------------------------

-- ----------------------------
-- Table structure for tb_barcode
-- ----------------------------
DROP TABLE IF EXISTS `tb_barcode`;
CREATE TABLE `tb_barcode`  (
  `id` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `harga` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_barcode
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cashflow
-- ----------------------------
DROP TABLE IF EXISTS `tb_cashflow`;
CREATE TABLE `tb_cashflow`  (
  `id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tanggal` date NOT NULL,
  `jam` time NULL DEFAULT NULL,
  `jenis` enum('Pengeluaran','Pemasukan') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `jumlah` int(11) NOT NULL,
  `keterangan` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `user_id` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_cashflow
-- ----------------------------

-- ----------------------------
-- Table structure for tb_detail_barang
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_barang`;
CREATE TABLE `tb_detail_barang`  (
  `id_barang` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `satuan` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `stok` double NULL DEFAULT NULL,
  `harga_jual` int(10) UNSIGNED NULL DEFAULT NULL,
  `isi` double NULL DEFAULT NULL,
  `satuan_isi` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `harga_beli` double NULL DEFAULT NULL,
  PRIMARY KEY (`id_barang`, `satuan`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_barang
-- ----------------------------
INSERT INTO `tb_detail_barang` VALUES ('4545423235566', 'Pack', 23, 12000, 10, 'Pcs', 11000);
INSERT INTO `tb_detail_barang` VALUES ('4545423235566', 'Pcs', 10, 1500, 0, '-', 1100);
INSERT INTO `tb_detail_barang` VALUES ('847757773299', 'Pack', 9, 205000, 10, 'Pcs', 195000);
INSERT INTO `tb_detail_barang` VALUES ('847757773299', 'Pcs', 5, 25000, 0, '-', 19500);
INSERT INTO `tb_detail_barang` VALUES ('9383884849', 'Pack', 23, 210000, 10, 'Pcs', 198000);

-- ----------------------------
-- Table structure for tb_detail_hutang
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_hutang`;
CREATE TABLE `tb_detail_hutang`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tgl_bayar` date NOT NULL,
  `jml_bayar` bigint(30) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 434 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_hutang
-- ----------------------------
INSERT INTO `tb_detail_hutang` VALUES (432, 'KR/291221/1', '2021-12-29', 100000);
INSERT INTO `tb_detail_hutang` VALUES (433, 'KR/291221/1', '2021-12-29', 265000);

-- ----------------------------
-- Table structure for tb_detail_pembelian
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_pembelian`;
CREATE TABLE `tb_detail_pembelian`  (
  `no_faktur` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id_barang` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `satuan` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `harga_beli` int(11) NULL DEFAULT NULL,
  `qty` double NULL DEFAULT NULL,
  PRIMARY KEY (`no_faktur`, `id_barang`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_pembelian
-- ----------------------------

-- ----------------------------
-- Table structure for tb_detail_return_pembelian
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_return_pembelian`;
CREATE TABLE `tb_detail_return_pembelian`  (
  `id_return` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_barang` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `satuan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `jumlah` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `harga` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id_return`, `id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_return_pembelian
-- ----------------------------

-- ----------------------------
-- Table structure for tb_detail_return_penjualan
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_return_penjualan`;
CREATE TABLE `tb_detail_return_penjualan`  (
  `id_return` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_barang` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `satuan` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `jumlah` double NULL DEFAULT NULL,
  `harga` int(11) NULL DEFAULT NULL,
  `kelayakan` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `keterangan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_return`, `id_barang`, `satuan`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_return_penjualan
-- ----------------------------

-- ----------------------------
-- Table structure for tb_detail_transaksi
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_transaksi`;
CREATE TABLE `tb_detail_transaksi`  (
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_barang` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `harga_jual` int(10) NOT NULL,
  `satuan` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `qty` double NOT NULL,
  `harga_beli` double NULL DEFAULT NULL,
  PRIMARY KEY (`no_faktur`, `id_barang`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_transaksi
-- ----------------------------
INSERT INTO `tb_detail_transaksi` VALUES ('TU/291221/1', '847757773299', 205000, 'Pack', 1, 195000);
INSERT INTO `tb_detail_transaksi` VALUES ('TU/291221/2', '9383884849', 210000, 'Pack', 1, 198000);
INSERT INTO `tb_detail_transaksi` VALUES ('TU/291221/3', '847757773299', 205000, 'Pack', 1, 195000);
INSERT INTO `tb_detail_transaksi` VALUES ('TU/291221/3', '9383884849', 210000, 'Pack', 1, 198000);
INSERT INTO `tb_detail_transaksi` VALUES ('TU/291221/4', '4545423235566', 12000, 'Pack', 1, 11000);

-- ----------------------------
-- Table structure for tb_detail_transaksi_kredit
-- ----------------------------
DROP TABLE IF EXISTS `tb_detail_transaksi_kredit`;
CREATE TABLE `tb_detail_transaksi_kredit`  (
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_barang` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `harga_jual` int(10) UNSIGNED NOT NULL,
  `satuan` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `qty` double UNSIGNED NOT NULL,
  `harga_beli` double NULL DEFAULT NULL,
  PRIMARY KEY (`no_faktur`, `id_barang`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_detail_transaksi_kredit
-- ----------------------------
INSERT INTO `tb_detail_transaksi_kredit` VALUES ('KR/291221/1', '4545423235566', 12000, 'Pack', 20, 11000);
INSERT INTO `tb_detail_transaksi_kredit` VALUES ('KR/291221/1', '847757773299', 25000, 'Pcs', 5, 19500);

-- ----------------------------
-- Table structure for tb_diskon
-- ----------------------------
DROP TABLE IF EXISTS `tb_diskon`;
CREATE TABLE `tb_diskon`  (
  `id_barang` char(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `satuan` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `diskon` int(10) NULL DEFAULT 0,
  PRIMARY KEY (`id_barang`, `satuan`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_diskon
-- ----------------------------

-- ----------------------------
-- Table structure for tb_hutang
-- ----------------------------
DROP TABLE IF EXISTS `tb_hutang`;
CREATE TABLE `tb_hutang`  (
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `sisa_hutang` bigint(20) NOT NULL,
  PRIMARY KEY (`no_faktur`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_hutang
-- ----------------------------
INSERT INTO `tb_hutang` VALUES ('KR/291221/1', 0);

-- ----------------------------
-- Table structure for tb_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_info`;
CREATE TABLE `tb_info`  (
  `indeks` enum('0','1') CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_karyawan
-- ----------------------------
DROP TABLE IF EXISTS `tb_karyawan`;
CREATE TABLE `tb_karyawan`  (
  `id` char(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `username` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `bagian` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `alamat` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `telp` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_karyawan
-- ----------------------------
INSERT INTO `tb_karyawan` VALUES ('admin', 'admin', 'Sistem Administrator', 'admin', 'Jogja', '082350001930');

-- ----------------------------
-- Table structure for tb_pelanggan
-- ----------------------------
DROP TABLE IF EXISTS `tb_pelanggan`;
CREATE TABLE `tb_pelanggan`  (
  `id` varchar(9) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nama_toko` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `alamat` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `telp` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_pelanggan
-- ----------------------------
INSERT INTO `tb_pelanggan` VALUES ('00001', 'Namanya', '-', 'Jogja', '0823777444');
INSERT INTO `tb_pelanggan` VALUES ('UMUM', 'UMUM', 'UMUM', 'UMUM', 'UMUM');

-- ----------------------------
-- Table structure for tb_pembelian
-- ----------------------------
DROP TABLE IF EXISTS `tb_pembelian`;
CREATE TABLE `tb_pembelian`  (
  `no_faktur` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `sub_total` int(11) NULL DEFAULT NULL,
  `supplier` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `tanggal` date NULL DEFAULT NULL,
  `jatuh_tempo` date NULL DEFAULT NULL,
  PRIMARY KEY (`no_faktur`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_pembelian
-- ----------------------------

-- ----------------------------
-- Table structure for tb_properties
-- ----------------------------
DROP TABLE IF EXISTS `tb_properties`;
CREATE TABLE `tb_properties`  (
  `nama_toko` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `alamat` varchar(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `telp` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `npwp` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`nama_toko`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_properties
-- ----------------------------
INSERT INTO `tb_properties` VALUES ('Nama Tokonya', 'Jogja', '093838838', '838388383');

-- ----------------------------
-- Table structure for tb_return_pembelian
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_pembelian`;
CREATE TABLE `tb_return_pembelian`  (
  `id` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tanggal` date NULL DEFAULT NULL,
  `distributor` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `karyawan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `subtotal` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_return_pembelian
-- ----------------------------

-- ----------------------------
-- Table structure for tb_return_penjualan
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_penjualan`;
CREATE TABLE `tb_return_penjualan`  (
  `id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_pelanggan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `tanggal` date NULL DEFAULT NULL,
  `subtotal` int(11) NULL DEFAULT NULL,
  `id_karyawan` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_return_penjualan
-- ----------------------------
INSERT INTO `tb_return_penjualan` VALUES ('RET/291221/1', 'UMUM', '2021-12-29', 205000, 'admin');

-- ----------------------------
-- Table structure for tb_satuan
-- ----------------------------
DROP TABLE IF EXISTS `tb_satuan`;
CREATE TABLE `tb_satuan`  (
  `id` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `satuan` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_satuan
-- ----------------------------
INSERT INTO `tb_satuan` VALUES ('001', 'Karton');
INSERT INTO `tb_satuan` VALUES ('002', 'Pcs');
INSERT INTO `tb_satuan` VALUES ('003', 'Lusin');
INSERT INTO `tb_satuan` VALUES ('004', 'Kodi');
INSERT INTO `tb_satuan` VALUES ('005', 'Pack');

-- ----------------------------
-- Table structure for tb_supplier
-- ----------------------------
DROP TABLE IF EXISTS `tb_supplier`;
CREATE TABLE `tb_supplier`  (
  `id` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `nama` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `alamat` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `telp` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_supplier
-- ----------------------------
INSERT INTO `tb_supplier` VALUES ('S001', 'CV. Mitra ', 'Jogja', '02837477483');

-- ----------------------------
-- Table structure for tb_tagihan
-- ----------------------------
DROP TABLE IF EXISTS `tb_tagihan`;
CREATE TABLE `tb_tagihan`  (
  `id` char(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tagihan` bigint(30) NOT NULL,
  `sisa_tagihan` bigint(30) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_tagihan
-- ----------------------------

-- ----------------------------
-- Table structure for tb_transaksi
-- ----------------------------
DROP TABLE IF EXISTS `tb_transaksi`;
CREATE TABLE `tb_transaksi`  (
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_pelanggan` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `id_karyawan` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `total_uang` int(11) NULL DEFAULT NULL,
  `jam` time NULL DEFAULT NULL,
  PRIMARY KEY (`no_faktur`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_transaksi
-- ----------------------------
INSERT INTO `tb_transaksi` VALUES ('TU/291221/1', 'UMUM', 205000, '2021-12-29', 'admin', 250000, '15:01:42');
INSERT INTO `tb_transaksi` VALUES ('TU/291221/2', 'UMUM', 210000, '2021-12-29', 'admin', 250000, '15:01:42');
INSERT INTO `tb_transaksi` VALUES ('TU/291221/3', 'UMUM', 415000, '2021-12-29', 'admin', 500000, '15:07:37');
INSERT INTO `tb_transaksi` VALUES ('TU/291221/4', 'UMUM', 12000, '2021-12-29', 'admin', 20000, '15:18:24');

-- ----------------------------
-- Table structure for tb_transaksi_kredit
-- ----------------------------
DROP TABLE IF EXISTS `tb_transaksi_kredit`;
CREATE TABLE `tb_transaksi_kredit`  (
  `no_faktur` char(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `id_pelanggan` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `total_bayar` int(20) UNSIGNED NOT NULL,
  `tanggal` date NOT NULL,
  `id_karyawan` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tanggal_jatuh_tempo` date NOT NULL,
  `jam` time NULL DEFAULT NULL,
  PRIMARY KEY (`no_faktur`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_transaksi_kredit
-- ----------------------------
INSERT INTO `tb_transaksi_kredit` VALUES ('KR/291221/1', '00001', 365000, '2021-12-29', 'admin', '2022-01-29', '15:19:17');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `pass` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `level` enum('admin','kasir','kasir_utama') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`user`, `pass`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('admin', '*4ACFE3202A5FF5CF467898FC58AAB1D615029441', 'admin');

-- ----------------------------
-- View structure for laporanpembelian
-- ----------------------------
DROP VIEW IF EXISTS `laporanpembelian`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `laporanpembelian` AS select `tb_pembelian`.`no_faktur` AS `no_faktur`,`tb_pembelian`.`sub_total` AS `sub_total`,`tb_pembelian`.`supplier` AS `supplier`,`tb_pembelian`.`tanggal` AS `tanggal`,`tb_pembelian`.`jatuh_tempo` AS `jatuh_tempo`,date_format(`tb_pembelian`.`tanggal`,'%d %M %Y') AS `tgl`,`tb_detail_pembelian`.`qty` AS `qty`,`tb_detail_pembelian`.`satuan` AS `satuan`,`tb_barang`.`nama` AS `nama`,(`tb_detail_pembelian`.`harga_beli` * `tb_detail_pembelian`.`qty`) AS `total`,`tb_supplier`.`nama` AS `nama_sup` from (((`tb_pembelian` join `tb_detail_pembelian` on((convert(`tb_pembelian`.`no_faktur` using utf8) = `tb_detail_pembelian`.`no_faktur`))) join `tb_barang` on((`tb_detail_pembelian`.`id_barang` = convert(`tb_barang`.`id` using utf8)))) join `tb_supplier` on((`tb_pembelian`.`supplier` = `tb_supplier`.`id`)));

-- ----------------------------
-- View structure for laporanrugilaba
-- ----------------------------
DROP VIEW IF EXISTS `laporanrugilaba`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `laporanrugilaba` AS select `tb_barang`.`nama` AS `nama`,sum(`tb_detail_transaksi`.`qty`) AS `qty`,(`tb_detail_transaksi`.`harga_beli` * sum(`tb_detail_transaksi`.`qty`)) AS `hrg_beli`,(`tb_detail_transaksi`.`harga_jual` * sum(`tb_detail_transaksi`.`qty`)) AS `hrg_jual`,((`tb_detail_transaksi`.`harga_jual` * sum(`tb_detail_transaksi`.`qty`)) - (`tb_detail_transaksi`.`harga_beli` * sum(`tb_detail_transaksi`.`qty`))) AS `laba`,`tb_detail_transaksi`.`satuan` AS `satuan` from (((`tb_barang` join `tb_detail_barang` on((`tb_barang`.`id` = `tb_detail_barang`.`id_barang`))) join `tb_detail_transaksi` on(((`tb_detail_transaksi`.`id_barang` = `tb_detail_barang`.`id_barang`) and (`tb_detail_barang`.`satuan` = `tb_detail_transaksi`.`satuan`)))) join `tb_transaksi` on((`tb_transaksi`.`no_faktur` = `tb_detail_transaksi`.`no_faktur`))) group by `tb_detail_barang`.`id_barang`,`tb_detail_barang`.`satuan` union select `tb_barang`.`nama` AS `nama`,sum(`tb_detail_transaksi_kredit`.`qty`) AS `qty`,(`tb_detail_transaksi_kredit`.`harga_beli` * sum(`tb_detail_transaksi_kredit`.`qty`)) AS `hrg_beli`,(`tb_detail_transaksi_kredit`.`harga_jual` * sum(`tb_detail_transaksi_kredit`.`qty`)) AS `hrg_jual`,((`tb_detail_transaksi_kredit`.`harga_jual` * sum(`tb_detail_transaksi_kredit`.`qty`)) - (`tb_detail_transaksi_kredit`.`harga_beli` * sum(`tb_detail_transaksi_kredit`.`qty`))) AS `laba`,`tb_detail_transaksi_kredit`.`satuan` AS `satuan` from (((`tb_barang` join `tb_detail_barang` on((`tb_barang`.`id` = `tb_detail_barang`.`id_barang`))) join `tb_detail_transaksi_kredit` on(((`tb_detail_transaksi_kredit`.`id_barang` = `tb_detail_barang`.`id_barang`) and (`tb_detail_barang`.`satuan` = `tb_detail_transaksi_kredit`.`satuan`)))) join `tb_transaksi_kredit` on((`tb_transaksi_kredit`.`no_faktur` = `tb_detail_transaksi_kredit`.`no_faktur`))) group by `tb_detail_barang`.`id_barang`,`tb_detail_barang`.`satuan`;

-- ----------------------------
-- View structure for laporantransaksi
-- ----------------------------
DROP VIEW IF EXISTS `laporantransaksi`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `laporantransaksi` AS select `tb_transaksi`.`no_faktur` AS `no_faktur`,`tb_transaksi`.`jam` AS `jam`,date_format(`tb_transaksi`.`tanggal`,'%d %M %Y') AS `date_format(tb_transaksi.tanggal, '%d %M %Y')`,`tb_transaksi`.`total_bayar` AS `total_bayar`,`tb_detail_transaksi`.`satuan` AS `satuan`,`tb_detail_transaksi`.`harga_jual` AS `harga_jual`,`tb_detail_transaksi`.`qty` AS `qty`,(`tb_detail_transaksi`.`harga_jual` * `tb_detail_transaksi`.`qty`) AS `total`,`tb_pelanggan`.`nama` AS `nama_pelanggan`,`tb_barang`.`nama` AS `nama_barang` from (((`tb_transaksi` join `tb_detail_transaksi` on((`tb_transaksi`.`no_faktur` = `tb_detail_transaksi`.`no_faktur`))) join `tb_barang` on((`tb_detail_transaksi`.`id_barang` = `tb_barang`.`id`))) join `tb_pelanggan` on((`tb_transaksi`.`id_pelanggan` = `tb_pelanggan`.`id`))) order by `tb_transaksi`.`tanggal` desc,`tb_transaksi`.`jam` desc,`tb_transaksi`.`no_faktur` desc;

-- ----------------------------
-- View structure for laporan_piutang
-- ----------------------------
DROP VIEW IF EXISTS `laporan_piutang`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `laporan_piutang` AS select `tb_properties`.`nama_toko` AS `nama_toko`,`tb_properties`.`alamat` AS `alamat`,`tb_properties`.`telp` AS `telp`,`tb_properties`.`npwp` AS `npwp`,`tb_barang`.`nama` AS `nama_barang`,date_format(`tb_transaksi_kredit`.`tanggal`,'%d - %m - %Y') AS `tanggal`,`tb_transaksi_kredit`.`total_bayar` AS `total_bayar`,`tb_karyawan`.`nama` AS `nama_karyawan`,`tb_pelanggan`.`nama` AS `nama_pelanggan`,`tb_detail_transaksi_kredit`.`harga_jual` AS `harga_jual`,`tb_detail_transaksi_kredit`.`qty` AS `qty`,(`tb_detail_transaksi_kredit`.`qty` * `tb_detail_transaksi_kredit`.`harga_jual`) AS `total`,`tb_detail_transaksi_kredit`.`satuan` AS `satuan`,`tb_transaksi_kredit`.`no_faktur` AS `no_faktur` from (((((`tb_transaksi_kredit` join `tb_detail_transaksi_kredit` on((`tb_transaksi_kredit`.`no_faktur` = `tb_detail_transaksi_kredit`.`no_faktur`))) join `tb_pelanggan` on((`tb_transaksi_kredit`.`id_pelanggan` = `tb_pelanggan`.`id`))) join `tb_barang` on((`tb_detail_transaksi_kredit`.`id_barang` = `tb_barang`.`id`))) join `tb_karyawan` on((`tb_transaksi_kredit`.`id_karyawan` = `tb_karyawan`.`id`))) join `tb_properties` on((`tb_properties`.`nama_toko` = `tb_properties`.`nama_toko`)));

SET FOREIGN_KEY_CHECKS = 1;
