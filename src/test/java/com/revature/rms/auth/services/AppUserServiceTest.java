package com.revature.rms.auth.services;

import com.revature.rms.auth.dtos.AppUserDto;
import com.revature.rms.auth.dtos.Credentials;
import com.revature.rms.auth.dtos.RegisterDto;
import com.revature.rms.auth.entities.AppUser;
import com.revature.rms.auth.entities.UserRole;
import com.revature.rms.auth.exceptions.AuthenticationException;
import com.revature.rms.auth.exceptions.BadRequestException;
import com.revature.rms.auth.exceptions.ResourceNotFoundException;
import com.revature.rms.auth.repositories.AppUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AppUserService appUserService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    //*************************************GET ALL TESTS******************************************************************

    @Test
    public void shouldReturnAllAppUsers(){

        List<AppUser> mockUsers = new ArrayList<AppUser>();
        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        mockUsers.add(new AppUser(1,"test1@revature.com", "test1", "password", mockRoles));
        mockUsers.add(new AppUser(2,"test2@revature.com", "test2", "password", mockRoles));
        mockUsers.add(new AppUser(3,"test3@revature.com", "test3", "password", mockRoles));
        Iterable<AppUser> mockIterable = new ArrayList<AppUser>(mockUsers);

        when(userRepository.findAll()).thenReturn(mockIterable);

        List<AppUserDto> result = appUserService.getAllUsers();

        assertEquals(result.size(), 3);

    }

    @Test
    public void shouldThrowResourceNotFoundErrorWhenIterableIsEmpty(){

        Iterable<AppUser> mockUsers = new ArrayList<AppUser>();
        when(userRepository.findAll()).thenReturn(mockUsers);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
           appUserService.getAllUsers();
        });

    }

    //*************************************GET BY ID TESTS******************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenGivenACorrectId(){

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);

        when(userRepository.findAppUserById(1)).thenReturn(mockUser);

        AppUserDto result = appUserService.getUserById(1);

        AppUserDto expectedResult = new AppUserDto(mockUser);

        assertEquals(result, expectedResult);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenAnIdOfZero(){

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.getUserById(0);
        });

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenNoUserIsFound(){

        when(userRepository.findAppUserById(1)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
           appUserService.getUserById(1);
        });

    }

    //*************************************AUTHENTICATE TESTS******************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenGivenValidCredentials(){

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);

        Credentials mockCreds = new Credentials("test1", "password");

        when(userRepository.findAppUserByUsernameAndPassword(mockCreds.getUsername(),mockCreds.getPassword())).thenReturn(mockUser);

        AppUserDto result = appUserService.authenticate(mockCreds);

        AppUserDto expectedResult = new AppUserDto(mockUser);

        assertEquals(result,expectedResult);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsNullUsername(){

        Credentials mockCreds = new Credentials(null, "password");

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.authenticate(mockCreds);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsEmptyUsername(){

        Credentials mockCreds = new Credentials("     ", "password");

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.authenticate(mockCreds);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsNullPassword(){

        Credentials mockCreds = new Credentials("test1", null);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.authenticate(mockCreds);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsEmptyPassword(){

        Credentials mockCreds = new Credentials("test1", "     ");

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.authenticate(mockCreds);
        });

    }

    @Test
    public void shouldThrowAuthenticationExceptionWhenGivenCredsThatDontExist(){

        Credentials mockCreds = new Credentials("test1", "password");

        when(userRepository.findAppUserByUsernameAndPassword(mockCreds.getUsername(), mockCreds.getPassword())).thenReturn(null);

        AuthenticationException thrown = assertThrows(AuthenticationException.class, () -> {
           appUserService.authenticate(mockCreds);
        });

    }

    //*************************************REGISTER TESTS******************************************************************

    @Test
    public void shouldReturnNewlyCreatedUserWhenGivenAValidUser(){

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "password", mockRegisterRoles);

        when(userRepository.save(any(AppUser.class))).thenReturn(mockUser);

        AppUserDto result = appUserService.register(mockRegisterDto);

        AppUserDto expectedResult = new AppUserDto(mockUser);

        assertEquals(result, expectedResult);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterUsernameIsNull(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", null, "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.register(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterUsernameIsEmpty(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "     ", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.register(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterEmailIsNull(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,null, "test1", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.register(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterEmailIsEmpty(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"      ", "test1", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.register(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterPasswordIsNull(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", null, mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.register(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterPasswordIsEmpty(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "     ", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.register(mockRegisterDto);
        });

    }

    //*************************************UPDATE TESTS******************************************************************

    @Test
    public void shouldReturnNewlyUpdatedUser(){

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "password", mockRegisterRoles);

        when(userRepository.save(any(AppUser.class))).thenReturn(mockUser);
        when(userRepository.findAppUserById(mockRegisterDto.getId())).thenReturn(mockUser);

        AppUserDto result = appUserService.updateUser(mockRegisterDto);

        AppUserDto expected = new AppUserDto(mockUser);

        assertEquals(result, expected);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateUsernameIsNull(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", null, "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateUsernameIsEmpty(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "     ", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateEmailIsNull(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,null, "test1", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateEmailIsEmpty(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"      ", "test1", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatePasswordIsNull(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", null, mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatePasswordIsEmpty(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "     ", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateIdIsZero(){

        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(0,"test1@revature.com", "test1", "password", mockRegisterRoles);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            appUserService.updateUser(mockRegisterDto);
        });

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenIdCannotBeFound(){

        List<UserRole> mockRoles = new ArrayList<UserRole>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        List<String> mockRegisterRoles = new ArrayList<String>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "password", mockRegisterRoles);

        when(userRepository.findAppUserById(mockUser.getId())).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
           appUserService.updateUser(mockRegisterDto);
        });

    }

}
