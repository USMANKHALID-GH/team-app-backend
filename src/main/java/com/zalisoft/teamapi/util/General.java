package com.zalisoft.teamapi.util;

import com.zalisoft.teamapi.constant.GeneralConstant;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class General {



    static  void checkIfIsImage(MultipartFile file){
        if(!isImage(file.getOriginalFilename())){
            throw new BusinessException(ResponseMessageEnum.BACK_IMAGE_MSG_001);
        }

    }


    static boolean  isImage(String file){
     return Arrays.stream(GeneralConstant.IMAGE_EXTENSION)
              .anyMatch(s -> file.toLowerCase().endsWith(s));

        }


}

