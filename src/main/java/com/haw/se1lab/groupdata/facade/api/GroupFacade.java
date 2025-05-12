package com.haw.se1lab.groupdata.facade.api;

import com.haw.se1lab.groupdata.common.api.exception.GroupNameInvalidException;
import com.haw.se1lab.groupdata.dataaccess.api.entity.Gruppe;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/group")
public interface GroupFacade {

    /**
     * Erstellt eine Gruppe und speichert diese in der Datenbank
     * 10. Das System speichert die Gruppeninformationen in der Datenbank
     * @param gruppe die Gruppe die erstellt werden soll
     * @return die erstellte Gruppe
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Gruppe createGroup(@RequestBody Gruppe gruppe) throws GroupNameInvalidException;
    //ruft intern auch den Use-Case zur Gruppennamenvalidierung auf
}
