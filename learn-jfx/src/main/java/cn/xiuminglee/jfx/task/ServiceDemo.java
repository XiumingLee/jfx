package cn.xiuminglee.jfx.task;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

/**
 * @author Xiuming Lee
 * @description JavaFX Service任务demo
 * @see javafx.concurrent.Service
 * @see javafx.concurrent.Worker
 */
public class ServiceDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        ProgressBar pb = new ProgressBar(0);
        pb.setPrefWidth(200);

        Button start = new Button("开始");
        Button cancel = new Button("取消");
        Button restart = new Button("重启");
        Button reset = new Button("重置");

        Label l1 = new Label("state");
        Label l2 = new Label("value");
        Label l3 = new Label("title");
        Label l4 = new Label("message");

        AnchorPane an = new AnchorPane();
        an.setStyle("-fx-background-color: #ffffff");

        hBox.getChildren().addAll(start,cancel,restart,reset,pb,l1,l2,l3,l4);

        an.getChildren().addAll(hBox);

        AnchorPane.setTopAnchor(hBox,100.0);
        AnchorPane.setLeftAnchor(hBox,100.0);

        Scene scene = new Scene(an);

        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);

        primaryStage.show();

        /** 以下是演示多任务相关 */
        MyService myService = new MyService();

        /** 按钮事件 */
        start.setOnAction(event -> {
            myService.start();
        });

        cancel.setOnAction(event -> {
            myService.cancel();
        });

        restart.setOnAction(event -> {
            myService.restart();
        });

        reset.setOnAction(event -> {
            myService.reset();
        });

        /** 监听事件，监听的属性本质来自于内部的Task,所有，Task有的它也有 */
        // 进度 监听事件ChangeListener。
        myService.progressProperty().addListener((observable, oldValue, newValue) -> {
            // 这里的newValue 就是updateProgress()方法中 `workDone/max` 的值，也就是，需要的百分比值
            pb.setProgress(newValue.doubleValue());
        });

        // 监听myService的标题属性
        myService.titleProperty().addListener((observable, oldValue, newValue) -> {
            l3.setText(newValue);
        });

        // 监听消息属性
        myService.messageProperty().addListener((observable, oldValue, newValue) -> {
            l4.setText(newValue);
        });

        // 监听Value属性
        myService.valueProperty().addListener((observable, oldValue, newValue) -> {
            l2.setText(newValue.toString());
        });

        // 状态属性
        myService.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("当前任务状态：" + "【" + newValue +"】");
            l1.setText(newValue.toString());
        });

    }
}

class MyService extends Service<Number>{

    @Override
    protected void executeTask(Task<Number> task) {
        // 非必须。createTask()返回的task会到这里。我们可以在这里做一些监听。但是一般都是在最外层通过Service进行监听
        super.executeTask(task);
    }

    @Override
    protected Task<Number> createTask() {
        Task<Number> task = new Task<>() {
            @Override
            protected Number call() throws Exception {
                this.updateTitle("计算任务");

                double progress = 0;
                int sum = 0;
                int total = 100;
                for (int i = 0; i <= total; i++) {
                    if (this.isCancelled()){ // 判断如果取消，跳出操作。
                        break;
                    }

                    sum+=i;
                    progress = (double) i / (double) total;

                    /** 更新进度 */
                    this.updateProgress(i,total);

                    /** 发送消息 */
                    if (progress < 0.5) {
                        this.updateMessage("请耐心等待！");
                    } else if (progress < 0.8) {
                        this.updateMessage("马上就好！");
                    } else if (progress < 1) {
                        this.updateMessage("即将完成！");
                    } else if (progress >= 1) {
                        this.updateMessage("已完成！");
                    }

                    TimeUnit.MILLISECONDS.sleep(50);
                }
                return sum;
            }
        };
        return task;
    }
}
