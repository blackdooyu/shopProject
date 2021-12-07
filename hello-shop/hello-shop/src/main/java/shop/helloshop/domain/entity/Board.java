package shop.helloshop.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String mainText;

    private int stars;

    @OneToMany(mappedBy = "board" ,cascade = CascadeType.ALL)
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //수정 편의 메서드
    public void updateBoard(String title,String mainText,List<UploadFile> uploadFiles) {
        this.title = title;
        this.mainText = mainText;
        setUploadFiles(uploadFiles);
    }
}
