package com.shop.controller;

import com.shop.dao.PhoneDAO;
import com.shop.dao.SessionDAO;
import com.shop.dao.ShoppingCartDAO;
import com.shop.entity.KhachHang;
import com.shop.entity.Phone;
import com.shop.entity.ShoppingCart;
import com.shop.validate.PhoneValidate;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequestMapping("shopping-cart")
public class ShoppingCartController {
    @Autowired
    PhoneDAO phoneDAO;
    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    SessionDAO session;

    @GetMapping({"","views"})
    public String viewCart(Model model) {
        Collection<ShoppingCart> listGioHang = shoppingCartDAO.getAll();
        model.addAttribute("listGioHang",listGioHang);
        model.addAttribute("tongTienGioHang",shoppingCartDAO.getAmout());
        model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
        KhachHang khachHang=(KhachHang) session.get("user");
        if(khachHang!=null) {
            model.addAttribute("sessionUsername",khachHang.getTaiKhoan());
        }
        return "customer/cart-items";
    }
    @PostMapping("addToCart")
    public ResponseEntity<String> addToCart(@RequestParam String maSanPham) throws JSONException {
        ShoppingCart cartItem=new ShoppingCart();
        Phone phone=phoneDAO.findById(Integer.parseInt(maSanPham)).get();
        cartItem.setMaDT(phone.getMaDT());
        cartItem.setDonGia(phone.getDonGia());
        cartItem.setGiamGia(phone.getGiamGia());
        cartItem.setHinhAnh(phone.getHinhAnh());
        cartItem.setHangDT(phone.getHangDT());
        cartItem.setLoaiDT(phone.getLoaiDT());
        cartItem.setMoTa(phone.getMoTa());
        cartItem.setTenDT(phone.getTenDT());
        shoppingCartDAO.add(cartItem);

        int tongSoLuongGioHang = shoppingCartDAO.getCount();
        JSONObject json = new JSONObject();
        json.put("soLuong", tongSoLuongGioHang);
        return ResponseEntity.ok(String.valueOf(json));
    }
    @PostMapping("deleteItem")
    public ResponseEntity<String> deleteItem(@RequestParam String maSanPham) throws JSONException {
        if(new PhoneValidate().checkIDPhone(maSanPham)) {
            shoppingCartDAO.remove(Integer.parseInt(maSanPham));
            int tongTien = shoppingCartDAO.getAmout();
            int tongSoLuongGioHang = shoppingCartDAO.getCount();
            JSONObject json = new JSONObject();
            json.put("soLuong", tongSoLuongGioHang);
            json.put("tongTien", tongTien);
            return ResponseEntity.ok(String.valueOf(json));
        }else {
            return ResponseEntity.ok("fail");
        }
    }
    @PostMapping("deleteAllItem")
    public String deleteAllItem(Model model) {
        shoppingCartDAO.clear();
        return "redirect:/shopping-cart";
    }
}
