package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    private int Thang1;
    private int Thang2;
    private int Thang3;
    private int Thang4;
    private int Thang5;
    private int Thang6;
    private int Thang7;
    private int Thang8;
    private int Thang9;
    private int Thang10;
    private int Thang11;
    private int Thang12;
}
