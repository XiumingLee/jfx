package cn.xiuminglee.jfx.image;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;


/**
 * @author Xiuming Lee
 * @description 小案例，图片转成字符串图像
 */
public class ImageDemo002 extends Stage {

    public ImageDemo002() throws IOException {
        setTitle("图片转成字符串图像");
        setHeight(500);
        setWidth(500);

        /** 初始化页面 */
        Pane pane = new Pane();
        ImageView imageView = new ImageView();
        pane.getChildren().add(imageView);
        Scene scene = new Scene(pane);
        setScene(scene);

        String path = "file:///Users/xiuming/Desktop/test.png";
        Image image = new Image(path,460,546,false,true);
        imageView.setImage(image);


        PixelReader pixelReader = image.getPixelReader();

        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = pixelReader.getColor(j, i);
                int red = (int)(color.getRed() * 255);
                stringBuilder.append(getStrValue(red));
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }

        FileWriter fr = new FileWriter("/Users/xiuming/Desktop/test.txt");
        fr.write(stringBuilder.toString());
        fr.flush();
        fr.close();
    }


    /**
     *
     * @param value 像素R(red) G(green) B(blue)三原色，其中一个的值
     * @return
     */
    private String getStrValue(int value){
        if (-1 < value && value < 50){
            return "#";
        } else if (50 >= value && value < 100){
            return "*";
        } else if(value >= 100 && value < 150){
            return "g";
        }else if(value >= 150 && value < 200){
            return "U";
        }else if(value >= 200 && value < 260){
            return " ";
        }
        return " ";
    }
}
