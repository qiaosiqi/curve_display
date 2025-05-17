// CosCurve.java
// y = cos(x)
// ---------------------------------------------
package multicurve;

import java.awt.Color;

public class CosCurve implements CurveData {
    @Override public double getY(double x) { return Math.cos(x); }
    @Override public Color getColor() { return Color.RED; }
    @Override public String getName() { return "cos(x)"; }
}
