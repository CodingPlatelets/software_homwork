package cellmachine;

import javax.swing.JFrame;

import cell.Cell;
import field.Field;
import field.View;

public class CellMachine {

    public static void main(String[] args) {
        Field field = new Field(40, 20);
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                field.place(row, col, new Cell());
            }
        }
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.get(row, col);
                if (Math.random() < 0.1) {
                    cell.reborn();
                }
            }
        }

        //����
        View view = new View(field);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Cells");
        frame.add(view);
        frame.pack();
        frame.setVisible(true);

        for (int i = 0; i < 1000; i++) {
            boolean flag = false;
            for (int row = 0; row < field.getHeight(); row++) {
                for (int col = 0; col < field.getWidth(); col++) {
                    Cell cell = field.get(row, col);
                    Cell[] neighbour = field.getNeighbour(row, col);
                    int numOfLive = 0;
                    for (Cell c : neighbour) {
                        if (c.isAlive()) {
                            numOfLive++;
                        }
                    }
                    System.out.print("[" + row + "][" + col + "]:");
                    System.out.print(cell.isAlive() ? "live" : "dead");
                    System.out.print(":" + numOfLive + "-->");
                    if (cell.isAlive()) {
                        if (numOfLive < 2 || numOfLive > 3) {
                            flag = true;
                            cell.die();
                            System.out.print("die");
                        }
                    } else if (numOfLive == 3) {
                        flag = true;
                        cell.reborn();
                        System.out.print("reborn");
                    }
                    System.out.println();
                }
            }
            System.out.println("UPDATE");
            frame.repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!flag) {
                for (int row = 0; row < field.getHeight(); row++) {
                    for (int col = 0; col < field.getWidth(); col++) {
                        Cell cell = field.get(row, col);
                        if (Math.random() < 0.1) {
                            cell.reborn();
                        }
                    }
                }
            }
        }
    }

}
