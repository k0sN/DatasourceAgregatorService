package com.k0s.datasourceagregatorservice.web;

import com.k0s.datasourceagregatorservice.entity.User;
import com.k0s.datasourceagregatorservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of users",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    )
            })
    public List<User> getAllUsers(
            @Parameter(description = "Field by which the result will be sorted {id(default), username, name, surname}")
            @RequestParam Optional<String> sortBy,
            @Parameter(description = "Sort order {asc,desc}")
            @RequestParam Optional<String> sortOrder) {
        return userService.findAll(sortBy, sortOrder);
    }

    @GetMapping("/filter")
    @Operation(summary = "Get users by selecting filters")
    @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of users",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    )
            })
    public List<User> getUsersFiltered(
            @Parameter(description = "Username of user to be searched")
            @RequestParam Optional<String> username,
            @Parameter(description = "Name of user to be searched")
            @RequestParam Optional<String> name,
            @Parameter(description = "Surname of user to be searched")
            @RequestParam Optional<String> surname,
            @Parameter(description = "Field by which the result will be sorted {id(default), username, name, surname}")
            @RequestParam Optional<String> sortBy,
            @Parameter(description = "Sort order {asc,desc}")
            @RequestParam Optional<String> sortOrder) {
        return userService.findByRequestParams(username, name, surname, sortBy, sortOrder);
    }
}


