package VentanaEmergente.Ventas;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class ColorVentas extends JTable{
    @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        Component componente;
        componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        if (isRowSelected(rowIndex)) {
            componente.setBackground(Color.BLUE);
            componente.setForeground(Color.WHITE); // Cambiar texto a blanco para mejor contraste
        } else {
            try{
            }catch(Exception e){
                System.out.println("error: "+e);
            }
            String valor;
            if(getValueAt(rowIndex, 8) == null){
                valor = "";
            }else{
                valor = getValueAt(rowIndex, 8).toString();
            }
            if(valor.getClass().equals(String.class)){

                switch (valor) {
                    case "DETENIDO":
                        componente.setBackground(Color.decode("#c00000"));
                        componente.setForeground(Color.black);
                        break;
                    case "EN PROCESO":
                        componente.setBackground(Color.decode("#ffea00"));
                        componente.setForeground(Color.black);
                        break;
                    case "CERRADO":
                        componente.setBackground(Color.decode("#15df00"));
                        componente.setForeground(Color.black);
                        break;
                    case "REPROSESAMIENTO":
                        componente.setBackground(Color.decode("#00A4E6"));
                        componente.setForeground(Color.black);
                        break;
                    default:
                        componente.setBackground(null);
                        componente.setForeground(null);
                        break; 
                }
            }
        }
        return componente;
    }
}
