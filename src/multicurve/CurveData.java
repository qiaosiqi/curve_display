// CurveData.java
// 抽象接口，所有曲线类型实现它
// ---------------------------------------------
package multicurve;

import java.awt.Color;

public interface CurveData {
    double getY(double x);
    Color getColor();
    String getName();
}
