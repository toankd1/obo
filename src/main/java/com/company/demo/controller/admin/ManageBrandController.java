package com.company.demo.controller.admin;

import com.company.demo.entity.Brand;
import com.company.demo.entity.User;
import com.company.demo.model.dto.BrandInfo;
import com.company.demo.model.request.CreateBrandReq;
import com.company.demo.security.CustomUserDetails;
import com.company.demo.service.BrandService;
import com.company.demo.service.ImageService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ManageBrandController {

  @Autowired
  private BrandService brandService;

  @Autowired
  private ImageService imageService;

  @GetMapping("/admin/brands")
  public String getPostManagePage(Model model) {
    // Get list image of user
    User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getUser();
    List<String> images = imageService.getListImageOfUser(user.getId());
    model.addAttribute("images", images);

    List<BrandInfo> brands = brandService.getListBrandAndProductCount();
    model.addAttribute("brands", brands);

    return "admin/brand/list";
  }

  @PostMapping("/api/admin/brands")
  public ResponseEntity<?> createCategory(@Valid @RequestBody CreateBrandReq req) {
    Brand brand = brandService.createBrand(req);

    return ResponseEntity.ok(brand);
  }

  @PutMapping("/api/admin/brands/{id}")
  public ResponseEntity<?> updateCategory(@Valid @RequestBody CreateBrandReq req,
      @PathVariable int id) {
    brandService.updateBrand(id, req);

    return ResponseEntity.ok("C???p nh???t th??nh c??ng");
  }

  @DeleteMapping("/api/admin/brands/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable int id) {
    brandService.deleteBrand(id);

    return ResponseEntity.ok("X??a th??nh c??ng");
  }
}
