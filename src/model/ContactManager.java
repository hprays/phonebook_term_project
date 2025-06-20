package model;

import storage.FileStorage;
import java.util.*;

/**
 * 연락처 전체를 리스트로 관리하고
 * 추가, 삭제, 수정, 정렬, 검색 기능을 처리하는 클래스
 */
public class    ContactManager {
    private final List<Contact> contacts = new ArrayList<>();
    private final String SAVE_PATH = "data/contact_list.csv";

    // 연락처 추가 후 자동 정렬
    public void addContact(Contact contact) {
        contacts.add(contact);
        sortContacts();
    }

    // 연락처 수정 (해당 인덱스에 새 연락처로 교체)
    public void updateContact(int index, Contact updated) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, updated);
            sortContacts();
        }
    }

    // 연락처 삭제
    public void removeContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
        }
    }

    // 즐겨찾기 토글 (★ 표시 on/off)
    public void toggleFavorite(int index) {
        if (index >= 0 && index < contacts.size()) {
            Contact c = contacts.get(index);
            c.setFavorite(!c.isFavorite());
            sortContacts();
        }
    }

    // 전체 연락처 반환 (복사본)
    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    // 이름에 키워드가 포함된 연락처 검색
    public List<Contact> searchByName(String keyword) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getName().contains(keyword)) {
                result.add(c);
            }
        }
        return result;
    }

    // 즐겨찾기 우선 → 이름 가나다순으로 정렬
    public void sortContacts() {
        Collections.sort(contacts, (a, b) -> {
            if (a.isFavorite() && !b.isFavorite()) return -1;
            if (!a.isFavorite() && b.isFavorite()) return 1;
            return a.getName().compareToIgnoreCase(b.getName());
        });
    }

    // 연락처 저장 (파일로)
    public void saveToFile(String path) {
        FileStorage.saveContactsToFile(contacts, path);
    }

    // 연락처 불러오기
    public void loadFromFile(String path) {
        List<Contact> loaded = FileStorage.loadContactsFromFile(path);
        if (loaded != null) {
            contacts.clear();
            contacts.addAll(loaded);
            sortContacts();
        }
    }
}


