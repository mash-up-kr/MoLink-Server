package com.mashup.molinkfirst.folder;

import com.mashup.molinkfirst.exception.BadRequestException;
import com.mashup.molinkfirst.exception.NotFoundException;
import com.mashup.molinkfirst.folder.dto.ReqCategoryFolder;
import com.mashup.molinkfirst.folder.dto.ReqCreateFolder;
import com.mashup.molinkfirst.folder.dto.ReqUpdateFolder;
import com.mashup.molinkfirst.folder.dto.ResCategoryFolder;
import com.mashup.molinkfirst.folder.dto.ResCreateFolder;
import com.mashup.molinkfirst.folder.dto.ResFoldersAll;
import com.mashup.molinkfirst.folder.dto.ResShowFolder;
import com.mashup.molinkfirst.link.LinkRepository;
import com.mashup.molinkfirst.user.User;
import com.mashup.molinkfirst.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FolderService {
 private FolderRepository folderRepository;
 private UserRepository userRepository;
 private LinkRepository linkRepository;

  public FolderService(FolderRepository folderRepository,
      UserRepository userRepository, LinkRepository linkRepository) {
    this.folderRepository = folderRepository;
    this.userRepository = userRepository;
    this.linkRepository = linkRepository;
  }

  @Transactional
  List<ResCategoryFolder> postCategoriesFolders(User user, ReqCategoryFolder requestBody) {
    int length = requestBody.getCategory_name().size();
    for (int i = 0; i < length; i++){
      Folder folder = Folder.builder()
          .name(requestBody.getCategory_name().get(i))
          .color("#000000")
          .parent(null)
          .user(user)
          .build();
      folderRepository.save(folder);
    }

    List<ResCategoryFolder> resCategoryFolders = new ArrayList<>();
    List<Folder> folders = folderRepository.findByUser(user);

    for (int i = 0; i < folders.size(); i++) {
      ResCategoryFolder resCategoryFolder = new ResCategoryFolder();
      resCategoryFolder.setId(folders.get(i).getId());
      resCategoryFolder.setName(folders.get(i).getName());
      resCategoryFolder.setColor(folders.get(i).getColor());

      resCategoryFolders.add(resCategoryFolder);
    }

    return resCategoryFolders;
  }

  @Transactional
  public ResCreateFolder createFolder(User user, ReqCreateFolder requestBody){
    Optional<Folder> parentFolder = folderRepository.findById(requestBody.getParentId());
    Folder createdFolder;

    if (parentFolder.isPresent() ) {
      Folder folder = Folder.builder()
          .name(requestBody.getName())
          .color(requestBody.getColor())
          .parent(parentFolder.get())
          .user(user)
          .build();

      createdFolder = folderRepository.save(folder);
    } else {
      Folder folder = Folder.builder()
          .name(requestBody.getName())
          .color(requestBody.getColor())
          .parent(null)
          .user(user)
          .build();
      createdFolder = folderRepository.save(folder);
    }

    ResCreateFolder resCreateFolder = new ResCreateFolder();
    resCreateFolder.setId(createdFolder.getId());
    resCreateFolder.setName(createdFolder.getName());
    resCreateFolder.setColor(createdFolder.getColor());

    return resCreateFolder;
  }

  /* 폴더 수정 */
  public void updateFolder(User user, ReqUpdateFolder requestBody){
    Folder folder = folderRepository.findById(requestBody.getId()).orElseThrow(() -> new NotFoundException("Not found folder"));

    if (user.getId().equals(folder.getUser().getId())) {
      folder.setName(requestBody.getName());
      folder.setColor(requestBody.getColor());

      folderRepository.save(folder);
    } else throw new BadRequestException("Check user or folder id");
  }

  /* 폴더 삭제 */
  public void deleteFolder(String phoneUuid, Long folderId){
    User user = userRepository.findByPhoneUuid(phoneUuid).orElseThrow(() -> new NotFoundException("Not found user"));
    Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new NotFoundException("Not found folder"));

    if (folder.getUser().getId().equals(user.getId()))
      folderRepository.delete(folder);
    else throw new BadRequestException("Check user or folder id");
  }

  public ResShowFolder getFolders(String phoneUuid, Long id, Long parentId){
    User user = userRepository.findByPhoneUuid(phoneUuid).orElseThrow(() -> new NotFoundException("Not found user"));;
    Optional<Folder> currentFolder = folderRepository.findById(id);
    ResShowFolder resShowFolder = new ResShowFolder();

    if (parentId != null) {
      Optional<Folder> parentFolder = folderRepository.findById(parentId);
      if (parentFolder.isPresent()) {
        resShowFolder.setSiblingFolders(folderRepository.findAllByParent(parentFolder));
      }
    } else {
      resShowFolder.setSiblingFolders(folderRepository.findByParentIsNull());
    }

      //sibling은 requestBody.getParentId()와 같은 parentId를 가지는 폴더들
      //childer은 requestBody.getId()를 paretnId로 가지는 폴더들

      resShowFolder.setChildrenFolders(folderRepository.findAllByParent(currentFolder));
      if (currentFolder.isPresent()) {
        resShowFolder.setLinks(linkRepository.findAllByFolder(currentFolder));
      }
    return  resShowFolder;
  }

  public List<ResFoldersAll> getFoldersAll(String phoneUuid){
    User user = userRepository.findByPhoneUuid(phoneUuid).orElseThrow(() -> new NotFoundException("Not found user"));
    List<Folder> folders = folderRepository.findAllByUser(user);
    List<ResFoldersAll> resFoldersAllList = new ArrayList<>();
    Folder parentFolder = new Folder();

    int folderNum = folders.size();

    for (int i = 0; i < folderNum; i++) {
      ResFoldersAll resFoldersAll = new ResFoldersAll();
      resFoldersAll.setId(folders.get(i).getId());
      resFoldersAll.setName(folders.get(i).getName());

      if (folders.get(i).getParent() != null){
        resFoldersAll.setParentId(folders.get(i).getParent().getId());
      } else {
        resFoldersAll.setParentId(null);
      }
      resFoldersAllList.add(resFoldersAll);
    }
    return resFoldersAllList;
  }

}
