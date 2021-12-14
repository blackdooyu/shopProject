package shop.helloshop.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.helloshop.domain.entity.UploadFile;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.web.dto.FindSort;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        em.persist(item);
    }

    public void delete(Long id) {
        Item findItem = findOne(id);
        em.remove(findItem);
    }

    public Item findOne(Long itemId) {
       return em.find(Item.class,itemId);
    }

    public List<Item> findList(String sort) {

        if (FindSort.Item_Popularity_List.equals(sort)) { // 인기순으로 정렬
            return em.createQuery("select i from Item i order by i.salesQuantity desc", Item.class)
                    .getResultList();

        } else if (FindSort.Item_Recent_List.equals(sort)) { // 최신순으로 정렬
            return em.createQuery("select i from Item i order by i.localDateTime desc",Item.class)
                    .getResultList();
        }
        return em.createQuery("select i from Item i", Item.class)//전체
                .getResultList();
    }

    public List<Item> findItemView(Long id) {
        return em.createQuery("select DISTINCT i from Item i join fetch i.uploadFiles where i.id = :id",Item.class)
                .setParameter("id",id)
                .getResultList();
    }

}
