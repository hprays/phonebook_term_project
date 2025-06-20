package model;

import java.io.Serializable;

/**
 * 연락처 정보를 담는 클래스
 * 이름, 전화번호, 이메일, 즐겨찾기 여부 포함
 */
public class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;
    private boolean favorite;

    public Contact(String name, String phoneNumber, String email, boolean favorite) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.favorite = favorite;
    }

    // getter/setter
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public boolean isFavorite() { return favorite; }

    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}

