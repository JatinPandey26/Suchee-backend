package com.suchee.app.service.impl;

import com.suchee.app.dto.MemberCreateDTO;
import com.suchee.app.dto.MemberDTO;
import com.suchee.app.entity.Member;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.MemberMapper;
import com.suchee.app.repository.MemberRepository;
import com.suchee.app.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public MemberDTO createMember(MemberCreateDTO memberCreateDTO) {

        Member member = this.memberMapper.toEntityFromCreateDto(memberCreateDTO);

        Member savedMember = this.memberRepository.save(member);

        if(Trace.member){
            Trace.log("Member created successfully " , member);
        }

        return this.memberMapper.toDto(savedMember);
    }
}
