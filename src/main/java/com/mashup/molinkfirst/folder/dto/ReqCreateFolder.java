package com.mashup.molinkfirst.folder.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReqCreateFolder {
  String name;
  String color;
  Long parentId;
}
