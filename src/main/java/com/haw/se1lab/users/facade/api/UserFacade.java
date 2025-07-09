package com.haw.se1lab.users.facade.api;

import com.haw.se1lab.users.common.api.datatype.RegistrierungsFormular;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/user")
public interface UserFacade {

    /**
     * Registriert einen Benutzer.
     * Falls erfolgreich, wird der Benutzer und Passwort in der Datenbank gespeichert.
     * @param formular - Registrierungsformular, enthält Benutzername, Passwort und das wiederholte Passwort.
     * @return ResponseEntity ok, wenn die Registrierung erfolgreich war
     *                        badRequest, falls die Registrierung fehl schlägt
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createBenutzer(@RequestBody RegistrierungsFormular formular);

    /**
     * Einloggen von einem Benutzer.
     * @param benutzer - Benutzer der sich anmelden möchte. enthält Benutzername und Passwort.
     * @return ResponseEntity OK, wenn das einloggen erfolgreich war
     *                        BadRequest, wenn es nicht erfolgreich war (falscher Benutzername/Passwort).
     */
    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> loginBenutzer(@RequestBody Benutzer benutzer);

    /**
     * Prüft, ob ein Benutzer, anhand der Benutzer ID, in der Datenbank enthalten ist.
     * @param userId - Id des Benutzers
     * @return ResponseEntity ok, wenn der Benutzer existiert.
     *                        badRequest, wenn der Benutzer nicht in der Datenbank enthalten ist.
     */
    @GetMapping("/exist")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> checkIfUserExists(@RequestParam Long userId);
}
