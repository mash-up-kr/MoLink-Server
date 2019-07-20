package com.mashup.molinkfirst.folder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mashup.molinkfirst.entity.BaseTimeEntity;
import com.mashup.molinkfirst.link.Link;
import com.mashup.molinkfirst.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Folder extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 40)
  private String name;

  @Column(length = 20)
  private String color;

  @JsonIgnore
  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "parentId")
  private Folder parent;

  @JsonIgnore
  @OneToMany(cascade = {CascadeType.ALL})
  @JoinColumn(name = "parentId")
  private final List<Folder> children = new ArrayList<Folder>();

  @ManyToOne
  @JoinColumn(name = "userId")
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy="folder", cascade = CascadeType.ALL)
  private final List<Link> links = new ArrayList<Link>();
}