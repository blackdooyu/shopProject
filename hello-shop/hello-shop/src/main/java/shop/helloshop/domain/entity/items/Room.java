package shop.helloshop.domain.entity.items;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("R")
public class Room extends Item{

    private String day;
}
