package com.company.demo.service;

import com.company.demo.entity.Brand;
import com.company.demo.model.dto.BrandInfo;
import com.company.demo.model.request.CreateBrandReq;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BrandService {

  List<Brand> getListBrand();

  List<BrandInfo> getListBrandAndProductCount();

  Brand createBrand(CreateBrandReq req);

  void updateBrand(int id, CreateBrandReq req);

  void deleteBrand(int id);
}
