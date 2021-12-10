package shop.helloshop.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.web.repository.ItemRepository;
import shop.helloshop.web.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private MemberRepository memberRepository;
    private ItemRepository itemRepository;

    @Transactional
    private void save(Item item,Long memberId) {
        Member findMember = memberRepository.findOne(memberId);
        item.setMember(findMember);
        item.setLocalDateTime(LocalDateTime.now());
        itemRepository.save(item);
    }

    @Transactional
    private void update(Long itemId,String name,int price,int quantity) {
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

    public List<Item> findList(String sort) {
        return itemRepository.findList(sort);
    }


}
