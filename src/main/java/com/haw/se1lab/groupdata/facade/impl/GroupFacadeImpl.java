package com.haw.se1lab.groupdata.facade.impl;

import com.haw.se1lab.groupdata.common.api.exception.GroupNameInvalidException;
import com.haw.se1lab.groupdata.dataaccess.api.entity.Gruppe;
import com.haw.se1lab.groupdata.facade.api.GroupFacade;
import com.haw.se1lab.groupdata.logic.api.usecase.GroupUseCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class GroupFacadeImpl implements GroupFacade {

    @Autowired
    private GroupUseCase groupUseCase;

    public final Log log = LogFactory.getLog(getClass());

    @Override
    public Gruppe createGroup(Gruppe gruppe) throws GroupNameInvalidException
    {
        log.info("Starte createGroup");

        if(!groupUseCase.validateGroupName(gruppe.getName()))
        {
            throw new GroupNameInvalidException("Group name is invalid");
        }

        Gruppe erstellteGruppe =  groupUseCase.createGruppe(gruppe);
        log.info("Ende createGroup");
        return erstellteGruppe;
    }
}
