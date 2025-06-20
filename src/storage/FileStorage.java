package storage;

import model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 연락처를 사람이 이해하기 쉬운 CSV(즐겨찾기/일반) 형식으로 저장/불러오는 클래스
 */
public class FileStorage {

    // 연락처 저장 (CSV: 즐겨찾기 / 일반)
    public static void saveContactsToFile(List<Contact> contacts, String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Contact c : contacts) {
                String favText = c.isFavorite() ? "즐겨찾기" : "일반";
                writer.printf("%s,%s,%s,%s%n",
                        escape(c.getName()),
                        escape(c.getPhoneNumber()),
                        escape(c.getEmail()),
                        favText);
            }
        } catch (IOException e) {
            System.out.println("CSV 저장 실패: " + e.getMessage());
        }
    }

    // 연락처 불러오기 (CSV: 즐겨찾기 / 일반)
    public static List<Contact> loadContactsFromFile(String path) {
        List<Contact> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length >= 4) {
                    String name = unescape(tokens[0]);
                    String phone = unescape(tokens[1]);
                    String email = unescape(tokens[2]);
                    boolean favorite = tokens[3].equals("즐겨찾기");
                    list.add(new Contact(name, phone, email, favorite));
                }
            }
        } catch (IOException e) {
            System.out.println("CSV 불러오기 실패: " + e.getMessage());
        }

        return list;
    }

    // 쉼표 등 특수문자 회피 처리
    private static String escape(String value) {
        return value.replace(",", "\\,").replace("\n", "").replace("\r", "");
    }

    private static String unescape(String value) {
        return value.replace("\\,", ",");
    }
}
