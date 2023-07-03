package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name="chitietdonhangs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChiTietDonHang implements Serializable {
    @EmbeddedId
    private ChiTietDonHangPK id;
    private int soLuong;
    private int donGia;

    @ManyToOne(optional=false)
    @JoinColumn(name="maDT", insertable=false, updatable=false)
    private Phone phone;

    //bi-directional many-to-one association to GioHang
    @ManyToOne(optional=false)
    @JoinColumn(name="maDonHang", insertable=false, updatable=false)
    private DonHang donHang;
}
