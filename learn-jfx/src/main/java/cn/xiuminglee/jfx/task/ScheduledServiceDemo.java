package cn.xiuminglee.jfx.task;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author Xiuming Lee
 * @description JavaFX ScheduledService任务demo
 * @see javafx.concurrent.ScheduledService
 * @see javafx.concurrent.Worker
 */
public class ScheduledServiceDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        Button start = new Button("开始");
        Button cancel = new Button("取消");
        Button restart = new Button("重启");
        Button reset = new Button("重置");

        Label l2 = new Label("value");

        AnchorPane an = new AnchorPane();
        an.setStyle("-fx-background-color: #ffffff");

        hBox.getChildren().addAll(start,cancel,restart,reset,l2);

        an.getChildren().addAll(hBox);

        AnchorPane.setTopAnchor(hBox,100.0);
        AnchorPane.setLeftAnchor(hBox,100.0);

        Scene scene = new Scene(an);

        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);

        primaryStage.show();

        /** 以下是演示多任务相关 */
        MyScheduledService myScheduledService = new MyScheduledService();

        /** 设置时间相关 */

        // javafx.util.Duration;
        myScheduledService.setDelay(Duration.seconds(5)); // 第一次运行：多久之后开始执行任务

        myScheduledService.setPeriod(Duration.seconds(2)); // 多久之后再次运行

        myScheduledService.setRestartOnFailure(true);  // 失败后重新启动
        // 还有一些其他属性，自行查看

        /** 按钮事件 */
        start.setOnAction(event -> {
            myScheduledService.start();
        });

        cancel.setOnAction(event -> {
            myScheduledService.cancel();
        });

        restart.setOnAction(event -> {
            myScheduledService.restart();
        });

        reset.setOnAction(event -> {
            myScheduledService.reset();
        });

        /** 监听事件 */
        myScheduledService.valueProperty().addListener((observable, oldValue, newValue) -> {
            // 注意这里，一次结果的变化，会调用两次这个方法。oldValue有值时，newValue为null，newValue有值时，oldValue为null.☆☆☆☆☆☆☆☆☆☆☆☆
            System.out.println("oldValue：" + oldValue);
            System.out.println("newValue：" + newValue);
            if (newValue != null) {
                l2.setText(newValue.toString());
            }
        });


    }
}

class MyScheduledService extends ScheduledService<Number> {
    int sum = 0;

    @Override
    protected Task<Number> createTask() {
        System.out.println("createTask()");

        Task<Number> task = new Task<Number>() {

            @Override
            protected void updateValue(Number value) {
                super.updateValue(value);
                System.out.println(value);
                if (value.intValue() > 10){
                    // ☆☆☆☆☆☆☆☆☆☆☆☆ 注意这里：内类调用外部类的方法 ☆☆☆☆☆☆☆☆☆☆☆☆
                    MyScheduledService.this.cancel();
                    System.out.println("任务取消");
                }
            }

            @Override
            protected Number call() throws Exception {
                sum+=1;
                TimeUnit.MILLISECONDS.sleep(50);
                return sum;
            }
        };
        return task;
    }
}
