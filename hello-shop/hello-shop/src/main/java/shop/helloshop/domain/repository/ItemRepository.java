package shop.helloshop.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.helloshop.domain.entity.items.Clothes;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.entity.items.Phone;
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

    public List<Item> homeList() {

            return em.createQuery("select i from Item i order by i.salesQuantity desc", Item.class)
                    .setFirstResult(0)
                    .setMaxResults(8)
                    .getResultList();

    }

    public List<Item> findList(String sort,int page) {

        int result = (page * 16) - 16;


        if (FindSort.Item_Popularity_List.equals(sort)) { // 인기순으로 정렬
            return em.createQuery("select i from Item i order by i.salesQuantity desc", Item.class)
                    .setFirstResult(result)
                    .setMaxResults(16)
                    .getResultList();

        } else if (FindSort.Item_Recent_List.equals(sort)) { // 최신순으로 정렬
            return em.createQuery("select i from Item i order by i.localDateTime desc",Item.class)
                    .setFirstResult(result)
                    .setMaxResults(16)
                    .getResultList();
        }
        return null;
    }

    public Long findItemCount() {
       return em.createQuery("select count(i.id) from Item i",Long.class)
                .getSingleResult();
    }

    public Phone findViewPhone(Long id) {
       return em.find(Phone.class, id);
    }

    public Clothes findViewClothes(Long id) {
        return em.find(Clothes.class,id);
    }


}
