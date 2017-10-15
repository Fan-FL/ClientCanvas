package client.file;

import client.UI.DrawArea;
import client.UI.WhiteBoardClient;
import client.shape.Shape;

import java.awt.Color;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;

public class FileHandler {
    private WhiteBoardClient whiteboard;
    DrawArea drawarea = null;

    public FileHandler(WhiteBoardClient wb, DrawArea da) {
        whiteboard = wb;
        drawarea = da;
    }
    /*
        save image
     */
    public void saveFile() {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = filechooser.showSaveDialog(whiteboard);
        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }

        File fileName = filechooser.getSelectedFile();
        fileName.canWrite();
        if (fileName == null || fileName.getName().equals("")){
            //If the file name does not exit
            JOptionPane.showMessageDialog(filechooser, "File name", "Please input file name锛�", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                fileName.delete();
                FileOutputStream fos = new FileOutputStream(fileName + ".cvs");
                //Output file in the form of bytes
                ObjectOutputStream output = new ObjectOutputStream(fos);
                //Shape record;
                output.writeInt(drawarea.shapeList.size());

                for (int i = 0; i < drawarea.shapeList.size(); i++) {
                    Shape p = drawarea.shapeList.get(i);
                    output.writeObject(p);
                    output.flush();
                }
                output.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
