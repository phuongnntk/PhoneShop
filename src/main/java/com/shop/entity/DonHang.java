package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name="donhangs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDonHang;
    @Column(columnDefinition = "nvarchar(50)")
    private String diaChi;
    @Column(columnDefinition = "nvarchar(50)")
    private String tenNguoiNhan;
    private int tongTien;
    @Column(columnDefinition = "nvarchar(200)")
    private String ghiChuAdmin;
    @Column(columnDefinition = "nvarchar(200)")
    private String ghiChuKhachHang;
    private Timestamp ngayDatHang;
    private Date ngayGiaoDuKien;
    @Column(columnDefinition = "varchar(20)")
    private String soDienThoai;
    private String trangThai;

    @OneToMany(mappedBy="donHang")
    private List<ChiTietDonHang> chiTietDonHangs;

    @ManyToOne
    @JoinColumn(name = "taiKhoan")
    private KhachHang khachHang;
}
