package likelion.springbootgongpal.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Embeddable // 어떠한 객체들을 묶을거야
    @Data // Final, getter,setter
    @AllArgsConstructor // 모든 필드 생성자
    @NoArgsConstructor // 기본 생성자
    public class Address {
        private String city;
        private String state;
        private String street;
        private String zipcode;


    }






