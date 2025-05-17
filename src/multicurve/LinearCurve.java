// LinearCurve.java
// y = x
// ---------------------------------------------
package multicurve;

import java.awt.Color;

public class LinearCurve implements CurveData {
    @Override public double getY(double x) { return x; }
    @Override public Color getColor() { return Color.GREEN.darker(); }
    @Override public String getName() { return "x"; }
}