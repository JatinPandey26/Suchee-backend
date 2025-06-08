package com.suchee.app.service;

import com.suchee.app.dto.MemberCreateDTO;
import com.suchee.app.dto.MemberDTO;
import com.suchee.app.dto.TeamDTO;

import java.util.List;

public interface MemberService {

    MemberDTO createMember(MemberCreateDTO memberCreateDTO);
}
