package cn.xiuminglee.jfx;

import cn.xiuminglee.jfx.ImageDemo.ImageDemo002;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Xiuming Lee
 * @description
 */
public class LearnFXApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = new ImageDemo002();
        primaryStage.show();
    }
}
