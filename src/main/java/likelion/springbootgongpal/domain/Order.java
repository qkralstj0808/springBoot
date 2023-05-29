package likelion.springbootgongpal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;


/*
* @Table(name = "orders"): 해당 엔티티가 orders 테이블과 매핑되어 있음
* @ManyToOne(fetch = LAZY): Member 엔티티와의 다대일 관계를 매핑
* @JoinColumn(name = "member_id"): @ManyToOne 관계, name 은 member_id를 FK로 사용
*
* */
@Entity
@Table(name = "orders") //  해당 엔티티가 orders 테이블과 매핑되어 있음
@Getter // 필드에 대한 Getter 메서드를 자동으로 생성
@NoArgsConstructor(access = PROTECTED) // 인자가 없는 기본 생성자를 생성, 접근 수준을 PROTECTED로 설정햄
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = LAZY) // Member 엔티티와의 다대일 관계를 매핑
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    /*
    *
    * OrderItem 엔티티와의 일대다 관계를 매핑
    *  mappedBy = "order"는 OrderItem 엔티티의 order 필드에 의해 매핑되어 있다는 것을 의미함
    *
    * */
    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }

    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.orderDate = LocalDateTime.now();
        order.orderStatus = OrderStatus.ORDERED;
        order.delivery = Delivery.createDelivery(order, member.getAddress().getCity(),
                member.getAddress().getState(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode());
        for (OrderItem orderItem : orderItems) {
            order.orderItemList.add(orderItem);
            orderItem.setOrder(order);
        }
        return order;
    }

    public void cancel() {
        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.DONE) {
            throw new IllegalStateException("배송 완료했습니다");
        }
        this.orderStatus = OrderStatus.CANCELED;
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}