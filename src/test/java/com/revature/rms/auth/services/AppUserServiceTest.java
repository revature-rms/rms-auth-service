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

        //Arrange
        List<AppUser> mockUsers = new ArrayList<>();
        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        mockUsers.add(new AppUser(1,"test1@revature.com", "test1", "password", mockRoles));
        mockUsers.add(new AppUser(2,"test2@revature.com", "test2", "password", mockRoles));
        mockUsers.add(new AppUser(3,"test3@revature.com", "test3", "password", mockRoles));
        Iterable<AppUser> mockIterable = new ArrayList<>(mockUsers);

        when(userRepository.findAll()).thenReturn(mockIterable);

        //Act
        List<AppUserDto> result = appUserService.getAllUsers();

        //Assert
        assertEquals(result.size(), 3);

    }

    @Test
    public void shouldThrowResourceNotFoundErrorWhenIterableIsEmpty(){

        //Arrange
        Iterable<AppUser> mockUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(mockUsers);

        //Act/Assert
        assertThrows(ResourceNotFoundException.class, () -> appUserService.getAllUsers());

    }

    //*************************************GET BY ID TESTS******************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenGivenACorrectId(){

        //Arrange
        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);

        when(userRepository.findAppUserById(1)).thenReturn(mockUser);

        //Act
        AppUserDto result = appUserService.getUserById(1);

        AppUserDto expectedResult = new AppUserDto(mockUser);

        //Assert
        assertEquals(result, expectedResult);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenAnIdOfZero(){

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.getUserById(0));

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenNoUserIsFound(){

        //Arrange
        when(userRepository.findAppUserById(1)).thenReturn(null);

        //Act/Assert
        assertThrows(ResourceNotFoundException.class, () -> appUserService.getUserById(1));

    }

    //*************************************AUTHENTICATE TESTS******************************************************************

    @Test
    public void shouldReturnAppUserDtoWhenGivenValidCredentials(){

        //Arrange
        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);

        Credentials mockCreds = new Credentials("test1", "password");

        when(userRepository.findAppUserByUsernameAndPassword(mockCreds.getUsername(),mockCreds.getPassword())).thenReturn(mockUser);

        //Act
        AppUserDto result = appUserService.authenticate(mockCreds);

        AppUserDto expectedResult = new AppUserDto(mockUser);

        //Assert
        assertEquals(result,expectedResult);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsNullUsername(){

        //Arrange
        Credentials mockCreds = new Credentials(null, "password");

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.authenticate(mockCreds));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsEmptyUsername(){

        //Arrange
        Credentials mockCreds = new Credentials("     ", "password");

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.authenticate(mockCreds));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsNullPassword(){

        //Arrange
        Credentials mockCreds = new Credentials("test1", null);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.authenticate(mockCreds));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenInvalidCredsEmptyPassword(){

        //Arrange
        Credentials mockCreds = new Credentials("test1", "     ");

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.authenticate(mockCreds));

    }

    @Test
    public void shouldThrowAuthenticationExceptionWhenGivenCredsThatDontExist(){

        //Arrange
        Credentials mockCreds = new Credentials("test1", "password");

        when(userRepository.findAppUserByUsernameAndPassword(mockCreds.getUsername(), mockCreds.getPassword())).thenReturn(null);

        //Act/Assert
        assertThrows(AuthenticationException.class, () -> appUserService.authenticate(mockCreds));

    }

    //*************************************REGISTER TESTS******************************************************************

    @Test
    public void shouldReturnNewlyCreatedUserWhenGivenAValidUser(){

        //Arrange
        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "password", mockRegisterRoles);

        when(userRepository.save(any(AppUser.class))).thenReturn(mockUser);

        //Act
        AppUserDto result = appUserService.register(mockRegisterDto);

        AppUserDto expectedResult = new AppUserDto(mockUser);

        //Assert
        assertEquals(result, expectedResult);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterUsernameIsNull(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", null, "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.register(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterUsernameIsEmpty(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "     ", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.register(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterEmailIsNull(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,null, "test1", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.register(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterEmailIsEmpty(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"      ", "test1", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.register(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterPasswordIsNull(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", null, mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.register(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenRegisterPasswordIsEmpty(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "     ", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.register(mockRegisterDto));

    }

    //*************************************UPDATE TESTS******************************************************************

    @Test
    public void shouldReturnNewlyUpdatedUser(){

        //Arrange
        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "password", mockRegisterRoles);

        when(userRepository.save(any(AppUser.class))).thenReturn(mockUser);
        when(userRepository.findAppUserById(mockRegisterDto.getId())).thenReturn(mockUser);

        //Act
        AppUserDto result = appUserService.updateUser(mockRegisterDto);

        AppUserDto expected = new AppUserDto(mockUser);

        //Assert
        assertEquals(result, expected);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateUsernameIsNull(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", null, "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateUsernameIsEmpty(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "     ", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateEmailIsNull(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,null, "test1", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateEmailIsEmpty(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"      ", "test1", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatePasswordIsNull(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", null, mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatePasswordIsEmpty(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "     ", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateIdIsZero(){

        //Arrange
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(0,"test1@revature.com", "test1", "password", mockRegisterRoles);

        //Act/Assert
        assertThrows(BadRequestException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenIdCannotBeFound(){

        //Arrange
        List<UserRole> mockRoles = new ArrayList<>();
        mockRoles.add(UserRole.ADMIN);
        mockRoles.add(UserRole.TRAINING_SITE_MANAGER);
        AppUser mockUser = new AppUser(1,"test1@revature.com", "test1", "password", mockRoles);
        List<String> mockRegisterRoles = new ArrayList<>();
        mockRegisterRoles.add("Admin");
        mockRegisterRoles.add("Training Site Manager");
        RegisterDto mockRegisterDto = new RegisterDto(1,"test1@revature.com", "test1", "password", mockRegisterRoles);

        when(userRepository.findAppUserById(mockUser.getId())).thenReturn(null);

        //Act/Assert
        assertThrows(ResourceNotFoundException.class, () -> appUserService.updateUser(mockRegisterDto));

    }

}
