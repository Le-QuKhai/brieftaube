package com.haw.se1lab.groupdata.logic.impl;

import com.haw.se1lab.groupdata.dataaccess.api.entity.Gruppe;
import com.haw.se1lab.groupdata.dataaccess.api.repo.GruppeRepository;
import com.haw.se1lab.groupdata.logic.api.usecase.GroupUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GroupUseCaseImpl implements GroupUseCase {

    @Autowired
    private GruppeRepository gruppeRepository;

    @Override
    public boolean validateGroupName(String groupName)
    {
        return !groupName.isEmpty() && groupName.length() <= 50;
    }

    @Override
    public Gruppe createGruppe(Gruppe gruppe)
    {
        Assert.notNull(gruppe, "Gruppe must not be null");
        return gruppeRepository.save(gruppe);
    }
}
