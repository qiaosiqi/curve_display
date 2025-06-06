package another_method;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;
import java.util.function.DoubleUnaryOperator;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurveWindow::new);
    }
}

class CurveWindow extends JFrame {
    private final CurvePanel curvePanel;

    public CurveWindow() {
        setTitle("Multiple Curve Display System");
        setSize(1440, 810);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        curvePanel = new CurvePanel();
        add(curvePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton sinBtn = new JButton("sin(x)");
        JButton cosBtn = new JButton("cos(x)");
        JButton xBtn = new JButton("x");
        JButton xxBtn = new JButton("x²");

        sinBtn.addActionListener(e -> {
            curvePanel.addCurve("sin(x)", Math::sin, Color.BLACK);
            sinBtn.setEnabled(false);
        });
        cosBtn.addActionListener(e -> {
            curvePanel.addCurve("cos(x)", Math::cos, Color.GRAY);
            cosBtn.setEnabled(false);
        });
        xBtn.addActionListener(e -> {
            curvePanel.addCurve("x", x -> x, Color.CYAN.darker());
            xBtn.setEnabled(false);
        });
        xxBtn.addActionListener(e -> {
            curvePanel.addCurve("x²", x -> x * x, Color.RED.darker());
            xxBtn.setEnabled(false);
        });

        buttonPanel.add(sinBtn);
        buttonPanel.add(cosBtn);
        buttonPanel.add(xBtn);
        buttonPanel.add(xxBtn);

        JButton pauseBtn = new JButton("暂停");
        JButton resumeBtn = new JButton("继续");

        pauseBtn.addActionListener(e -> {
            curvePanel.pauseAllCurves();
            pauseBtn.setEnabled(false);
            resumeBtn.setEnabled(true);
        });

        resumeBtn.addActionListener(e -> {
            curvePanel.resumeAllCurves();
            resumeBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
        });

        buttonPanel.add(pauseBtn);
        buttonPanel.add(resumeBtn);

        JButton resetBtn = new JButton("重置");
        resetBtn.addActionListener(e -> {
            curvePanel.resetAllCurves();
            sinBtn.setEnabled(true);
            cosBtn.setEnabled(true);
            xBtn.setEnabled(true);
            xxBtn.setEnabled(true);
        });
        buttonPanel.add(resetBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

class CurveData {
    String name;
    DoubleUnaryOperator func;
    List<Double> yValues = new LinkedList<>();
    double x = 0;
    Color color;

    public CurveData(String name, DoubleUnaryOperator func, Color color) {
        this.name = name;
        this.func = func;
        this.color = color;
    }
}

class CurvePanel extends JPanel {
    private final List<CurveData> activeCurves = new ArrayList<>();
    private final int maxPoints = 1440;
    private final javax.swing.Timer repaintTimer;
    private boolean isPaused = false;
    private final List<javax.swing.Timer> curveTimers = new ArrayList<>();

    public CurvePanel() {
        setBackground(Color.WHITE);
        int delay = 8;
        repaintTimer = new javax.swing.Timer(delay, e -> repaint());
        repaintTimer.start();
    }

    public void addCurve(String name, DoubleUnaryOperator func, Color color) {
        CurveData curve = new CurveData(name, func, color);
        activeCurves.add(curve);
        int delay = 8;
        javax.swing.Timer curveTimer = new javax.swing.Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double y = curve.func.applyAsDouble(curve.x);
                curve.yValues.add(y);
                if (curve.yValues.size() > maxPoints) {
                    curve.yValues.remove(0);
                }
                curve.x += 0.01;

                if (curve.yValues.size() >= maxPoints) {
                    ((javax.swing.Timer) e.getSource()).stop();
                }
            }
        });
        curveTimer.start();
        curveTimers.add(curveTimer);
    }

    public void pauseAllCurves() {
        isPaused = true;
        for (javax.swing.Timer timer : curveTimers) {
            timer.stop();
        }
    }

    public void resumeAllCurves() {
        isPaused = false;
        for (javax.swing.Timer timer : curveTimers) {
            timer.start();
        }
    }

    public void resetAllCurves() {
        for (javax.swing.Timer timer : curveTimers) {
            timer.stop();
        }
        curveTimers.clear();
        activeCurves.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int totalHeight = getHeight();
        int drawBoardHeight = totalHeight - 80;

        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(Color.ORANGE);
        g2d.drawLine(0, drawBoardHeight / 2, width, drawBoardHeight / 2);
        g2d.drawLine(20, 0, 20, drawBoardHeight);
        g2d.drawString("x", getWidth() - 15, drawBoardHeight / 2 + 15);
        g2d.drawString("y", 10, 10);
        g2d.drawString("0", 10, drawBoardHeight / 2 + 15);

        for (CurveData curve : activeCurves) {
            List<Double> ys = curve.yValues;
            g2d.setColor(curve.color);
            g2d.setStroke(new BasicStroke(2.5f));
            for (int i = 1; i < ys.size(); i++) {
                int x1 = width - (ys.size() - i + 1);
                int x2 = width - (ys.size() - i);
                int y1 = (int) (drawBoardHeight / 2 - ys.get(i - 1) * 100);
                int y2 = (int) (drawBoardHeight / 2 - ys.get(i) * 100);
                g2d.drawLine(x1, y1, x2, y2);
            }
        }

        g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
        int line = 0;
        for (CurveData curve : activeCurves) {
            if (!curve.yValues.isEmpty()) {
                double lastY = curve.yValues.get(curve.yValues.size() - 1);
                g2d.setColor(curve.color);
                g2d.drawString(curve.name + ": y = " + String.format("%.4f", lastY),
                        getWidth() - 200, drawBoardHeight + 0 + line * 15);
                line++;
            }
        }
    }
}
