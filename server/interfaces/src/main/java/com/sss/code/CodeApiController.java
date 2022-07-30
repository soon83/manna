package com.sss.code;

import com.sss.enumcode.EnumMapper;
import com.sss.response.Res;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CodeApiController {

    private final EnumMapper enumMapper;

    @GetMapping
    public ResponseEntity<Res> fetchCodeList() {
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(enumMapper.getAll()));
    }

    @GetMapping("/{codeName}")
    public ResponseEntity fetchCode(@PathVariable String codeName) {
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(enumMapper.get(codeName)));
    }
}
