package likelion.springbootgongpal.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/*
* @ManyToOne(fetch = LAZY): Order 엔티티와의 다대일 관계를 매핑
* @JoinColumn(name = "order_id"): 다대일 관계, order_id라는 FK 칼럼 이름을 사용하겠다는 의미
* @ManyToOne(fetch = LAZY): Item 엔티티와의 다대일 관계 매핑
* @JoinColumn(name = "item_id"): 다대일 관계,  item_id라는 FK 칼럼 이름을 사용하겠다는 의미
*
* */


@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer price;
    private Integer count;

    /**
     * 스태틱 팩토리 메서드
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int orderCount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.price = orderPrice;
        orderItem.count = orderCount;
        // 연관관계 편의 메서드
        item.removeStock(orderCount);
        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItemList().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItem().add(this);
    }

    /**
     * 비즈니스 로직
     */
    public int getTotalPrice() {
        return this.getPrice() * this.getCount();
    }

    public void cancel() {
        this.getItem().addStock(count);
    }
}