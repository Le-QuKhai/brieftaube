package com.haw.se1lab.users.facade.api;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/user", consumes = "application/json;charset=UTF-8")
public interface UserFacade {

    @PostMapping
    @ResponseStatus(HttpStatus.OK) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createBenutzer(@RequestBody RegestrierungsFormular formular);

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> loginBenutzer(@RequestBody Benutzer benutzer);
}
