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
import com.mashup.molinkfirst.model.ApiResponseModel;
import com.mashup.molinkfirst.user.User;
import com.mashup.molinkfirst.user.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FolderController {
  private FolderService folderService;
  private UserService userService;

  public FolderController(FolderService folderService,
      UserService userService) {
    this.folderService = folderService;
    this.userService = userService;
  }

  @PostMapping("/categories/folders")
  public ApiResponseModel<List<ResCategoryFolder>> postCategoriesFolders(
      @RequestHeader("phone_uuid") String phoneUuid,
      @Valid @RequestBody ReqCategoryFolder requestBody,
      BindingResult bindingResult){
    ApiResponseModel<List<ResCategoryFolder>> response = new ApiResponseModel<List<ResCategoryFolder>>();

    User user = userService.createUser(phoneUuid);

    if (bindingResult.hasErrors()) throw new BadRequestException("Check RequestBody");
    if (requestBody.getCategory_name().size() == 0) throw new BadRequestException("Select Categories");

    response.setStatusCode(HttpStatus.CREATED.value());
    response.setMessage(HttpStatus.CREATED.toString());
    response.setData(folderService.postCategoriesFolders(user, requestBody));

    return response;
  }

  /* 폴더 생성 */
  @PostMapping("/folders")
  public ApiResponseModel<ResCreateFolder> createFolders(
      @RequestHeader("phone_uuid") String phoneUuid,
      @Valid @RequestBody ReqCreateFolder requestBody,
      BindingResult bindingResult){
    ApiResponseModel<ResCreateFolder> response = new ApiResponseModel<ResCreateFolder>();

    User user = userService.findUser(phoneUuid);

    if (user == null) {
      throw new NotFoundException("Cannot find User");
    }

    if (bindingResult.hasErrors()) throw new BadRequestException("Check RequestBody");

    response.setStatusCode(HttpStatus.CREATED.value());
    response.setMessage(HttpStatus.CREATED.toString());
    response.setData(folderService.createFolder(user, requestBody));

    return response;
  }

  /* 폴더 수정 */
  @PutMapping("/folders/{id}")
  public ApiResponseModel updateFolders(
      @RequestHeader("phone_uuid") String phoneUuid,
      @RequestBody ReqUpdateFolder requestBody){
    ApiResponseModel response = new ApiResponseModel();

    User user = userService.findUser(phoneUuid);
    folderService.updateFolder(user, requestBody);

    response.setStatusCode(HttpStatus.OK.value());
    response.setMessage(HttpStatus.OK.toString());

    return  response;
  }

  /* 폴더 삭제 */
  @DeleteMapping("/folders/{id}")
  public ApiResponseModel delteFolders(
      @RequestHeader("phone_uuid") String phoneUuid,
      @PathVariable("id") Long id) {

    ApiResponseModel response = new ApiResponseModel();

    folderService.deleteFolder(phoneUuid, id);

    response.setStatusCode(HttpStatus.NO_CONTENT.value());
    response.setMessage(HttpStatus.NO_CONTENT.toString());

    return  response;
  }

  /* 현재 폴더에따른 폴더 및 링크 조회 */
  @GetMapping("/folders")
  public ApiResponseModel<ResShowFolder> getFolders(
      @RequestHeader("phone_uuid") String phoneUuid,
      @RequestParam(value = "id") Long id,
      @RequestParam(value = "parent_id", required = false) Long parentId){

    ApiResponseModel<ResShowFolder> response = new ApiResponseModel<ResShowFolder>();
    response.setStatusCode(HttpStatus.OK.value());
    response.setMessage(HttpStatus.OK.toString());
    ResShowFolder res = folderService.getFolders(phoneUuid, id, parentId);
    response.setData(res);

    return response;
  }


  @GetMapping("/folders/all")
  public ApiResponseModel<List<ResFoldersAll>> getFoldersAll(
      @RequestHeader("phone_uuid") String phoneUuid){
    ApiResponseModel<List<ResFoldersAll>> response = new ApiResponseModel<List<ResFoldersAll>>();

    response.setStatusCode(HttpStatus.OK.value());
    response.setMessage(HttpStatus.OK.toString());
    response.setData(folderService.getFoldersAll(phoneUuid));
    return response;
  }
}
