package shop.helloshop.domain.entity;

import lombok.Getter;
import lombok.Setter;
import shop.helloshop.domain.entity.items.Item;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String name;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;




}
