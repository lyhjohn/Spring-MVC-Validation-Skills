package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }


    /**
     * 반드시 ModelAttribute 뒤에 indingResult가 와야함
     * BindingResult 가 없으면 400 오류가 발생하면서 컨트롤러가 호출되지 않고, 오류 페이지로 이동한다.
     * BindingResult 가 있으면 오류 정보( FieldError )를 BindingResult 에 담아서 컨트롤러를 정상 호출한다.
     */
//    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
//                          Model model) {
//
//        // 검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            // ModelAttribute에 담기는것, 필드명, 메시지
//            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
//
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", "가격은 1000원 이상, 100만원 미만 까지 허용됩니다."));
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() == 0) {
//            bindingResult.addError(new FieldError("item", "quantity", "수량은 최소 1부터 최대 9,999 까지 허용합니다."));
//        }
//
//        // 특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice <= 10000) {
////                field명이 없는 글로벌에러
//                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. "));
//            }
//        }
//
//        // bindingResult는 Model에 안담아도 view에 자동으로 넘어감!
//        // 검증에 실패하면 다시 입력 폼으로
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        // 성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
//                            Model model) {
//
//        // 검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            // ModelAttribute에 담기는것, 필드명, 메시지
//            // rejectedValue : 에러 발생으로 거절됐을 때 띄울 값 추가
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null,  "상품 이름은 필수 입니다."));
//
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1000원 이상, 100만원 미만 까지 허용됩니다."));
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() == 0) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최소 1부터 최대 9,999 까지 허용합니다."));
//        }
//
//        // 특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice <= 10000) {
////                field명이 없는 글로벌에러
////                ObjectError는 필드값이 넘어오는게 아님.
//                bindingResult.addError(new ObjectError("item", null, null,  "가격 * 수량의 합은 10,000원 이상이어야 합니다. "));
//            }
//        }
//
//        // bindingResult는 Model에 안담아도 view에 자동으로 넘어감!
//        // 검증에 실패하면 다시 입력 폼으로
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        // 성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
//                            Model model) {
//
//        log.info("object={}", bindingResult.getObjectName());
//        log.info("target={}",  bindingResult.getTarget());
//
//        // 검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            // ModelAttribute에 담기는것, 필드명, 메시지
//            // rejectedValue : 에러 발생으로 거절됐을 때 띄울 값 추가
//            // String 배열안에 든 메시지를 코드를 못찾으면 배열의 다음 인덱스 코드가 호출됨
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName", "required.default"}, null,  null));
//
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() == 0) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999},null));
//        }
//
//        // 특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice <= 10000) {
////                field명이 없는 글로벌에러
////                ObjectError는 필드값이 넘어오는게 아님.
//                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000}, null));
//            }
//        }
//
//        // bindingResult는 Model에 안담아도 view에 자동으로 넘어감!
//        // 검증에 실패하면 다시 입력 폼으로
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        // 성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }


//    @PostMapping("/add")
//    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
//                            Model model) {
//
//        // bindingResult는 Model에 안담아도 view에 자동으로 넘어감!
//        // 타입 오류 등으로 바인딩 자체에 실패하면 다시 입력 폼으로
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//
//        // 검증 로직
//        // 갑싱 비었거나 공백이면 걸리게함
////        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
//
////        위 ValidationUtils.rejectIfEmptyOrWhitespace와 같음
//        if (!StringUtils.hasText(item.getItemName())) { // item에 getItemName이 비었는지 체크(null, length, containsText 한번에 체크해줌)
//            // 필드명, 에러코드를 dot 기준으로 나눠서 앞부분을 입력, 파라미터, 디폴트메시지
//            bindingResult.rejectValue("itemName", "required");
//        }
//
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 9999 || item.getQuantity() == 0) {
//            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
//        }
//
//        // 특정 필드가 아닌 복합 룰 검증(글로벌 에러)
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice <= 10000) {
////                field명이 없는 글로벌에러
////                ObjectError는 필드값이 넘어오는게 아님.
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
//            }
//        }
//        // bindingResult는 Model에 안담아도 view에 자동으로 넘어감!
//        // 타입 오류 등으로 바인딩 자체에 실패하면 다시 입력 폼으로
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//
//
//        // 성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                            Model model) {


        // bindingResult는 Model에 안담아도 view에 자동으로 넘어감!
        // 타입 오류 등으로 바인딩 자체에 실패하면 다시 입력 폼으로
        // 타입 미스매칭으로 인한 바인딩에러를 먼저 확인
        if (bindingResult.hasErrors()) {
            return "validation/v2/addForm";
        }

        itemValidator.validate(item, bindingResult);

        // 널 값 입력 등으로 인한 바인딩 에러 확인
        if (bindingResult.hasErrors()) {
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}



