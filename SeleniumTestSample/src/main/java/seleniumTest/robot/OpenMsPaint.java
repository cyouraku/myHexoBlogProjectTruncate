package seleniumTest.robot;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

public class OpenMsPaint {

    public static void main(String[] args) throws Exception {

        final Robot rb = new Robot();

        new Thread() {
            public void run() {
                rb.delay(2000); // 模拟回车
                rb.keyPress(KeyEvent.VK_ENTER);
                rb.keyRelease(KeyEvent.VK_ENTER);
            }
        }.start();

        rb.delay(3000);

        //Press print screen key
        rb.keyPress(KeyEvent.VK_PRINTSCREEN);
        rb.delay(500);

        // 设置开始菜单的大概位置
        int x = 40;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10; // 鼠标移动到开始菜单,
        rb.mouseMove(x, y);
        rb.delay(500);

        // 单击开始菜单
        RobotCommonUtil.clickLMouse(rb, x, y, 500);
        rb.delay(1000);

        // 运行mspaint命令cmd enter
        int[] ks = { KeyEvent.VK_M, KeyEvent.VK_S, KeyEvent.VK_P, KeyEvent.VK_A, KeyEvent.VK_I, KeyEvent.VK_N,
                KeyEvent.VK_T, KeyEvent.VK_ENTER, };
        RobotCommonUtil.pressKeys(rb, ks, 500);
        rb.mouseMove(400, 400);
        rb.delay(500);

        //Paste
        RobotCommonUtil.doParse(rb);
        rb.delay(500);


        new Thread() {
            public void run() {
                rb.delay(1000); // 回车
                rb.keyPress(KeyEvent.VK_ENTER);
                rb.keyRelease(KeyEvent.VK_ENTER);
            }
        }.start();

        JOptionPane.showMessageDialog(null, "演示完毕!");
    }
}
