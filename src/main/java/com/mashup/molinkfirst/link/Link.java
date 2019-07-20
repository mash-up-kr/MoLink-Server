package com.mashup.molinkfirst.link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mashup.molinkfirst.entity.BaseTimeEntity;
import com.mashup.molinkfirst.folder.Folder;
import com.mashup.molinkfirst.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@EqualsAndHashCode(of = "id", callSuper=false)
@Entity
public class Link extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String name;

  @Column
  private String url;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "parentId")
  private Folder folder;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;
}
