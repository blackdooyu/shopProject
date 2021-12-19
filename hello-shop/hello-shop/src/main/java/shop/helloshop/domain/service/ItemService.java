package shop.helloshop.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.UploadFile;
import shop.helloshop.domain.entity.items.Clothes;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.entity.items.Phone;
import shop.helloshop.domain.entity.items.PhoneColor;
import shop.helloshop.domain.repository.ItemRepository;
import shop.helloshop.domain.repository.MemberRepository;
import shop.helloshop.web.dto.ItemViewForm;

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
    public void updatePhone(Long itemId, Phone phone,List<UploadFile> updateUploadFiles) {
        Phone viewPhone = itemRepository.findViewPhone(itemId);
        viewPhone.setName(phone.getName());
        viewPhone.setPrice(phone.getPrice());
        viewPhone.setPhoneColor(phone.getPhoneColor());
        viewPhone.setQuantity(phone.getQuantity());

        viewPhone.getUploadFiles().remove(0);

        for (UploadFile uploadFile : updateUploadFiles) {
            viewPhone.addUploadFile(uploadFile);
        }

    }

    @Transactional
    public void updateClothes(Long itemId, Clothes clothes,List<UploadFile> updateUploadFiles) {
        Clothes viewClothes = itemRepository.findViewClothes(itemId);
        viewClothes.setName(clothes.getName());
        viewClothes.setPrice(clothes.getPrice());
        viewClothes.setItemSize(clothes.getItemSize());


        viewClothes.setQuantity(clothes.getQuantity());
        viewClothes.getUploadFiles().remove(0);

        for (UploadFile uploadFile : updateUploadFiles) {
            viewClothes.addUploadFile(uploadFile);
        }
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
