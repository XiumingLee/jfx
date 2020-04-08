package cn.xiuminglee.jfx.OtherDemo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Xiuming Lee
 * @description 动态读取fxml文件展示
 */
public class DynamicReadFxml extends Stage {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Pane root = new FlowPane();


    public DynamicReadFxml() {
        initView();
    }

    private void initView(){
        setTitle("动态读取fxml文件展示");
        setHeight(500);
        setWidth(500);
        setResizable(false);

        /** 初始化页面 */
        btn1 = new Button("视图1");
        btn2 = new Button("视图2");
        btn3 = new Button("视图3");

        /** 用于存放加载的fxml内容  */
        Pane pane = new Pane();

        root.getChildren().addAll(btn1,btn2,btn3,pane);
        Scene scene = new Scene(root);
        setScene(scene);


        /** 为按钮设置事件 */
        btn1.setOnAction(event -> {
            pane.getChildren().clear();
            //
            try {
                Parent parent =  FXMLLoader.load(getClass().getResource("/cn/xiuminglee/jfx/OtherDemo/DynamicReadFxml/fxml001.fxml"));
                pane.getChildren().add(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn2.setOnAction(event -> {
            pane.getChildren().clear();
            //
            try {
                Parent parent =  FXMLLoader.load(getClass().getResource("/cn/xiuminglee/jfx/OtherDemo/DynamicReadFxml/fxml002.fxml"));
                pane.getChildren().add(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn3.setOnAction(event -> {
            pane.getChildren().clear();
            //
            try {
                Parent parent =  FXMLLoader.load(getClass().getResource("/cn/xiuminglee/jfx/OtherDemo/DynamicReadFxml/fxml003.fxml"));
                pane.getChildren().add(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }



}
