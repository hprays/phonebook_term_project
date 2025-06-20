package model;

import java.util.Comparator;

/**
 * 연락처를 정렬하기 위한 비교 클래스입니다.
 * 즐겨찾기 연락처는 최상단에 오고
 * 그 외에는 이름 가나다순으로 정렬됩니다.
 */
public class ContactComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact a, Contact b) {
        // 1단계: 즐겨찾기 먼저 정렬
        if (a.isFavorite() && !b.isFavorite()) return -1;
        if (!a.isFavorite() && b.isFavorite()) return 1;

        // 2단계: 이름 가나다순 (대소문자 무시)
        return a.getName().compareToIgnoreCase(b.getName());
    }
}
