package com.company.demo.service;

import com.company.demo.entity.Product;
import com.company.demo.entity.ProductSize;
import com.company.demo.model.dto.DetailProductInfoDto;
import com.company.demo.model.dto.PageableDto;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.dto.ShortProductInfoDto;
import com.company.demo.model.request.CreateProductReq;
import com.company.demo.model.request.FilterProductReq;
import com.company.demo.model.request.UpdateOnfeetImagesReq;
import com.company.demo.model.request.UpdateSizeCountReq;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

  List<ProductInfoDto> getListBestSellerProduct();

  List<ProductInfoDto> getListNewProduct();

  List<ProductInfoDto> getListSuggestProduct();

  DetailProductInfoDto getDetailProductById(String id);

  List<ProductInfoDto> getRelatedProducts(String id);

  List<Integer> getListAvailableSize(String id);

  PageableDto filterProduct(FilterProductReq req);

  PageableDto searchProductByKeyword(String keyword, Integer page);

  PageableDto adminGetListProduct(String id, String name, String category, String brand,
      String order, String direction, int page);

  String createProduct(CreateProductReq req);

  Product getProductById(String id);

  void updateProduct(String id, CreateProductReq req);

  void updateOnfeetImages(String id, UpdateOnfeetImagesReq req);

  void updateSizeCount(UpdateSizeCountReq req);

  void deleteProduct(String id);

  List<ProductSize> getListSizeOfProduct(String id);

  List<ShortProductInfoDto> getAllProduct();

  List<ShortProductInfoDto> getAvailableProducts();

  boolean checkProductSizeAvailable(String productId, int size);
}
