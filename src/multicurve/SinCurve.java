// SinCurve.java
// y = sin(x)
// ---------------------------------------------
package multicurve;

import java.awt.Color;

public class SinCurve implements CurveData {
    @Override public double getY(double x) { return Math.sin(x); }
    @Override public Color getColor() { return Color.BLUE; }
    @Override public String getName() { return "sin(x)"; }
}