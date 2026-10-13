package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class UserObject {

    private Integer id;

    @NotBlank(message = "Please provide a first name.")
    @Size(min = 4, max = 50, message = "First name must be at least 4 characters long.")
    private String firstName;

    @Size(min = 4, max = 50, message = "Last name must be at least 4 characters long.")
    @NotBlank(message = "Please provide a last name.")
    private String lastName;

    @Max(value = 40, message = "User can't be more than 40 year's old.")
    @Positive(message = "Please provide a valid age.")
    private int age;

    @NotBlank(message = "Please enter a gender.")
    private String gender;

    @NotBlank(message = "Please enter your birthday")
    private String birthday;

    @NotBlank(message = "Please enter an email address")
    @Email(message = "Please provide a valid email adress")
    private String email;

    @NotBlank(message = "Please enter a phone number")
    @Size(min = 8, max = 11, message = "Phone number must be at least 8 digits long.")
    private String phoneNumber;

    @NotBlank(message = "Please enter an ID")
    @Size(min = 9, max = 9, message = "ID must be 9 characters long")
    private String personalId;

    @NotBlank(message = "Please select a career")
    private String career;

    private boolean isAdult;

    public UserObject() {}

   

    @DynamoDbPartitionKey
    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPersonalId() {
        return personalId;
    }

    public String getCareer() {
        return career;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public void setAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }
}