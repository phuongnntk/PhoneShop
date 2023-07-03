package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="phones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Phone implements Serializable {
    @Id
    private int maDT;
    private int donGia;
    private double giamGia;
    private String hinhAnh;
    @Column(columnDefinition = "nvarchar(700)")
    private String moTa;
    private String tenDT;

    @OneToMany(mappedBy = "phone")
    private List<ChiTietDonHang> chiTietDonHangs;
    // bi-directional many-to-one association to HangGiay
    @ManyToOne
    @JoinColumn(name = "maHang")
    private HangDT hangDT;

    // bi-directional many-to-one association to LoaiGiay
    @ManyToOne
    @JoinColumn(name = "maLoaiDT")
    private LoaiDT loaiDT;

}
