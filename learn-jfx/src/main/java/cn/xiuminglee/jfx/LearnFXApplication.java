package cn.xiuminglee.jfx;

import cn.xiuminglee.jfx.controls.ChoiceBoxDemo;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Xiuming Lee
 * @description
 */
public class LearnFXApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = new ChoiceBoxDemo();
        primaryStage.show();
    }
}
