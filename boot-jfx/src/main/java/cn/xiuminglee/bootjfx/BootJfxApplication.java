package cn.xiuminglee.bootjfx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
public class BootJfxApplication {
    public static void main(String[] args) {
        Application.launch(JavaFxApp.class,args);
    }
}
