package com.company.demo.service;

import com.company.demo.entity.Image;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {

  void save(Image img);

  List<String> getListImageOfUser(long userId);

  void deleteImage(String uploadDir, String filename);
}
