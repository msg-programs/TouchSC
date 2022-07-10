// File is part of TouchSC (c) 2020 msg-programs, see LICENSE
package main;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private Point down = null;
	private Point up = null;
	private Point d = null;
	private static final int DELTA = 2;


	ArrayList<Point> pts = new ArrayList<>();

	boolean pass[] = new boolean[4];
	int last = -1;
	int count = 0;

	public Canvas() {
		super();
		this.setOpaque(false);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		System.out.printf("%s, %s, %s\n", this.getBackground().getRed() ,this.getBackground().getGreen() ,this.getBackground().getBlue() );
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = this.getWidth();
		int h = this.getHeight();

		g2d.setColor(Settings.bg);
		g2d.fillOval(0, 0, w, h);

		g2d.setColor(Settings.detail);
		g2d.fillOval(w / 2 - 2, h / 2 - 2, 4, 4);

		Polygon p = new Polygon();
		p.addPoint(w / 2, h / 2 - 10);
		p.addPoint(w / 2 - 2, 10);
		p.addPoint(w / 2 + 2, 10);
		g2d.fillPolygon(p);

		p.reset();
		p.addPoint(w / 2, h / 2 + 10);
		p.addPoint(w / 2 - 2, h - 10);
		p.addPoint(w / 2 + 2, h - 10);
		g2d.fillPolygon(p);

		p.reset();
		p.addPoint(10, h / 2 - 2);
		p.addPoint(10, h / 2 + 2);
		p.addPoint(w / 2 - 10, h / 2);
		g2d.fillPolygon(p);

		p.reset();
		p.addPoint(w - 10, h / 2 - 2);
		p.addPoint(w - 10, h / 2 + 2);
		p.addPoint(w / 2 + 10, h / 2);
		g2d.fillPolygon(p);

		g2d.setStroke(new BasicStroke(2));
		
		for (int i = 0; i < pts.size() - 1; i++) {
			Point f = pts.get(i);
			Point s = pts.get(i + 1);
			g2d.drawLine(f.x, f.y, s.x, s.y);
		}

		g2d.setColor(Settings.rim);
		g2d.setStroke(new BasicStroke(4));
		if (Shortcuts.tsc[Shortcuts.TOPLEFT].isHeld)
			g2d.draw(new Arc2D.Double(0, 0, w - 1, h - 1, 90, 90, Arc2D.OPEN));

		if (Shortcuts.tsc[Shortcuts.TOPRIGHT].isHeld)
			g2d.draw(new Arc2D.Double(0, 0, w - 1, h - 1, 0, 90, Arc2D.OPEN));

		if (Shortcuts.tsc[Shortcuts.BOTLEFT].isHeld)
			g2d.draw(new Arc2D.Double(0, 0, w - 1, h - 1, 180, 90, Arc2D.OPEN));

		if (Shortcuts.tsc[Shortcuts.BOTRIGHT].isHeld)
			g2d.draw(new Arc2D.Double(0, 0, w - 1, h - 1, 270, 90, Arc2D.OPEN));

	}

	@Override
	public void mousePressed(MouseEvent e) {
		up = null;
		down = null;
		d = null;
		count = 0;
		last = -1;
		resetPass();

		down = e.getPoint();
		pts.add(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		up = e.getPoint();
		pts.clear();
		try {
			int dxF = down.x - d.x;
			int dyF = down.y - d.y;

			int dxS = down.x - up.x;
			int dyS = down.y - up.y;

			int first = -1;

			if (Math.abs(dxF) >= Math.abs(dyF)) {
				if (dxF >= 0) {
					first = Shortcuts.LEFT;
				} else {
					first = Shortcuts.RIGHT;
				}
			} else {
				if (dyF >= 0) {
					first = Shortcuts.UP;
				} else {
					first = Shortcuts.DOWN;
				}
			}

			int second = -1;

			if (first <= 1) {
				if (dxS >= 0) {
					second = Shortcuts.LEFT;
				} else {
					second = Shortcuts.RIGHT;
				}
			} else {
				if (dyS >= 0) {
					second = Shortcuts.UP;
				} else {
					second = Shortcuts.DOWN;
				}
			}
			Shortcuts.doSC(first, second);
			repaint();
		} catch (Exception ex) {
			// drop it
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		count++;
		pts.add(e.getPoint());

		if (count == DELTA) {
			d = e.getPoint();
		}

		if (count > DELTA) {
			int w = this.getWidth();
			int h = this.getHeight();

			int x = e.getX();
			int y = e.getY();

			if (x < w / 2 && x >= 0 && y < h / 2 && y >= 0 && (last == 3 || last == -1)) {
				last = 0;
				pass[last] = true;
			} else if (x >= w / 2 && x <= w && y < h / 2 && y >= 0 && (last == 0 || last == -1)) {
				last = 1;
				pass[last] = true;
			} else if (x < w / 2 && x >= 0 && y >= h / 2 && y <= h && (last == 1 || last == -1)) {
				last = 2;
				pass[last] = true;
			} else if (x >= w / 2 && x <= w && y >= h / 2 && y <= h && (last == 2 || last == -1)) {
				last = 3;
				pass[last] = true;
			}

			if (last != -1 && pass[0] && pass[1] && pass[2] && pass[3]) {
				System.exit(0);
			}

		}
		repaint();
	}

	private void resetPass() {
		for (int i = 0; i < 4; i++) {
			pass[i] = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click");
		int pos = 0;

		if (up.x > this.getWidth() / 2) {
			if (up.y > this.getHeight() / 2) {
				pos = Shortcuts.BOTRIGHT;
			} else {
				pos = Shortcuts.TOPRIGHT;
			}
		} else {
			if (up.y > this.getHeight() / 2) {
				pos = Shortcuts.BOTLEFT;
			} else {
				pos = Shortcuts.TOPLEFT;
			}
		}
		Shortcuts.doTSC(pos);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
