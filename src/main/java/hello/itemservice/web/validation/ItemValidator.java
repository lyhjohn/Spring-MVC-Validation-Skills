package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.PostConstruct;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) { // 넘어오는 클래스가 item or 자식클래스인지 먼저 확인
        return Item.class.isAssignableFrom(clazz);
        // 들어오는 타입과 Item 타입이 같은지 먼저 검증 후 Item 타입만 통과시킴
        // item == clazz ?
        // item == subItem ? (item의 자식클래스(subItem)도 통과시킴)
    }

    /**
     *
     * @param target = item
     * @param errors = BindingResult의 부모클래스
     */
    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target; // 캐스팅
        
        // 값이 비었거나 공백이면 걸리게함
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
//        아래 hasText를 통한 검증과 같음
        
        if (!StringUtils.hasText(item.getItemName())) { // item에 getItemName이 비었는지 체크(null, length, containsText 한번에 체크해줌)
            // 필드명, 에러코드를 dot 기준으로 나눠서 앞부분을 입력, 파라미터, 디폴트메시지
            errors.rejectValue("itemName", "required");
        }



        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() == 0) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증(글로벌 에러)
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice <= 10000) {
//                field명이 없는 글로벌에러
//                ObjectError는 필드값이 넘어오는게 아님.
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
