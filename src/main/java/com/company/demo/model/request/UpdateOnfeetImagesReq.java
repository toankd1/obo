package com.company.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateOnfeetImagesReq {

  @JsonProperty("onfeet_images")
  private ArrayList<String> onfeetImages;
}