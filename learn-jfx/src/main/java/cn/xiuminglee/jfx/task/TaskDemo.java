package cn.xiuminglee.jfx.task;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Xiuming Lee
 * @description JavaFX Task任务demo
 * @see javafx.concurrent.Task
 * @see javafx.concurrent.Worker
 */
public class TaskDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        ProgressBar pb = new ProgressBar(0);
        pb.setPrefWidth(200);

        Button start = new Button("开始");
        Button cancel = new Button("取消");

        Label l1 = new Label("state");
        Label l2 = new Label("value");
        Label l3 = new Label("title");
        Label l4 = new Label("message");

        AnchorPane an = new AnchorPane();
        an.setStyle("-fx-background-color: #ffffff");

        hBox.getChildren().addAll(start,cancel,pb,l1,l2,l3,l4);

        an.getChildren().addAll(hBox);

        AnchorPane.setTopAnchor(hBox,100.0);
        AnchorPane.setLeftAnchor(hBox,100.0);

        Scene scene = new Scene(an);

        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);

        primaryStage.show();

        /** 以下是演示多任务相关 */
        MyTask myTask = new MyTask();
        ExecutorService es = Executors.newSingleThreadExecutor();


        // 开始任务
        start.setOnAction(actionEvent->{
            es.submit(myTask);
        });

        // 取消任务
        cancel.setOnAction(actionEvent->{
            myTask.cancel();
        });

        /** 监听任务的回调事件相关 */

        // 进度 监听事件ChangeListener。
        myTask.progressProperty().addListener((observable, oldValue, newValue) -> {
            // 这里的newValue 就是updateProgress()方法中 `workDone/max` 的值，也就是，需要的百分比值
            pb.setProgress(newValue.doubleValue());
        });

        // 监听myTask的标题属性
        myTask.titleProperty().addListener((observable, oldValue, newValue) -> {
            l3.setText(newValue);
        });

        // 监听消息属性
        myTask.messageProperty().addListener((observable, oldValue, newValue) -> {
            l4.setText(newValue);
        });

        // 监听Value属性
        myTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            l2.setText(newValue.toString());
        });

        // 状态属性
        myTask.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("当前任务状态：" + "【" + newValue +"】");
            l1.setText(newValue.toString());
        });



    }
}

class MyTask extends Task<Number> {

    /**
     * 1、此方法运行在UI线程中，可以在此更新界面，但是需要将组件传进来。推荐，在UI线程中监听Task事件进行更新UI
     * 2、call方法的返回值，会作为此方法的参数
     * 3、注意点：这些方法调用父类的方法都是有其实现的，不要删除。一般放在方法的第一行就可以了。
     * @param value call方法的返回值
     */
    @Override
    protected void updateValue(Number value) {
        super.updateValue(value);
        out("updateValue",String.valueOf(value));
    }

    /**
     * 必须实现的方法，可以在这里做一些业务处理
     * @return
     * @throws Exception
     */
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

    private void out(String methodName,String msg){
        System.out.println(methodName + "------【" + Thread.currentThread().getName() + "】" + "：" + msg);
    }
}
