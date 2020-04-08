package cn.xiuminglee.jfx;

import cn.xiuminglee.jfx.ImageDemo.ImageDemo001;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Xiuming Lee
 * @description
 */
public class LearnFXApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageDemo001 demo001 = new ImageDemo001();
        demo001.show();
    }
}
