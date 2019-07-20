package com.mashup.molinkfirst.link;

import com.mashup.molinkfirst.exception.BadRequestException;
import com.mashup.molinkfirst.exception.NotFoundException;
import com.mashup.molinkfirst.folder.Folder;
import com.mashup.molinkfirst.folder.FolderRepository;
import com.mashup.molinkfirst.link.dto.ReqCreateLink;
import com.mashup.molinkfirst.link.dto.ReqUpdateLink;
import com.mashup.molinkfirst.link.dto.ResCreateLink;
import com.mashup.molinkfirst.link.dto.ResUpdateLink;
import com.mashup.molinkfirst.user.User;
import com.mashup.molinkfirst.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkService {
  private LinkRepository linkRepository;
  private FolderRepository folderRepository;
  private UserRepository userRepository;

  public LinkService(LinkRepository linkRepository,
      FolderRepository folderRepository, UserRepository userRepository) {
    this.linkRepository = linkRepository;
    this.folderRepository = folderRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public ResCreateLink createLink(User user, ReqCreateLink requestBody){
    Folder parentFolder = folderRepository.findById(requestBody.getParent_id())
        .orElseThrow(() -> new NotFoundException("저장할 폴더가 존재하지 않습니다."));

    if(!(parentFolder.getUser().getId().equals(user.getId())))
      throw new BadRequestException("Check user or Folder");

    Link link = Link.builder()
        .url(requestBody.getUrl())
        .name(requestBody.getName())
        .folder(parentFolder)
        .user(user)
        .build();

    Link createdLink = linkRepository.save(link);

    ResCreateLink resCreateLink = new ResCreateLink();
    resCreateLink.setId(createdLink.getId());
    resCreateLink.setName(createdLink.getName());
    resCreateLink.setUrl(createdLink.getUrl());

    return  resCreateLink;
  }

  @Transactional
  public ResUpdateLink updateLink(User user, Long id, ReqUpdateLink requestBody){
    Folder parentFolder = folderRepository.findById(requestBody.getParentId())
        .orElseThrow(() -> new NotFoundException("저장할 폴더가 존재하지 않습니다."));

    Link link = linkRepository.findById(id).orElseThrow(() -> new NotFoundException("Link not Found"));

    if (! user.getId().equals(link.getUser().getId())) throw new BadRequestException("Check user or Folder");

    link.setName(requestBody.getName());
    link.setFolder(parentFolder);

    Link updatedLink = linkRepository.save(link);

    ResUpdateLink resUpdateLink = new ResUpdateLink();
    resUpdateLink.setName(updatedLink.getName());
    resUpdateLink.setParentId(updatedLink.getFolder().getId());

    return  resUpdateLink;
  }

  @Transactional
  public void deleteLink(String phoneUuid, Long id){
    User user = userRepository.findByPhoneUuid(phoneUuid).orElseThrow(() -> new NotFoundException("Not found user"));
    Link link = linkRepository.findById(id).orElseThrow(() -> new NotFoundException("Link not Found"));

    if ( !(user.getId().equals(link.getUser().getId()))) throw new BadRequestException("Check user or Folder");

    linkRepository.delete(link);
  }
}
