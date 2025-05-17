// CurveThread.java
// 负责曲线数据更新与重绘调用
// ---------------------------------------------
package multicurve;

import javax.swing.SwingUtilities;

public class CurveThread extends Thread {
    private final CurveData curve;
    private final CurveCanvas canvas;
    private volatile boolean running = true;
    private double offset = 0;

    public CurveThread(CurveData curve, CurveCanvas canvas) {
        this.curve = curve;
        this.canvas = canvas;
    }

    @Override
    public void run() {
        int w = canvas.getWidth();
        offset = -w / 2.0;
        while (running) {
            offset += 0.1;
            canvas.updateCurve(curve, offset);
            SwingUtilities.invokeLater(canvas::repaint);
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}