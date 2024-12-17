package VentanaEmergente.Maquinados;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CustomDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            // Permite solo caracteres numéricos, puntos y 'x'
            if (string != null && string.matches("[0-9.x]*")) {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                // Verifica que no haya más de 4 dimensiones
                if (currentText.split("x").length <= 4) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            // Permite solo caracteres numéricos, puntos y 'x'
            if (text != null && text.matches("[0-9.x]*")) {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                // Verifica que no haya más de 4 dimensiones
                if (currentText.split("x").length <= 4) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
