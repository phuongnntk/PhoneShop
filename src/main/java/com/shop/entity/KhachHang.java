package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="khachhangs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KhachHang implements Serializable {
    @Id
    private String taiKhoan;
    private String matKhau;
    private String soDienThoai;
    private String tenKhachHang;
    private String diaChi;
    private boolean quyen;
    // bi-directional many-to-one association to DonHang
    @OneToMany
    @JoinColumn(name = "maDonHang")
    private List<DonHang> donhangs;
}
