// MainFrame.java
// 主窗口，组合画布、状态面板和控制器
// ---------------------------------------------
package multicurve;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final CurveCanvas canvas;
    private final JPanel controlPanel;

    public MainFrame() {
        setTitle("多曲线动态展示系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // 状态面板：显示四种曲线在 x=w/2 的 y 值
        JPanel statusPanel = new JPanel(new GridLayout(1, 4));
        Map<String, JLabel> statusLabels = new LinkedHashMap<>();
        for (String name : new String[]{"sin(x)", "cos(x)", "x", "x^2"}) {
            JLabel lbl = new JLabel(name + "(x=0)=0.00", SwingConstants.CENTER);
            statusLabels.put(name, lbl);
            statusPanel.add(lbl);
        }
        add(statusPanel, BorderLayout.NORTH);

        // 绘图画布，传入状态标签
        canvas = new CurveCanvas(statusLabels);
        add(canvas, BorderLayout.CENTER);

        // 控制面板
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(controlPanel);
        scroll.setPreferredSize(new Dimension(200, 0));
        add(scroll, BorderLayout.EAST);

        JButton addBtn = new JButton("添加控制面板");
        addBtn.addActionListener(e -> {
            CurveController controller = new CurveController(canvas);
            controlPanel.add(controller);
            controlPanel.revalidate();
        });
        add(addBtn, BorderLayout.SOUTH);
    }
}
