package shop.helloshop.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;
import shop.helloshop.domain.entity.UploadFile;
import shop.helloshop.domain.entity.items.ItemSize;
import shop.helloshop.domain.entity.items.PhoneColor;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemViewForm {

    private String name;

    private int price;

    private int quantity;

    private int salesQuantity;

    private ItemSize itemSize;

    private PhoneColor phoneColor;

    private List<String> imgFiles = new ArrayList<>();



    public static ItemViewForm createPhoneForm(String name, int price, int quantity , int salesQuantity,PhoneColor phoneColor ,List<UploadFile> uploadFiles) {
        ItemViewForm itemViewForm = new ItemViewForm();
        itemViewForm.setName(name);
        itemViewForm.setPrice(price);
        itemViewForm.setQuantity(quantity);
        itemViewForm.setSalesQuantity(salesQuantity);
        itemViewForm.setPhoneColor(phoneColor);

        for (UploadFile uploadFile : uploadFiles) {
            itemViewForm.getImgFiles().add(uploadFile.getUniqueURL());
        }
        return itemViewForm;

    }

    public static ItemViewForm createClothesForm(String name, int price, int quantity , int salesQuantity,ItemSize itemSize ,List<UploadFile> uploadFiles) {
        ItemViewForm itemViewForm = new ItemViewForm();
        itemViewForm.setName(name);
        itemViewForm.setPrice(price);
        itemViewForm.setQuantity(quantity);
        itemViewForm.setSalesQuantity(salesQuantity);
        itemViewForm.setItemSize(itemSize);

        for (UploadFile uploadFile : uploadFiles) {
            itemViewForm.getImgFiles().add(uploadFile.getUniqueURL());
        }
        return itemViewForm;

    }
}
