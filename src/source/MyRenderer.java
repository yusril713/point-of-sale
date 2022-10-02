package source;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class MyRenderer extends DefaultTableCellRenderer 
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Color bg, fg;
   public MyRenderer(Color bg, Color fg) {
      super();
      this.bg = bg;
      this.fg = fg;
   }
   public Component getTableCellRendererComponent(JTable table, Object 
   value, boolean isSelected, boolean hasFocus, int row, int column) 
   {
      Component cell = super.getTableCellRendererComponent(table, value, 
      isSelected, hasFocus, row, column);
      cell.setBackground(bg);
      cell.setForeground(fg);
      return cell;
   }
}