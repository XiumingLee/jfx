package cn.xiuminglee.jfx.controls;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * @author Xiuming Lee
 * @description
 */
public class ChoiceBoxDemo extends Stage {


    private Student currentStudent;

    public ChoiceBoxDemo() {
        initView();
    }

    private void initView() {
        setTitle("ChoiceBox的使用");
        setHeight(500);
        setWidth(500);
        AnchorPane ap = new AnchorPane();
        Scene scene = new Scene(ap);
        setScene(scene);
        show();

        ChoiceBox<Student> choiceBox = new ChoiceBox();
        ap.getChildren().add(choiceBox);

        Student s1 = new Student("A", 12);
        Student s2 = new Student("B", 16);
        Student s3 = new Student("C", 20);
        Student s4 = new Student("D", 22);

        //choiceBox.getItems().addAll(s1,s2,s3,s4);
        ObservableList<Student> students = FXCollections.observableArrayList(s1, s2, s3, s4);
        choiceBox.setItems(students);

        /** -------------- 以上，选择框中显示的是Student地址，下面我们做一些特殊处理 ----------- */


        /** 这样通过setConverter就可以转换成想要看到的字符串了 */
        choiceBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                System.out.println(student.getName());
                return student.getName().getValue();
            }
            @Override
            public Student fromString(String s) {
                return null;
            }
        });



        /**  通过选择模式添加监听事件 */
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                /** 这里可以根据选项进行相关处理 */
                System.out.println(newValue.getName());
                currentStudent = newValue;

                /** 为属性添加监听事件 */
                currentStudent.getName().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        int index = students.indexOf(currentStudent);
                        students.remove(currentStudent);
                        students.add(index,currentStudent);
                    }
                });
            }
        });

        /** 动态改变选中的值 */
        TextField textField = new TextField();
        textField.setPrefWidth(80);
        ap.getChildren().add(textField);
        // 距离上边框100px
        AnchorPane.setTopAnchor(textField,100.0);

        Button button = new Button("修改名称");
        ap.getChildren().add(button);
        AnchorPane.setTopAnchor(button,100.0);
        AnchorPane.setLeftAnchor(button,100.0);


        button.setOnAction(event -> {
            // 获取内容
            String newValue = textField.getText();
            currentStudent.setName(newValue);
        });

    }
}


class Student {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty age = new SimpleIntegerProperty();

    public Student(String name, Integer age) {
        this.name.setValue(name);
        this.age.setValue(age);
    }

    public SimpleStringProperty getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public SimpleIntegerProperty getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age.setValue(age);
    }

}

//class Student {
//    private String name;
//    private Integer age;
//
//    public Student(String name, Integer age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//}
