package cn.xiuminglee.bootjfx;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/10/19 12:50
 * @Version 1.0
 * @Describe: JavaFX的主类
 */
public class JavaFxApp extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        // 将JavaFX相关的类注册到Spring应用中去
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext-> {
                genericApplicationContext.registerBean(Application.class,()->JavaFxApp.this);
                genericApplicationContext.registerBean(Parameters.class, this::getParameters);
                genericApplicationContext.registerBean(HostServices.class,this::getHostServices);
        };
        // 构建并启动Spring应用
        this.context = new SpringApplicationBuilder()
                .sources(BootJfxApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Spring Boot and JavaFX");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}
