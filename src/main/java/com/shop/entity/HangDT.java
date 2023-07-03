package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name="hangDTS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HangDT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maHang;
    private String tenHang;
    @OneToMany(mappedBy = "hangDT")
    List<Phone> phones;
}
