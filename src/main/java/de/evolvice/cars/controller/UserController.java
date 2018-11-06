package de.evolvice.cars.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.evolvice.cars.entity.User;
import de.evolvice.cars.exception.ResourceNotFoundException;
import de.evolvice.cars.payload.UserIdentityAvailability;
import de.evolvice.cars.payload.UserSummary;
import de.evolvice.cars.repository.UserRepository;
import de.evolvice.cars.security.CurrentUser;
import de.evolvice.cars.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * User Controller provide rest services for operations of user
 * 
 * @author sayed
 *
 */
@RestController
@RequestMapping("/api")
@Api(
		value = "BodyType",
		description = "Operations for users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * Get the currently logged in user
	 * 
	 * @param currentUser
	 * @return
	 */
	@ApiOperation(
			value = "View a summary of the currently logged in user",
			response = UserSummary.class)
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "Authorization",
					value = "Authorization token",
					required = true,
					dataType = "string",
					paramType = "header") })
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}

	/**
	 * Check if a username is available for registration
	 * 
	 * @param username
	 * @return
	 */
	@ApiOperation(
			value = "Check if username is availabile for registration",
			response = UserIdentityAvailability.class)
	@GetMapping("/user/checkUsernameAvailability")
	public UserIdentityAvailability checkUsernameAvailability(@RequestParam(
			value = "username") String username) {
		Boolean isAvailable = !userRepository.existsByUsername(username);
		return new UserIdentityAvailability(isAvailable);
	}

	/**
	 * Check if an email is available for registration
	 * 
	 * @param email
	 * @return
	 */
	@ApiOperation(
			value = "Check if email is availabile for registration",
			response = UserIdentityAvailability.class)
	@GetMapping("/user/checkEmailAvailability")
	public UserIdentityAvailability checkEmailAvailability(@RequestParam(
			value = "email") String email) {
		Boolean isAvailable = !userRepository.existsByEmail(email);
		return new UserIdentityAvailability(isAvailable);
	}

	/**
	 * Get the public profile of a user
	 * 
	 * @param username
	 * @return
	 */
	@ApiOperation(
			value = "View the public profile of a user",
			response = User.class)
	@GetMapping("/users/{username}")
	public User getUserProfile(@PathVariable(
			value = "username") String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

}
