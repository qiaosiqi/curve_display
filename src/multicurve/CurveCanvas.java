// CurveCanvas.java
// 绘图面板，绘制所有曲线并更新状态
// ---------------------------------------------
package multicurve;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurveCanvas extends JPanel {
    private final Map<CurveData, Double> curveOffsets = new ConcurrentHashMap<>();
    private final Map<String, JLabel> statusLabels;

    public CurveCanvas(Map<String, JLabel> statusLabels) {
        this.statusLabels = statusLabels;
        setBackground(Color.WHITE);
    }

    public void updateCurve(CurveData curve, double offset) {
        curveOffsets.put(curve, offset);
        // 更新状态面板：计算 x=w/2 处的 y 值
        int w = getWidth();
        double realX = (w / 2.0 + offset) * 0.05;
        double y = curve.getY(realX);
        JLabel label = statusLabels.get(curve.getName());
        if (label != null) {
            label.setText(curve.getName() + "(x=0)=" + String.format("%.2f", y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth(), h = getHeight();
        g2.setColor(Color.GRAY);
        g2.drawLine(0, h/2, w, h/2);
        g2.drawLine(w/2, 0, w/2, h);
        g2.drawLine(w, h/2, w - 5, h/2 - 5);
        g2.drawLine(w, h/2, w - 5, h/2 + 5);
        g2.drawLine(w/2, 0, w/2 - 5, 5);
        g2.drawLine(w/2, 0, w/2 + 5, 5);
        g2.drawString("x", w - 15, h/2 + 15);
        g2.drawString("y", w/2 + 5, 15);
        g2.drawString("0", w/2 - 15, h/2 + 15);

        for (Map.Entry<CurveData, Double> e : curveOffsets.entrySet()) {
            CurveData c = e.getKey(); double off = e.getValue();
            g2.setColor(c.getColor());
            for (int x = 0; x < w; x++) {
                double realX1 = (x + off) * 0.05;
                double realX2 = (x + 1 + off) * 0.05;
                int y1 = (int) (h/2 - c.getY(realX1) * 50);
                int y2 = (int) (h/2 - c.getY(realX2) * 50);
                g2.drawLine(x, y1, x + 1, y2);
            }
        }
    }
}
