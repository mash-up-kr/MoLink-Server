package com.mashup.molinkfirst.link;

import com.mashup.molinkfirst.link.dto.ReqCreateLink;
import com.mashup.molinkfirst.link.dto.ReqUpdateLink;
import com.mashup.molinkfirst.link.dto.ResCreateLink;
import com.mashup.molinkfirst.link.dto.ResUpdateLink;
import com.mashup.molinkfirst.model.ApiResponseModel;
import com.mashup.molinkfirst.user.User;
import com.mashup.molinkfirst.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/links")
public class LinkController {
  private LinkService linkService;
  private UserService userService;
  private LinkRepository linkRepository;

  public LinkController(LinkService linkService, UserService userService,
      LinkRepository linkRepository) {
    this.linkService = linkService;
    this.userService = userService;
    this.linkRepository = linkRepository;
  }

  /* 링크 생성 */
  @PostMapping("")
  public ApiResponseModel<ResCreateLink> createLinks(
      @RequestHeader("phone_uuid") String phoneUuid,
      @RequestBody ReqCreateLink requestBody){
    ApiResponseModel<ResCreateLink> response = new ApiResponseModel<ResCreateLink>();

    User user = userService.findUser(phoneUuid);

    response.setStatusCode(HttpStatus.CREATED.value());
    response.setMessage(HttpStatus.CREATED.toString());
    response.setData(linkService.createLink(user, requestBody));

    return response;
  }

  /* 링크 수정 */
  @PutMapping("/{id}")
  public ApiResponseModel<ResUpdateLink> updateLinks(
      @RequestHeader("phone_uuid") String phoneUuid,
      @RequestBody ReqUpdateLink requestBody,
      @PathVariable Long id){
    ApiResponseModel<ResUpdateLink> response = new ApiResponseModel<ResUpdateLink>();

    User user = userService.findUser(phoneUuid);

    response.setStatusCode(HttpStatus.CREATED.value());
    response.setMessage(HttpStatus.CREATED.toString());
    response.setData(linkService.updateLink(user, id, requestBody));

    return  response;
  }

  /* 링크 삭제 */
  @DeleteMapping("/{id}")
  public ApiResponseModel deleteLinks(
      @RequestHeader("phone_uuid") String phoneUuid,
      @PathVariable Long id){

    ApiResponseModel response =  new ApiResponseModel();

    linkService.deleteLink(phoneUuid, id);

    response.setStatusCode(HttpStatus.NO_CONTENT.value());
    response.setMessage(HttpStatus.NO_CONTENT.toString());

    return response;
  }

}
