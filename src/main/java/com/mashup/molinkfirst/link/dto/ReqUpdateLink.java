package com.mashup.molinkfirst.link.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReqUpdateLink {
  String name;
  Long parentId;
}
