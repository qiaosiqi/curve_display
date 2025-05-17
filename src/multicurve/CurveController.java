// CurveController.java
// 每条曲线的控制面板，按钮点击后禁用
// ---------------------------------------------
package multicurve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CurveController extends JPanel {
    public CurveController(CurveCanvas canvas) {
        setLayout(new GridLayout(4, 1, 5, 5));
        JButton sinBtn = new JButton("sin(x)");
        JButton cosBtn = new JButton("cos(x)");
        JButton linearBtn = new JButton("x");
        JButton quadBtn = new JButton("x^2");
        add(sinBtn); add(cosBtn); add(linearBtn); add(quadBtn);

        ActionListener listener = e -> {
            JButton btn = (JButton) e.getSource();
            btn.setEnabled(false);
            String cmd = btn.getText();
            CurveData curve;
            switch (cmd) {
                case "sin(x)": curve = new SinCurve(); break;
                case "cos(x)": curve = new CosCurve(); break;
                case "x":     curve = new LinearCurve(); break;
                case "x^2":   curve = new QuadCurve(); break;
                default:       curve = new SinCurve();
            }
            new CurveThread(curve, canvas).start();
        };
        sinBtn.addActionListener(listener);
        cosBtn.addActionListener(listener);
        linearBtn.addActionListener(listener);
        quadBtn.addActionListener(listener);
    }
}