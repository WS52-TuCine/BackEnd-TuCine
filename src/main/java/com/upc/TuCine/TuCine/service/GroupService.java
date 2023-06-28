package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.GroupDto;
import com.upc.TuCine.TuCine.dto.PersonDto;
import com.upc.TuCine.TuCine.dto.save.Group.GroupSaveDto;

import java.util.List;

public interface GroupService {

    List<GroupDto> getAllGroups();

    GroupDto createGroup(GroupSaveDto groupSaveDto);

    List<PersonDto> getPersonsByGroupId(Integer id);

}