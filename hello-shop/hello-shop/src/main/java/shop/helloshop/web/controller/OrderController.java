package shop.helloshop.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import shop.helloshop.domain.service.OrderService;
import shop.helloshop.web.dto.OrderItemDto;
import shop.helloshop.web.dto.SessionKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


//    @PostMapping("/item/view/session/cart")
//    public String shopCartSession(HttpServletRequest request) {
//
//        HttpSession session = request.getSession(false);
//        List<OrderItemDto> list = (List<OrderItemDto>)session.getAttribute(SessionKey.CART_SESSION);
//
//        if(!list.isEmpty()){
//            list.
//        }
//
//        session.setAttribute(SessionKey.CART_SESSION,);
//
//        return "";
//    }


}
