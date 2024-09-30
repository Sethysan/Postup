package com.techelevator.controller;

import com.techelevator.dao.ReplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ReplyController {

    @Autowired
    private ReplyDao replyDao;
}
