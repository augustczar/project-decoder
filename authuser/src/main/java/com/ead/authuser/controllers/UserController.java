package com.ead.authuser.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
			@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
		
		Page<UserModel> userModelPage = userService.findAll(spec, pageable);
		//Construção do Heateos com links de hipermidia
		if(!userModelPage.isEmpty()) {
			for (UserModel user : userModelPage.toList()) {
				user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getOneUser(@PathVariable UUID userId){
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
		}
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable UUID userId){
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		log.debug("DELETE deleteUser userID recived {}", userId);
		
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}else {
			userService.deleteUser(userModelOptional.get());
			
			log.debug("DELETE updateUser userId recived {}", userId);
			log.info("User deleted successfully userId {}", userId);
			
			return ResponseEntity.status(HttpStatus.OK).body("User deleted success!");
		}
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable UUID userId, 
			@RequestBody @Validated(UserDto.UserView.UserPut.class) 
			@JsonView(UserDto.UserView.UserPut.class) UserDto userDto){
		
		log.debug("PUT updateUser userDto recived {}", userDto.toString());
		
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}else {
			var userModel = userModelOptional.get();
			userModel.setFullName(userDto.getFullName());
			userModel.setPhoneNumber(userDto.getPhoneNumber());
			userModel.setCpf(userDto.getCpf());
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			
			userService.updateUser(userModel);
			
			log.debug("PUT updateUser userId recived {}", userModel.getUserId());
			log.info("User updateUser successfully userId {}", userModel.getUserId());
			
			return ResponseEntity.status(HttpStatus.OK).body(userModel);
		}
	}
	
	@PutMapping("/{userId}/password")
	public ResponseEntity<Object> updatePassword(@PathVariable UUID userId, 
			@RequestBody @Validated(UserDto.UserView.PasswordPut.class) 
			@JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto){
		log.debug("PUT updatePassword userDto recived {}", userDto.getUserId());
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}
		if(!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
			log.warn("Mismatched old password userId {} ", userDto.getUserId());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
		}else {
			var userModel = userModelOptional.get();
			userModel.setPassword(userDto.getPassword());
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			
			userService.updatePassword(userModel);
			log.debug("PUT updatePassword userId recived {}", userModel.getUserId());
			log.info("User updatePassword successfully userId {}", userModel.getUserId());
			
			return ResponseEntity.status(HttpStatus.OK).body("Password updated successfull!");
		}
	}
	
	@PutMapping("/{userId}/image")
	public ResponseEntity<Object> updateImage(@PathVariable UUID userId, 
			@RequestBody @Validated(UserDto.UserView.ImagePut.class) 
			@JsonView(UserDto.UserView.ImagePut.class) UserDto userDto){
		log.debug("PUT updateImage userDto recived {}", userDto.getUserId());
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}else {
			var userModel = userModelOptional.get();
			userModel.setImageUrl(userDto.getImageUrl());
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			
			userService.updateUser(userModel);
			log.debug("PUT updateImage userId recived {}", userModel.getUserId());
			log.info("User updateImage successfully userId {}", userModel.getUserId());
			return ResponseEntity.status(HttpStatus.OK).body(userModel);
		}
	}
}
