package shop.helloshop.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.helloshop.domain.entity.UploadFile;
import shop.helloshop.domain.entity.items.Clothes;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.entity.items.Phone;
import shop.helloshop.domain.service.ItemService;
import shop.helloshop.domain.service.MemberService;
import shop.helloshop.web.FileChange;
import shop.helloshop.web.argumentresolver.Login;
import shop.helloshop.web.dto.ItemForm;
import shop.helloshop.web.dto.MemberSessionDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final FileChange fileChange;




    @GetMapping("/item/select")
    public String selectForm(Model model) {
        ItemForm itemForm = new ItemForm();
        model.addAttribute("form", itemForm);
        return "item/selectItem";
    }

    @PostMapping("/item/select")
    public String selectItem(@RequestParam String select, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("item",select);
        return "redirect:/item/add/{item}";
    }

    @GetMapping("/item/add/{item}")
    public String itemAddForm(Model model, @PathVariable("item") String selectItem) {

        if (!selectItem.equals("T") && !selectItem.equals("P")) {
            return "redirect:/item/select";
        }

        ItemForm itemForm = new ItemForm();
        itemForm.setSelect(selectItem);

        model.addAttribute("form", itemForm);
        return "/item/addItem";
    }

    @PostMapping("/item/add/{item}")
    public String itemAdd(@Validated @ModelAttribute("form") ItemForm itemForm, BindingResult bindingResult, @PathVariable("item") String selectItem,
                          @Login MemberSessionDto memberSession) throws IOException {

        itemForm.setSelect(selectItem);

        if (bindingResult.hasErrors()) {
            log.info("{}",bindingResult.getFieldErrors());
            return "/item/addItem";
        }

        List<UploadFile> uploadFiles = fileChange.storeFiles(itemForm.getMultipartFileList());

        Item item = createItem(itemForm);

        for (UploadFile uploadFile : uploadFiles) {
            item.addUploadFile(uploadFile);
        }

        itemService.save(item,memberSession.getId());

        return "redirect:/";
    }

    private Item createItem(ItemForm itemForm) {

        if(itemForm.getSelect().equals("T")){
            Clothes clothes = new Clothes();
            clothes.setItemSize(itemForm.getItemSize());
            clothes.setName(itemForm.getName());
            clothes.setPrice(itemForm.getPrice());
            clothes.setQuantity(itemForm.getQuantity());
            return clothes;
        } else if (itemForm.getSelect().equals("P")) {
            Phone phone = new Phone();
            phone.setName(itemForm.getName());
            phone.setQuantity(itemForm.getQuantity());
            phone.setPhoneColor(itemForm.getPhoneColor());
            phone.setPrice(itemForm.getPrice());
            return phone;
        }
        return null;
    }
}