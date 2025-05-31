package com.suchee.app.service;

import com.suchee.app.dto.MemberCreateDTO;
import com.suchee.app.dto.MemberDTO;

public interface MemberService {

    MemberDTO createMember(MemberCreateDTO memberCreateDTO);

}
