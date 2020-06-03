package viewer;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyService {
	private static MyService service = null;
	private File currentFile = null;
	private File currentDirectory = null;
	private List<File> listCurrentFile = null;
	private JFileChooser fileChooser = new JFileChooser();
	private double largeRate = 0.3;

	public void openFile(MyFrame frame) {
		// �����ļ��ļ򵥹���
		FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "png", "gif");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.showOpenDialog(frame);
		fileChooser.addChoosableFileFilter(filter);

		this.currentFile = fileChooser.getSelectedFile();
		String fileImgName = this.currentFile.getPath(); // ����ͼƬ�ľ���·��
		// System.out.println(fileImgName);
		if (fileImgName.isEmpty())
			return;
		File fileDirectory = fileChooser.getCurrentDirectory();
		if (currentDirectory != fileDirectory) {
			File files[] = fileDirectory.listFiles();
			this.listCurrentFile = new ArrayList<File>();
			for (File file : files) {
				String str = file.getName();
				if (str.endsWith("png") || str.endsWith("jpg") || str.endsWith("gif")) {
					this.listCurrentFile.add(file);
					// System.out.println(file);
				}
			}
		}
		ImageIcon Img = new ImageIcon(fileImgName);
		frame.getLabel().setIcon(Img);

	}

	public void lastImage(MyFrame frame) {
		if (this.listCurrentFile != null && !this.listCurrentFile.isEmpty()) {
			int index = this.listCurrentFile.indexOf(this.currentFile);
			if (index > 0) {
				File file = (File) this.listCurrentFile.get(index - 1);
				ImageIcon Img = new ImageIcon(file.getPath());
				frame.getLabel().setIcon(Img);
				this.currentFile = file;
			} else {
				return;
			}
		} else {
			return;
		}
	}

	public void nextImage(MyFrame frame) {
		int lenOfList = listCurrentFile.size(); // get the size of list file
		if (this.listCurrentFile != null && !this.listCurrentFile.isEmpty()) {
			int index = this.listCurrentFile.indexOf(this.currentFile);
			if (index + 1 < lenOfList) {
				File file = (File) this.listCurrentFile.get(index + 1); // get file by index
				ImageIcon Img = new ImageIcon(file.getPath());
				frame.getLabel().setIcon(Img);
				this.currentFile = file;
			} else {
				return;
			}
		} else {
			return;
		}
	}

	public void zoomBig(MyFrame frame) {
		ImageIcon Img = (ImageIcon) frame.getLabel().getIcon();
		if (Img != null) {
			int width = (int) (Img.getIconWidth() * (1 + largeRate));
			ImageIcon newImg = new ImageIcon(Img.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
			frame.getLabel().setIcon(newImg);
		} else {
			return;
		}
	}

	public void zoomSmall(MyFrame frame) {
		ImageIcon Img = (ImageIcon) frame.getLabel().getIcon();
		// System.out.println("**************");
		if (Img != null) {
			int width = (int) (Img.getIconWidth() * (1 - largeRate));
			ImageIcon newImg = new ImageIcon(Img.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
			frame.getLabel().setIcon(newImg);
		} else {
			return;
		}
	}

	/*
	 * ��ȡ��ǰ�ľ��
	 */
	public static MyService getInstance() {
		if (service == null) {
			service = new MyService();
		}
		return service;
	}

	public void funcOfImage(MyFrame frame, String cmd) {
		if (cmd.equals("����ͼƬ")) {
			openFile(frame);
		} else if (cmd.equals("�˳�")) {
			System.exit(0);
		} else if (cmd.equals("��һ��")) {
			lastImage(frame);
		} else if (cmd.equals("��һ��")) {
			nextImage(frame);
		} else if (cmd.equals("�Ŵ�")) {
			zoomBig(frame);
		} else if (cmd.equals("��С")) {
			zoomSmall(frame);
		} else
			return;
	}
}
