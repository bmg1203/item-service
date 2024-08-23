package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    /*
    Spring Contact 안에서 쓰면 싱클톤이기 때문에 크게 static 안써도 되긴함
    하지만 따로 new 해서 쓰거나 이럴 떄는 static을 안하면 생성한 만큼 이 부분이 생성된다.
    */
    //실제로는 hashMap을 사용하면 안됨, static으로 선언함
    //여러 곳에서 접근하고 싱글톤이기 때문에 HashMap이 아니라 ConcurrentHashMap을 사용해야함
    private static final Map<Long, Item> store = new HashMap<>();
    //얘도 long을 사용하면 동시 접근 시 값이 꼬일 수 있다.
    //automatic long이나 등등을 사용해야 한다.
    private static long sequence = 0L; //static

    //아이템 저장 기능
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    //단일 아이템 조회
    public Item findById(Long id) {
        return store.get(id);
    }

    //모든 아이템 조회
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    //아이템 수정
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        //실제로는 dto를 사용해서 id를 제외한 값만 받는 것을 해서 받는 것이 맞다.
        //하지만 여기서는 작은 프로젝트라 그대로 사용했다.
    }

    //테스트용으로 데이터를 다 날리는 기능
    public void clearStore() {
        store.clear();
    }
}
