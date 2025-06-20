package ui;

import model.Contact;
import model.ContactManager;
import util.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import model.Contact;
import model.ContactManager;
import util.Validator;

public class MainUI extends JFrame {

    private final ContactManager manager = new ContactManager();
    private DefaultTableModel tableModel;
    private JTable table;

    private final JTextField nameField = new JTextField(10);
    private final JTextField phoneField = new JTextField(10);
    private final JTextField emailField = new JTextField(10);
    private final JTextField searchField = new JTextField(10);

    public MainUI() {
        setTitle("Phonebook App");
        setSize(750, 420);
        setLocationRelativeTo(null);

        // 종료 버튼 눌렀을 때 종료 확인 창 띄우기
        // 종료 버튼 누르면 저장 후 종료 여부 확인
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        MainUI.this,
                        "저장하고 종료하시겠습니까?",
                        "종료 확인",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    // 저장 수행
                    manager.saveToFile("data/contact_list.csv");
                    System.exit(0);
                } else if (result == JOptionPane.NO_OPTION) {
                    System.exit(0);  // 저장하지 않고 종료
                }
                // Cancel 또는 닫기 버튼은 아무것도 안 함
            }
        });

        // 전역 UI 폰트 설정
        UIManager.put("Label.font", new Font("맑은 고딕", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("맑은 고딕", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("맑은 고딕", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("맑은 고딕", Font.PLAIN, 14));
        UIManager.put("Table.rowHeight", 22);

        tableModel = new DefaultTableModel(new String[]{"이름", "전화번호", "이메일", "즐겨찾기"}, 0);
        table = new JTable(tableModel);

        initComponents();
        manager.loadFromFile("data/contact_list.csv");
        refreshTable();
        setVisible(true);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(new JLabel("이름"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("전화번호"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("이메일"));
        inputPanel.add(emailField);

        JButton addButton = new JButton("추가");
        addButton.setBackground(new Color(210, 150, 250));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addContact());
        inputPanel.add(addButton);

        JButton updateButton = new JButton("수정");
        updateButton.setBackground(new Color(255, 140, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> updateContact());
        inputPanel.add(updateButton);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("검색"));
        searchPanel.add(searchField);

        JButton searchButton = new JButton("검색");
        searchButton.setBackground(new Color(70, 150, 240));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchContacts());
        searchPanel.add(searchButton);

        JButton deleteButton = new JButton("삭제");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteContact());

        JButton favoriteButton = new JButton("즐겨찾기 토글");
        favoriteButton.setBackground(new Color(255, 195, 0));
        favoriteButton.setForeground(Color.WHITE);
        favoriteButton.setFocusPainted(false);
        favoriteButton.addActionListener(e -> toggleFavorite());

        JButton saveButton = new JButton("저장");
        saveButton.setBackground(new Color(0, 200, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> manager.saveToFile("data/contact_list.csv"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(deleteButton);
        buttonPanel.add(favoriteButton);
        buttonPanel.add(saveButton);

        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showError("모든 정보를 입력하세요.");
            return;
        }

        if (!Validator.isValidEmail(email)) {
            showError("이메일 형식이 올바르지 않습니다.");
            return;
        }

        if (!Validator.isValidPhone(phone)) {
            showError("전화번호 형식이 올바르지 않습니다.");
            return;
        }

        manager.addContact(new Contact(name, phone, email, false));
        refreshTable();
        clearFields();
    }

    private void updateContact() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("수정할 연락처를 선택하세요.");
            return;
        }

        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showError("모든 정보를 입력하세요.");
            return;
        }

        if (!Validator.isValidEmail(email)) {
            showError("이메일 형식이 올바르지 않습니다.");
            return;
        }

        if (!Validator.isValidPhone(phone)) {
            showError("전화번호 형식이 올바르지 않습니다.");
            return;
        }

        boolean isFav = tableModel.getValueAt(row, 3).equals("★");

        Contact updated = new Contact(name, phone, email, isFav);
        manager.updateContact(row, updated);
        refreshTable();
        clearFields();
    }

    private void deleteContact() {
        int row = table.getSelectedRow();
        if (row != -1) {
            manager.removeContact(row);
            refreshTable();
        } else {
            showError("삭제할 연락처를 선택하세요.");
        }
    }

    private void toggleFavorite() {
        int row = table.getSelectedRow();
        if (row != -1) {
            manager.toggleFavorite(row);
            refreshTable();
        } else {
            showError("즐겨찾기할 연락처를 선택하세요.");
        }
    }

    private void searchContacts() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            refreshTable();
            return;
        }

        tableModel.setRowCount(0);
        for (Contact c : manager.searchByName(keyword)) {
            tableModel.addRow(new Object[]{
                    c.getName(), c.getPhoneNumber(), c.getEmail(), c.isFavorite() ? "★" : ""
            });
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Contact c : manager.getContacts()) {
            tableModel.addRow(new Object[]{
                    c.getName(), c.getPhoneNumber(), c.getEmail(), c.isFavorite() ? "★" : ""
            });
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "에러", JOptionPane.ERROR_MESSAGE);
    }
}
