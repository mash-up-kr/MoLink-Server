package com.mashup.molinkfirst.folder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mashup.molinkfirst.folder.Folder;
import com.mashup.molinkfirst.link.Link;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@ToString
public class ResShowFolder {
  List<Folder> siblingFolders;
  List<Folder> childrenFolders;
  List<Link> links;
}
