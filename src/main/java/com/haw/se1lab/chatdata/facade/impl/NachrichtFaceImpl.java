package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.facade.api.ChatFacade;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class NachrichtFaceImpl {
    @Autowired
    private NachrichtenUseCase nachrichtenUseCase;
}
