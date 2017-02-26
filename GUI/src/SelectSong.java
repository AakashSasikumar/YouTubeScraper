import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Aakash on 12/15/2016.
 */
public class SelectSong extends JPanel {
    private JLabel selectSong;
    static  JTable selectionTable;
    private JPanel temp;

    SelectSong(){
        setLayout(new BorderLayout());
        selectSong = new JLabel("Choose Song To Download:  ");
        temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(selectSong);
        selectionTable = new JTable();
        selectionTable.setPreferredScrollableViewportSize(selectionTable.getPreferredSize());
        selectionTable.setFillsViewportHeight(true);
        selectionTable.setRowHeight(21);
        //selectionTable.setEnabled(false);
        add(temp, BorderLayout.NORTH);
        add(new JScrollPane(selectionTable), BorderLayout.CENTER);


    }
    static void populateTable(ArrayList<String> title){
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Select From Here", title.toArray());
        selectionTable.setModel(dtm);

    }

}
