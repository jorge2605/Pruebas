package pruebas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorRojo extends JTable {
   
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        
            componente.setForeground(Color.black);
            return componente;
        }
        
    }
    
}
