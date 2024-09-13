package VentanaEmergente.Costos;

import javax.swing.JOptionPane;

public class Fechas{
    FF fecha;
    FF Enero2023 = new FF("2023-01-02","2023-01-29",4,1);
    FF Febrero2023 = new FF("2023-01-30","2023-02-26",4,5);
    FF Marzo2023 = new FF("2023-02-27","2023-04-02",5,9);
    FF Abril2023 = new FF("2023-04-03","2023-04-30",4,14);
    FF Mayo2023 = new FF("2023-05-01","2023-05-28",4,18);
    FF Junio2023 = new FF("2023-05-29","2023-07-02",5,22);
    FF Julio2023 = new FF("2023-07-03","2023-07-30",4,27);
    FF Agosto2023 = new FF("2023-07-31","2023-08-27",4,31);
    FF Septiembre2023 = new FF("2023-08-28","2023-10-01",5,35);
    FF Octubre2023 = new FF("2023-10-02","2023-10-29",4,40);
    FF Noviembre2023 = new FF("2023-10-30","2023-11-26",4,44);
    FF Diciembre2023 = new FF("2023-11-27","2023-12-31",5,48);
    
    FF Enero2024 = new FF("2024-01-01","2024-01-28",4,1);
    FF Febrero2024 = new FF("2024-01-29","2024-02-25",4,5);
    FF Marzo2024 = new FF("2024-02-26","2024-03-31",5,9);
    FF Abril2024 = new FF("2024-04-01","2024-04-28",4,14);
    FF Mayo2024 = new FF("2024-04-29","2024-06-02",5,18);
    FF Junio2024 = new FF("2024-06-03","2024-06-30",4,23);
    FF Julio2024 = new FF("2024-07-01","2024-08-04",5,27);
    FF Agosto2024 = new FF("2024-08-05","2024-09-01",4,32);
    FF Septiembre2024 = new FF("2024-09-02","2024-09-29",4,36);
    FF Octubre2024 = new FF("2024-09-30","2024-10-27",4,40);
    FF Noviembre2024 = new FF("2024-10-28","2024-11-01",5,44);
    FF Diciembre2024 = new FF("2024-11-02","2024-12-29",4,49);
    
    public Fechas(int mes, int año){
        if(mes == 1 && año == 2023){
            fecha = Enero2023;
        }else if(mes == 2 && año == 2023){
            fecha = Febrero2023;
        }else if(mes == 3 && año == 2023){
            fecha = Marzo2023;
        }else if(mes == 4 && año == 2023){
            fecha = Abril2023;
        }else if(mes == 5 && año == 2023){
            fecha = Mayo2023;
        }else if(mes == 6 && año == 2023){
            fecha = Junio2023;
        }else if(mes == 7 && año == 2023){
            fecha = Julio2023;
        }else if(mes == 8 && año == 2023){
            fecha = Agosto2023;
        }else if(mes == 9 && año == 2023){
            fecha = Septiembre2023;
        }else if(mes == 10 && año == 2023){
            fecha = Octubre2023;
        }else if(mes == 11 && año == 2023){
            fecha = Noviembre2023;
        }else if(mes == 12 && año == 2023){
            fecha = Diciembre2023;
        }else if(mes == 1 && año == 2024){
            fecha = Enero2024;
        }else if(mes == 2 && año == 2024){
            fecha = Febrero2024;
        }else if(mes == 3 && año == 2024){
            fecha = Marzo2024;
        }else if(mes == 4 && año == 2024){
            fecha = Abril2024;
        }else if(mes == 5 && año == 2024){
            fecha = Mayo2024;
        }else if(mes == 6 && año == 2024){
            fecha = Junio2024;
        }else if(mes == 7 && año == 2024){
            fecha = Julio2024;
        }else if(mes == 8 && año == 2024){
            fecha = Agosto2024;
        }else if(mes == 9 && año == 2024){
            fecha = Septiembre2024;
        }else if(mes == 10 && año == 2024){
            fecha = Octubre2024;
        }else if(mes == 11 && año == 2024){
            fecha = Noviembre2024;
        }else if(mes == 12 && año == 2024){
            fecha = Diciembre2024;
        }else{
            JOptionPane.showMessageDialog(null, "No hay registro de esta fecha","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getFechaInicio(){
        return fecha.getFechaInicio();
    }
    
    public String getFechaFinal(){
        return fecha.getFechaFinal();
    }
    
    public int getNumeroSemanas(){
        return fecha.getSemanas();
    }
    
    public int getEmpiesa(){
        return fecha.getEmpieza();
    }
    
}
