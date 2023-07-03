package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Getter
@Setter
@Embeddable
public class ChiTietDonHangPK implements Serializable {
    @Column(name="maDonHang", insertable=false, updatable=false)
    private int maDonHang;

    @Column(name="maDT", insertable=false, updatable=false)
    private int maDT;
}
