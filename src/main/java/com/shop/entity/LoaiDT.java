package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Data
@Entity
@Table(name="loaiDTS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoaiDT implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maLoaiDT;
    private String tenLoai;

    //bi-directional many-to-one association to Giay
    @OneToMany(mappedBy="loaiDT")
    private List<Phone> phones;
}
