package com.example.mienspa.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.request.LoginRequest;
import com.example.mienspa.dto.request.SignupRequest;
import com.example.mienspa.dto.request.TokenRefreshRequest;
import com.example.mienspa.dto.response.JwtResponse;
import com.example.mienspa.dto.response.MessageResponse;
import com.example.mienspa.dto.response.TokenRefreshResponse;
import com.example.mienspa.jwt.JwtUtils;

import com.example.mienspa.models.ERole;
import com.example.mienspa.models.RefreshToken;
import com.example.mienspa.models.UserDetailsImpl;
import com.example.mienspa.models.UserRole;
import com.example.mienspa.models.Users;
import com.example.mienspa.service.RefreshTokenService;
import com.example.mienspa.service.RoleService;
import com.example.mienspa.service.UserRoleService;
import com.example.mienspa.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class AuthController {
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;
	
	HttpHeaders responseHeaders = new HttpHeaders();

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		try {
			Users user = userService.getByEmail(loginRequest.getEmail());
			if(user != null) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(user.getUsEmailNo(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				String jwt = jwtUtils.generateJwtToken(userDetails);
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
				RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(),jwt);
				JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken.getJwtId(), userDetails.getId(),
						userDetails.getUsername(), userDetails.getEmail(), roles);
				return new ResponseEntity<>(jwtResponse, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>("Email or Password error", responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {		
			return  new ResponseEntity<>("Email or Password error", responseHeaders, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
		
	    try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(request.getToken());
			return  new ResponseEntity<>("JWT token is not expired: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (SignatureException e) {
			return  new ResponseEntity<>("Invalid JWT signature: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (MalformedJwtException e) {
			return  new ResponseEntity<>("Invalid JWT token: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (ExpiredJwtException e) {
			Date date = Date.from(Instant.now());
		    String requestRefreshToken = request.getRefreshToken();
		    String refreshToken = request.getRefreshToken();
		    RefreshToken entity = refreshTokenService.findByToken(refreshToken);
		    if(entity == null) {
		    	return  new ResponseEntity<>("Token does not exist", responseHeaders, HttpStatus.NOT_FOUND);
		    }else if(entity.getJwtId().equals(request.getRefreshToken()) == false){
		    	return  new ResponseEntity<>("Refresh Token has not yet expired", responseHeaders, HttpStatus.NOT_FOUND);
		    }else if(entity.isIsUsed()) {
		    	return  new ResponseEntity<>("Refresh Token has been used", responseHeaders, HttpStatus.NOT_FOUND);
		    }else if(entity.isIsRevorked()) {
		    	return  new ResponseEntity<>( "Refresh Token has been revoked", responseHeaders, HttpStatus.NOT_FOUND);
		    }else if (entity.getExpiryDate().compareTo(date) < 0) {
		    	return  new ResponseEntity<>( "Refresh Token expired", responseHeaders, HttpStatus.NOT_FOUND); 
		    }else {
		    	entity.setIsUsed(true);
		    	refreshTokenService.save(entity);
		    	Users user = userService.getById(entity.getUsers().getUsId());
		    	String tokenResponse = jwtUtils.generateTokenFromUsername(user.getUsUserName(),user.getUsId(), user.getUsEmailNo());
		    	return new ResponseEntity<>(new TokenRefreshResponse(tokenResponse, requestRefreshToken), responseHeaders, HttpStatus.OK);
		    }
			
		} catch (UnsupportedJwtException e) {
			return  new ResponseEntity<>("JWT token is unsupported: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return  new ResponseEntity<>("JWT claims string is empty: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}    
	  }


	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userService.checkMail(signUpRequest.getEmail())) {
			return  new ResponseEntity<>("Error: Email is already in use!", responseHeaders, HttpStatus.NOT_FOUND);
		}

		// Create new user's account
		try {
			Users user = new Users(idUserIentity(),signUpRequest.getUsername(),
					encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail());
			Set<String> strRoles = signUpRequest.getRole();
			Date date = Date.from(Instant.now());
			Set<UserRole> roles = new HashSet<>();
			if (strRoles == null) {
				UserRole userRole = new UserRole();
				userRole.setUsers(user);
				userRole.setRole(roleService.getByName(ERole.ROLE_USER.toString()));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "ROLE_USER":
						UserRole userRole = new UserRole();
						userRole.setUsers(user);
						userRole.setRole(roleService.getByName(ERole.ROLE_USER.toString()));
						roles.add(userRole);
						break;
					case "ROLE_ADMIN":
						UserRole adminRole = new UserRole();
						adminRole.setUsers(user);
						adminRole.setRole(roleService.getByName(ERole.ROLE_ADMIN.toString()));
						roles.add(adminRole);
						break;
					case "ROLE_MODERATOR":
						UserRole modRole = new UserRole();
						modRole.setUsers(user);
						modRole.setRole(roleService.getByName(ERole.ROLE_MODERATOR.toString()));
						roles.add(modRole);
						break;
					case "ROLE_CATEGORY":
						UserRole cateRole = new UserRole();
						cateRole.setUsers(user);
						cateRole.setRole(roleService.getByName(ERole.ROLE_CATEGORY.toString()));
						roles.add(cateRole);
						break;
					case "ROLE_ORDER_SERVICE":
						UserRole orSerRole = new UserRole();
						orSerRole.setUsers(user);
						orSerRole.setRole(roleService.getByName(ERole.ROLE_ORDER_SERVICE.toString()));
						roles.add(orSerRole);
						break;
					case "ROLE_ORDER_PRODUCT":
						UserRole orProRole = new UserRole();
						orProRole.setUsers(user);
						orProRole.setRole(roleService.getByName(ERole.ROLE_ORDER_PRODUCT.toString()));
						roles.add(orProRole);
						break;
					case "ROLE_PRODUCT":
						UserRole proRole = new UserRole();
						proRole.setUsers(user);
						proRole.setRole(roleService.getByName(ERole.ROLE_PRODUCT.toString()));
						roles.add(proRole);
						break;
					case "ROLE_SERVICE":
						UserRole serRole = new UserRole();
						serRole.setUsers(user);
						serRole.setRole(roleService.getByName(ERole.ROLE_SERVICE.toString()));
						roles.add(serRole);
						break;
					case "ROLE_SHOPINFOR":
						UserRole shopInforRole = new UserRole();
						shopInforRole.setUsers(user);
						shopInforRole.setRole(roleService.getByName(ERole.ROLE_SHOPINFOR.toString()));
						roles.add(shopInforRole);
						break;
					case "ROLE_ACCOUNT":
						UserRole accRole = new UserRole();
						accRole.setUsers(user);
						accRole.setRole(roleService.getByName(ERole.ROLE_ACCOUNT.toString()));
						roles.add(accRole);
						break;
					default:
						break;
					}
				});
			}
			userService.save(user);
			for (UserRole userRole : roles) {
				userRoleService.save(userRole);
			}
			user.setCreatedAt(date);
			user.setUserRoles(roles);
			userService.save(user);

			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public String idUserIentity() {
		 LocalDate today = LocalDate.now();
		 int numberUser = userService.getCountUserByDate(today);
		 int year = (today.getYear() % 100);
		 String number = String.valueOf(numberUser);
		 String id = null;
		 switch(number.length()) {
		 	case 1:
		 		if(numberUser != 9) {
		 			id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+"000"+(numberUser+1);	
		 		}else {
		 			id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+"00"+(numberUser+1);
		 		}
		 		break;
		 	case 2:
		 		if(numberUser != 99) {
		 			id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+"00"+(numberUser+1);	
		 		}else {
		 			id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+"0"+(numberUser+1);	
		 		}
		 		break;
		 	case 3:
		 		if(numberUser != 999) {
		 			id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+"0"+(numberUser+1);
		 		}else {
		 			id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+""+(numberUser+1);
		 		}
		 		break;
		 	case 4:
		 		id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+""+(numberUser+1);	
		 		break;
		 	default:
		 		id = "MS"+today.getDayOfMonth()+""+today.getMonthValue()+""+year+""+(numberUser+1);	
			}
		 return id;
	}

}
