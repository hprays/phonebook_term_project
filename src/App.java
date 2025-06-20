import ui.MainUI;

import javax.swing.SwingUtilities;

/**
 * 프로그램 실행 진입점
 */
public class App {
    public static void main(String[] args) {
        // Swing UI는 이벤트 디스패치 스레드에서 실행해야 함
        SwingUtilities.invokeLater(() -> {
            new MainUI().setVisible(true);
        });
    }
}
