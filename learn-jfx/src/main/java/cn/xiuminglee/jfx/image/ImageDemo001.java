package cn.xiuminglee.jfx.image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Xiuming Lee
 * @description 测试将粘贴板中的图片保存到电脑
 */
public class ImageDemo001 extends Stage {

    public ImageDemo001() {
        setTitle("测试将粘贴板中的图片保存到电脑");
        setHeight(500);
        setWidth(500);
        // 设置不可最大化，不可拉伸
        //setResizable(false);

        //
        ImageView imageView = new ImageView();
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);
        Pane root = new Pane();

        Button button = new Button();
        button.setText("测试按钮");

        root.getChildren().add(imageView);
        root.getChildren().add(button);
        Scene scene = new Scene(root);
        setScene(scene);


        /** 设置快捷键 */
        // 设置快捷键  Ctr + Shift + V
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.V,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        // 获取系统的粘贴板
        Clipboard clipboard = Clipboard.getSystemClipboard();
        scene.getAccelerators().put(keyCombination, new Runnable() {
            @Override
            public void run() {
                System.out.println("hasString()：" + clipboard.hasString());
                System.out.println("hasFiles()：" + clipboard.hasFiles());
                System.out.println("hasHtml()：" + clipboard.hasHtml());
                System.out.println("hasImage()：" + clipboard.hasImage());
                System.out.println("hasRtf()：" + clipboard.hasRtf());
                System.out.println("hasUrl()：" + clipboard.hasUrl());

                if (clipboard.hasString()){
                    System.out.println(clipboard.getString());
                }
                if (clipboard.hasFiles()){
                    File file = clipboard.getFiles().get(0);
                    System.out.println("文件名称：" + file.getName());
                }
                if (clipboard.hasHtml()){
                    System.out.println(clipboard.getHtml());
                }
                if (clipboard.hasImage()){
                    imageView.setImage(clipboard.getImage());
                }
                if (clipboard.hasRtf()){
                    System.out.println("getRtf()：" + clipboard.getRtf());
                }
                if (clipboard.hasUrl()){
                    System.out.println("getUrl()：" + clipboard.getUrl());
                }
            }
        });
        button.setOnAction((event)->{
            System.out.println("hasString()：" + clipboard.hasString());
            System.out.println("hasFiles()：" + clipboard.hasFiles());
            System.out.println("hasHtml()：" + clipboard.hasHtml());
            System.out.println("hasImage()：" + clipboard.hasImage());
            System.out.println("hasRtf()：" + clipboard.hasRtf());
            System.out.println("hasUrl()：" + clipboard.hasUrl());

            if (clipboard.hasString()){
                System.out.println(clipboard.getString());
            }
            if (clipboard.hasFiles()){
                File file = clipboard.getFiles().get(0);
                System.out.println("文件名称：" + file.getName());
            }
            if (clipboard.hasHtml()){
                System.out.println(clipboard.getHtml());
            }
            if (clipboard.hasImage()){ // 粘贴板图片
                Image image = clipboard.getImage();

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //File file = new File("/Users/xiuming/Desktop/test.png");
                try {
                    ImageIO.write(bufferedImage,"png" ,stream);
                    //ImageIO.write(bufferedImage,"png" ,file); // 直接写到文件中
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] bytes = stream.toByteArray();

                InputStream inputStream = new ByteArrayInputStream(bytes);
                Image imageNew = new Image(inputStream);

                imageView.setImage(imageNew);

            }
            if (clipboard.hasRtf()){
                System.out.println("getRtf()：" + clipboard.getRtf());
            }
            if (clipboard.hasUrl()){
                System.out.println("getUrl()：" + clipboard.getUrl());
            }
        });
    }
}
