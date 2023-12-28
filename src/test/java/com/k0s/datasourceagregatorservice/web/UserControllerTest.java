package com.k0s.datasourceagregatorservice.web;

import com.fasterxml.jackson.core.type.TypeReference;

import com.k0s.datasourceagregatorservice.AbstractWebITest;
import com.k0s.datasourceagregatorservice.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractWebITest {
    private static final String USER_API = "/users";
    private static final String USER_FILTER_API = "/users/filter";


    @Test
    @DisplayName("Get all users default behavior")
    void getAllUsers() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get(USER_API)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> users = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(7, users.size());
    }

    @Test
    @DisplayName("Get all users with sort order")
    void getAllUsersWithSortOrder() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get(USER_API)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> users = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(7, users.size());

        MockHttpServletResponse response_desc = mockMvc.perform(
                        get(USER_API)
                                .param("sortOrder", "desc")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> usersInDescOrder = objectMapper.readValue(response_desc.getContentAsString(), new TypeReference<>() {});

        Collections.reverse(users);
        assertThat(users).usingRecursiveComparison().isEqualTo(usersInDescOrder);
    }

    @Test
    @DisplayName("Get all users with sort by filed")
    void getAllUsersWithSorByField() throws Exception {

        MockHttpServletResponse response_desc = mockMvc.perform(
                        get(USER_API)
                                .param("sortOrder", "desc")
                                .param("sortBy", "name")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> usersInDescOrder = objectMapper.readValue(response_desc.getContentAsString(), new TypeReference<>() {});
        assertThat(usersInDescOrder)
                .hasSize(7)
                .extracting(User::getName)
                .containsExactlyInAnyOrder("user_name-2", "user_name-1", "name-3", "name-2", "name-2", "name-1", "name-1");
    }

    @Test
    @DisplayName("Filter users by username filed")
    void getUsersByUsernameField() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get(USER_FILTER_API)
                                .param("username", "ldap")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> users = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        assertThat(users)
                .hasSize(2)
                .extracting(User::getUsername)
                .containsExactlyInAnyOrder("ldap_username-1", "ldap_username-2");
    }

    @Test
    @DisplayName("Filter users by username, surname filed")
    void getUsersByUsernameSurnameField() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get(USER_FILTER_API)
                                .param("username", "username-2")
                                .param("surname", "user")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> users = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        assertThat(users)
                .hasSize(4)
                .extracting(User::getUsername, User::getSurname)
                .containsExactlyInAnyOrder(
                        tuple("username-2", "surname-2"),
                        tuple("ldap_username-2", "surname-2"),
                        tuple("user_login-1", "user_surname-1"),
                        tuple("user_login-2", "user_surname-2")
                );
    }

    @Test
    @DisplayName("Filter users by username, name, surname filed")
    void getUsersByUsernameNameSurnameField() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get(USER_FILTER_API)
                                .param("username", "2")
                                .param("name", "2")
                                .param("surname", "2")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        List<User> users = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        assertThat(users)
                .hasSize(3)
                .extracting(User::getUsername, User::getName, User::getSurname)
                .containsExactlyInAnyOrder(
                        tuple("username-2", "name-2", "surname-2"),
                        tuple("ldap_username-2", "name-2", "surname-2"),
                        tuple("user_login-2", "user_name-2", "user_surname-2")
                );
    }
}