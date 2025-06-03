CREATE DATABASE parkir_db;
USE parkir_db;

CREATE TABLE parkir (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomor_tiket VARCHAR(50) UNIQUE,
    nomor_polisi VARCHAR(20),
    jenis_kendaraan VARCHAR(10),
    tanggal_masuk DATE,
    jam_masuk TIME,
    tanggal_keluar DATE,
    durasi VARCHAR(50),
    total INT
);