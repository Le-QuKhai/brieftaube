package com.haw.se1lab.users.facade.api;

import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public interface UserFacade {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createBenutzer(@RequestBody Benutzer user);
}
