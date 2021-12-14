package shop.helloshop.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shop.helloshop.domain.entity.UploadFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemViewForm {

    private String name;

    private int price;

    private int quantity;

    private int salesQuantity;

    private List<String> imgFiles = new ArrayList<>();

    public static ItemViewForm createViewForm(String name, int price, int quantity , int salesQuantity, List<UploadFile> uploadFiles) {
        ItemViewForm itemViewForm = new ItemViewForm();
        itemViewForm.setName(name);
        itemViewForm.setPrice(price);
        itemViewForm.setQuantity(quantity);
        itemViewForm.setSalesQuantity(salesQuantity);

        for (UploadFile uploadFile : uploadFiles) {
            itemViewForm.getImgFiles().add(uploadFile.getUniqueURL());
        }
        return itemViewForm;

    }
}
