package com.haw.se1lab.users.facade.api;

import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/user")
public interface UserFacade {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // defines the HTTP status of the returned HTTP response
    public void createBenutzer(@RequestBody Benutzer professor);
}
