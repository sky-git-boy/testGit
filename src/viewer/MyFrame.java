package viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

class AbstractEvent extends AbstractAction {
	private String name = null;
	private MyFrame frame = null;
	private MyService service = MyService.getInstance();

	AbstractEvent(String name, Icon icon, MyFrame frame) { // set icon and string name on button in toolbar
		super(name, icon);
		this.name = name;
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		// ����ȷ�϶Ի���
		// System.out.println(e.getActionCommand());
		if (e.getActionCommand() == "����ͼƬ") {
			service.openFile(this.frame);
		} else if (e.getActionCommand() == "��һ��") {
			service.lastImage(this.frame);
		} else if (e.getActionCommand() == "��һ��") {
			service.nextImage(this.frame);
		} else if (e.getActionCommand() == "�Ŵ�") {
			service.zoomBig(this.frame);
		} else if (e.getActionCommand() == "��С") {
			service.zoomSmall(this.frame);
		}
	}
}

public class MyFrame extends JFrame {
	private int width = 1050;
	private int height = 700;
	private int pX = 40;
	private int pY = 10;
	private JLabel label = new JLabel(); // use a label to show a picture
	private JPanel panel;
	private MyService service = MyService.getInstance(); // get the instance of service
	private ActionListener menuListener;

	public MyFrame() // constructor function
	{
		menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.funcOfImage(MyFrame.this, e.getActionCommand());
			}
		};
		initFrame();
	}

	void initFrame() {
		this.setTitle("ͼƬ�����");
		this.setLocation(pX, pY);
		this.setPreferredSize(new Dimension(width, height)); // set the frame wdith and height
		createMenuBar();
		createToolPanel();
		this.add(new JScrollPane(label), BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}

	void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		String menuArray[] = { "�ļ�", "����", "����" };
		String menuArrayItem[][] = { { "����ͼƬ", "�˳�" }, { "��һ��", "��һ��", "�Ŵ�", "��С" }, { "����" } };
		for (int i = 0; i < menuArray.length; ++i) {
			JMenu menu = new JMenu(menuArray[i]);
			for (int j = 0; j < menuArrayItem[i].length; ++j) {
				JMenuItem menuItem = new JMenuItem(menuArrayItem[i][j]);
				menuItem.addActionListener(menuListener);
				menu.add(menuItem);
			}
			menuBar.add(menu);
		}
		this.setJMenuBar(menuBar);
	}

	void createToolPanel() {
		panel = new JPanel();
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true); // ʵ�ֹ������Ĳ�����

		String toolImgArr[] = { "����ͼƬ", "��һ��", "��һ��", "�Ŵ�", "��С" };
		for (int i = 0; i < toolImgArr.length; ++i) {
			ImageIcon imageIcon = new ImageIcon("src/img/" + toolImgArr[i] + ".gif"); // set the absolutely path
			AbstractEvent absAction = new AbstractEvent(toolImgArr[i], imageIcon, this);
			JButton btnTool = new JButton(absAction);
			btnTool.setVerticalTextPosition(JButton.BOTTOM);
			btnTool.setHorizontalTextPosition(JButton.CENTER);
			toolBar.add(btnTool);
		}
		panel.add(toolBar);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT)); // set the layout flow-layout
		this.add(panel, BorderLayout.NORTH);
	}

	public JLabel getLabel() {
		return label;
	}
}