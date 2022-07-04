package Utilidades;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.adempiere.utils.Miscfunc;

import Clases.DatosFichada;


public class generaXLS extends AbstractExcelRPT{


	String rutaArchivo = "";
	
	// Formatos de celda
	private WritableCellFormat formatTahoma8Underline;
	private WritableCellFormat formatTahoma8Bold;
	private WritableCellFormat formatTahoma9;
	private WritableCellFormat formatTahoma9Right;
	private WritableCellFormat formatTahoma9Left;
	
	int fila = 0;
	
	public generaXLS() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String doIt(List<DatosFichada> datosfichada) 
		throws Exception 
	{
		
		this.crearFormatosCelda();
		
		WritableWorkbook workbook = this.crearWorkbook();
		WritableSheet planillahoarios = this.crearPlanillaSueldos(workbook);
		this.crearCabeceraPlanilla(planillahoarios);
		
		try 
		{
			
			for(DatosFichada df : datosfichada)
				cargarDatos(planillahoarios,df);
			
			Miscfunc.Abrixls(rutaArchivo);
			
		}
		catch (Exception e) {
			e.printStackTrace();			
		}
		finally
		{
		    if (workbook != null) 
		    {
		    	workbook.write();
		    	workbook.close();
		    }
		}
		
		return "Termino OK!";
	}

	
	private void crearFormatosCelda() 
		throws WriteException 
	{
		WritableFont font8Bold = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.BOLD, false);
		formatTahoma8Bold = new WritableCellFormat(font8Bold);

		WritableFont arial8ptUnderline = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD, false,
														  UnderlineStyle.SINGLE);
		
		formatTahoma8Underline = new WritableCellFormat(arial8ptUnderline);
		formatTahoma8Underline.setBackground(Colour.GREY_25_PERCENT);
		formatTahoma8Underline.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		
		WritableFont font9 = new WritableFont(WritableFont.TAHOMA, 9);
		formatTahoma9 = new WritableCellFormat(font9);
		
		formatTahoma9Right = new WritableCellFormat(font9);
		formatTahoma9Right.setAlignment(Alignment.RIGHT);
		
		formatTahoma9Left = new WritableCellFormat(font9);
		formatTahoma9Left.setAlignment(Alignment.LEFT);	
		
	}
	
	
	private WritableWorkbook crearWorkbook()
		throws IOException
	{
		rutaArchivo = System.getProperty("user.home") + File.separator + "reportes" + File.separator + "Resumen_Datos_Basicos_" + Miscfunc.HoyAMD() + ".xls";
		return this.crearLibro(rutaArchivo);
	}
	
	private WritableSheet crearPlanillaSueldos(WritableWorkbook workbook)
	{
		String tituloPlanilla = "Resumen de Datos Basicos";
		return this.crearHoja(workbook, tituloPlanilla);
	}
	
	private void crearCabeceraPlanilla(WritableSheet sheet) 
		throws RowsExceededException, 
			   WriteException
	{
		Locale currentLocale = Language.getLoginLanguage().getLocale();
		NumberFormat formatCurrency = NumberFormat.getNumberInstance(currentLocale);
		formatCurrency.setMinimumFractionDigits(2);
		formatCurrency.setMaximumFractionDigits(2);
		
		int columna = 0;
		
		this.addLabel(sheet, 0, fila++, "Viviendas Roca - Planilla de Horarios", 9, formatTahoma8Bold);
		this.addLabel(sheet, 0, fila++, "Fecha Proceso: " + Miscfunc.HoyDMA(), 9, formatTahoma8Bold);
		
		this.addLabel(sheet, columna++, fila, "Nro. Documento", 30, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Hora", 30, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Minutos", 30, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Segundos", 10, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Nro. Maquina", 30, formatTahoma8Underline);		
	}
	
	
	private boolean cargarDatos(WritableSheet sheet,DatosFichada df) throws RowsExceededException, WriteException 
    {
		fila++;
		int columna = 0;

		// Nro. Documento
		this.addLabel(sheet, columna++, fila, String.valueOf(df.getNrodocumento()) , formatTahoma9Left);
		
		// Hora
		this.addLabel(sheet, columna++, fila, String.valueOf(df.getHora()), formatTahoma9Left);
		
		// Minutos
		this.addLabel(sheet, columna++, fila, String.valueOf(df.getMinutos()), formatTahoma9Left);

		// Segundos
		this.addLabel(sheet, columna++, fila, String.valueOf(df.getSegundos()), formatTahoma9);
		
		// Nro. Maquina.
		this.addLabel(sheet, columna++, fila, String.valueOf(df.getNromaquina()) , formatTahoma9Left);
					
		return true;
	}
		
}

