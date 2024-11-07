package com.company.service;

import com.company.dto.UserDto;
import com.company.entity.Mail;
import com.company.entity.User;
import com.company.enums.Role;
import com.company.enums.Status;
import com.company.exception.AllException;
import com.company.repository.MailRepository;
import com.company.repository.UserRepository;
import com.company.util.JWTUtil;
import com.company.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    @Autowired
    private MailRepository mailRepository;

    public String signup(UserDto userDto) {

        String randomSMSCode = RandomUtil.getRandomSMSCode();

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setVisible(true);
        user.setStatus(Status.BLOCKED);

        User save = userRepository.save(user);

        try {
            SendEmailService.sendMail(userDto.getEmail(), randomSMSCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        Mail mail = new Mail();
        mail.setUser(save);
        mail.setCode(randomSMSCode);
        mail.setSentTime(LocalDateTime.now());
        mail.setStatus(Status.ACTIVE);

        mailRepository.save(mail);

        String encode = JWTUtil.encode(save.getId(), save.getLogin(), save.getRole());

        return encode;
    }

    public UserDto checkMailCode(String jwt, String code) {

        Integer id = JWTUtil.decode(jwt).getId();

        User user1 = new User();
        user1.setId(id);

        Mail mail = mailRepository.findByUserAndStatus(user1, Status.ACTIVE).get();

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(mail.getSentTime().plusMinutes(2))) {
            mail.setStatus(Status.BLOCKED);
            throw new AllException("Code is expired!!!");
        }

        if (!code.equals(mail.getCode())) {
            throw new AllException("You have entered wrong password!!!");
        }

        Integer i = userRepository.updateStatus(Status.ACTIVE, id);

        if (i == 0) {
            throw new AllException("User has not been updated!!!");
        }

        User user = userRepository.findById(id).get();

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
