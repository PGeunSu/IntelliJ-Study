package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements  MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); //회원을 저장할 하나의 데이터베이스
    // 실무에서는 동시성 문제가 있을 수 있어서 공유되는 변수일 때는 ConcurrentHashMap 을 사용해야 하나 예제라서 단순하게 사용
    private static long sequence = 0L; //일련번호

    /*
    여기서 Map이란?
    java에서는 자료구조를 Map이라고 이름 지었습니다. Map에 저장되는 데이터는 ‘key-value’ pair라는 형식을 갖고 있으며 key와 value는 매칭 됩니다.
    이는 ‘주민등록번호 - 사람 이름’ 관계와 비슷하다고 보면 됩니다.
    주민등록번호는 한 명도 똑같은 사람이 없다. -> key값은 중복되지 않는다.
    주민등록번호는 있는데 사람 이름이 없는 경우는 없다. -> key없는 value는 없음
    이름이 있는 사람이 주민등록번호가 없는 경우는 없다. -> value없는 key는 없음
    주민등록번호가 달라도 사람이름은 똑같을 수 있다.(동명이인) -> value는 중복 가능

     */

    @Override
    public Member save(Member member) {

       member.setId(++sequence); //멤버를 저장할 때 일려번호 값을 1씩 증가
       store.put(member.getId(), member);
       return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //null 값이 올 수도 있으므로 ofNullable 사용
    }


    @Override
    public Optional<Member> findByName(String name) {

        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
        //같은 경우만 찾아서 반환 //찾는 값이 없을 경우 Optional 에서 자동으로 Null 반환
        // 람다식으로 사용되었으며 필터기능을 줘서 member.getName 값이 파라미터로 넘어온 name 값이랑 같은 지 확인
        // name 이 같은 경우에는 필터링이 된다.
        // findAny()는 결과가 Optional 로 반환된다. Map 에서 루프를 다 돌면서 하나 찾으면 찾은 값을 반환
        // 끝까지 돌렸는데 없으면 Optional 에 null 이 포함되서 반영된다.

    }


    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    //Optional : 자바 8에서 들어간 기능, 반환값이 null 인 경우 자동으로 null 반환

    public void clearStore(){
        store.clear(); // clear() : 데이터 정리(삭제)
    }

}
