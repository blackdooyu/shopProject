package shop.helloshop.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.service.ItemService;
import shop.helloshop.domain.service.OrderService;
import shop.helloshop.web.argumentresolver.Login;
import shop.helloshop.web.dto.MemberSessionDto;
import shop.helloshop.web.dto.OrderItemDto;
import shop.helloshop.web.dto.ShopCartSession;
import shop.helloshop.web.dto.SessionKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String orderForm(Model model, HttpServletRequest request, @Login MemberSessionDto memberSessionDto) {

        HttpSession session = request.getSession(false);
        Object sessionList = session.getAttribute(SessionKey.CART_SESSION);

        int totalPrice = 0;

        if(sessionList == null){
            model.addAttribute("orderItem",null);
        }

        if (sessionList != null) {
            List<ShopCartSession> cartList = (List<ShopCartSession>)sessionList;
            List<OrderItemDto> orderItemDtoList = new ArrayList<>();

            for (ShopCartSession shopCartSession : cartList) {
                Item findItem = itemService.findOne(shopCartSession.getId());
                OrderItemDto orderItemDto = new OrderItemDto(findItem.getId(),findItem.getName(), shopCartSession.getCount()
                        ,findItem.getPrice());
                orderItemDtoList.add(orderItemDto);
                totalPrice += orderItemDto.getTotalPrice();
            }

            model.addAttribute("orderItem",orderItemDtoList);
            model.addAttribute("totalPrice",totalPrice);
        }

        model.addAttribute("member",memberSessionDto);


        return "/order/shopCart";

    }

    @PostMapping("/order")
    public String orderAdd(@Login MemberSessionDto memberSessionDto,HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Object list = session.getAttribute(SessionKey.CART_SESSION);

        if (list == null){
            return "redirect:/";
        }

        List<ShopCartSession> shopCartList = (List<ShopCartSession>) list;

        orderService.order(memberSessionDto.getId(), shopCartList);

        session.removeAttribute(SessionKey.CART_SESSION);


        return "redirect:/";
    }

}
