package com.company.demo.service;

import com.company.demo.entity.Post;
import com.company.demo.entity.User;
import com.company.demo.model.dto.PageableDto;
import com.company.demo.model.request.CreatePostReq;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {

  Post createPost(CreatePostReq req, User user);

  void updatePost(CreatePostReq req, User user, long id);

  void deletePost(long id);

  Page<Post> getListPost(int page);

  Post getPostById(long id);

  List<Post> getLatestPostsNotId(long id);

  List<Post> getLatestPost();

  PageableDto adminGetListPost(String title, String status, int page, String order,
      String direction);
}
