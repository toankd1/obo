package com.company.demo.service;

import com.company.demo.entity.Promotion;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.request.CreatePromotionReq;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PromotionService {

  List<ProductInfoDto> checkPublicPromotion(List<ProductInfoDto> products);

  Promotion checkPublicPromotion();

  Promotion checkPromotion(String code);

  long calculatePromotionPrice(Long price, Promotion promotion);

  Page<Promotion> adminGetListPromotion(String code, String name, String ispublic, String active,
      int page);

  Promotion createPromotion(CreatePromotionReq req);

  void updatePromotion(long id, CreatePromotionReq req);

  void deletePromotion(long id);

  Promotion getPromotionById(long id);

  List<Promotion> getAllValidPromotion();
}
