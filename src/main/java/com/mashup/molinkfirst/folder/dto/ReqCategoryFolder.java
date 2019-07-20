package com.mashup.molinkfirst.folder.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReqCategoryFolder{
  private List<String> category_name = new ArrayList<>();
}
