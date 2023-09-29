package com.zalisoft.teamapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zalisoft.teamapi.constant.GeneralConstant;
import com.zalisoft.teamapi.dto.TeamDto;
import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
@Slf4j
public class General {



   public static  void checkIfIsImage(String  file){
        if(!isImage(file)){
            throw new BusinessException(ResponseMessageEnum.BACK_IMAGE_MSG_001);
        }
    }



    public  static  <T> T  convertToJson(String json, Class<T> class_ ){

        try {
            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, class_);
        }catch (Exception e){
            log.info("infor {}", e.getMessage());
            throw new BusinessException(ResponseMessageEnum.BACK_JSON_CONVERTOR_MSG_001);
        }

    }


   private static boolean  isImage(String file){
     return Arrays.stream(GeneralConstant.IMAGE_EXTENSION)
              .anyMatch(s -> file.toLowerCase().endsWith(s));

        }





}

