package shop.helloshop.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.items.Clothes;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.entity.items.Phone;
import shop.helloshop.domain.repository.ItemRepository;
import shop.helloshop.domain.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item,Long memberId) {
        Member findMember = memberRepository.findOne(memberId);
        item.setMember(findMember);
        item.setLocalDateTime(LocalDateTime.now());
        item.setSalesQuantity(0);
        itemRepository.save(item);
    }


    @Transactional
    public void update(Long itemId,String name,int price,int quantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.update(name,price,quantity);
    }

    @Transactional
    public void remove(Long itemId) {
        itemRepository.delete(itemId);
    }

    public Item findOne(Long id) {
       return itemRepository.findOne(id);
    }

    public List<Item> findHomeList() {
        return itemRepository.homeList();
    }

    public List<Item> findList(String sort,int page) {
        return itemRepository.findList(sort,page);
    }

    public Long findCount() {
        return itemRepository.findItemCount();
    }

    public Phone phoneView(Long id) {
        return itemRepository.findViewPhone(id);
    }

    public Clothes clothesView(Long id) {
        return itemRepository.findViewClothes(id);
    }



}
