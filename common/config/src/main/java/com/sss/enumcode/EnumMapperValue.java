package com.sss.enumcode;

import lombok.Getter;

@Getter
public class EnumMapperValue {

    private String code;
    private String text;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.text = enumMapperType.getText();
    }
}
