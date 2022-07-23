package com.sss.member;

import com.sss.domain.member.MemberQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public Page<MemberDto.MainResponse> memberInfoListMapper(Page<MemberQuery.Main> memberInfoList) {
        return memberInfoList.map(MemberDto.MainResponse::new);
    }

    public List<MemberDto.MainResponse> memberInfoListMapper(List<MemberQuery.Main> list) {
        return list.stream()
                .map(MemberDto.MainResponse::new)
                .collect(Collectors.toList());
    }

    public List<MemberDto.WithInterestListResponse> memberWithInterestInfoListMapper(List<MemberQuery.WithInterestInfo> list) {
        return list.stream()
                .map(withInterestInfo -> {
                    var interestInfoList = withInterestInfo.getInterestInfoList();
                    var interestResponseList = memberInterestInfoListMapper(interestInfoList);
                    return new MemberDto.WithInterestListResponse(withInterestInfo, interestResponseList);
                })
                .collect(Collectors.toList());
    }

    public List<MemberDto.InterestResponse> memberInterestInfoListMapper(List<MemberQuery.InterestInfo> list) {
        return list.stream()
                .map(MemberDto.InterestResponse::new)
                .collect(Collectors.toList());
    }
}
