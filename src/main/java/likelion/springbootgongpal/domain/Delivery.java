package likelion.springbootgongpal.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static likelion.springbootgongpal.domain.Delivery.DeliveryStatus.ESTABLISHED;
import static lombok.AccessLevel.PROTECTED;

/*
* @Entitiy : 엔티티 클래스
* @NoArgsConstructor(access = PROTECTED): 인자가 없는 기본 생성자를 생성하며, 접근 수준을 PROTECTED로 설정
* @GeneratedValue: 자동으로 값 생성함
* @OneToOne(mappedBy = "delivery"): Order 엔티티와의 일대일 관계를 매핑합니다. mappedBy 속성은 Order 엔티티의 delivery 필드에 의해 매핑되어 있음
* @Enumerated(EnumType.STRING): deliveryStatus 필드는 DeliveryStatus 열거형 값을 가짐. 이를 문자열로 저장하기 위해 EnumType.STRING을 설정
* DeliveryStatus: 배송 상태를 나타내는 enum상수 ESTABLISHED, PROGRESS, DONE 세 가지 상태 값을 가짐
* */

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Delivery {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Enumerated(STRING)
    private DeliveryStatus deliveryStatus;

    private String city;
    private String state;
    private String street;
    private String zipcode;

    public static Delivery createDelivery(Order order, String city, String state, String street, String zipcode) {
        Delivery delivery = new Delivery();
        delivery.order = order;
        delivery.deliveryStatus = ESTABLISHED;
        delivery.city = city;
        delivery.state = state;
        delivery.street = street;
        delivery.zipcode = zipcode;
        return delivery;
    }

    public enum DeliveryStatus {
        ESTABLISHED, PROGRESS, DONE
    }
}