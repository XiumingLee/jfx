/**
 * @Author: Xiuming Lee
 * @Date: 2019/10/21 9:26
 * @Version 1.0
 * @Describe:
 */
module jfx.module {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    opens cn.xiuminglee to javafx.fxml; // 和fxml文件有关？
    exports cn.xiuminglee;
}