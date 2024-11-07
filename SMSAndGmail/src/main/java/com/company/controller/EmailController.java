package com.company.controller;

import com.company.service.SendEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {

    @GetMapping("/{mail}")
    public ResponseEntity<String> sendEmail(@PathVariable String mail) throws Exception {

//        SendEmailService.sendMail(mail, );

        return ResponseEntity.ok().body("salom");
    }

}

