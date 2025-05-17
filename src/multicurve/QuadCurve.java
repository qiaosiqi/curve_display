// QuadCurve.java
// y = x^2
// ---------------------------------------------
package multicurve;

import java.awt.Color;

public class QuadCurve implements CurveData {
    @Override public double getY(double x) { return x * x; }
    @Override public Color getColor() { return Color.MAGENTA; }
    @Override public String getName() { return "x^2"; }
}