package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String item(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);

        System.out.println("itemId = " + itemId);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {

        return "/basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item")Item item, Model model) {

//        @ModelAttribute의 역할 1
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);

//        @ModelAttribute의 역할 2 (해당 name에 맞게 model에 들어가는 name과 일치시켜 addAttribute해줌) => 자동추가, 생략가능
//        model.addAttribute("item",item);

        return "/basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {

        // @ModelAttribute의 ("name") 을 생략하면 그옆의 선언한 객체의 클래스명에서 맨 앞글자를 소문자로 바꾼 이름이 객체로 model에 담기게 됨!)
//        @ModelAttribute의 역할 1
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);

//        @ModelAttribute의 역할 2 (해당 name에 맞게 model에 들어가는 name과 일치시켜 addAttribute해줌) => 자동추가, 생략가능
//        model.addAttribute("item",item);

        return "/basic/item";
    }
    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     * => 단순 타입인 경우 => @RequestParam자동적용, 임의로 생성한 객체인 경우 => @ModelAttribute가 적용됨
     */
    @PostMapping("/add")
    public String addItemV4(Item item, Model model) {

        itemRepository.save(item);

        return "/basic/item";
    }


    /**
     * test용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }
}
