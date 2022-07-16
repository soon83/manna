package com.sss.domain.meeting.fragment;

import com.sss.exception.common.InvalidParamException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class MeetLocation {

    @Column(length = 6)
    private String zipcode;
    @Column(length = 255)
    private String address1;
    @Column(length = 255)
    private String address2;

    @Builder
    public MeetLocation(
            String zipcode,
            String address1,
            String address2
    ) {
        if (ObjectUtils.isEmpty(zipcode)) throw new InvalidParamException("MeetLocation zipcode");
        if (ObjectUtils.isEmpty(address1)) throw new InvalidParamException("MeetLocation address1");
        if (ObjectUtils.isEmpty(address2)) throw new InvalidParamException("MeetLocation address2");

        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }
}
