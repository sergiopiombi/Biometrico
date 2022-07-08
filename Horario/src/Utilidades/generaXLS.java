package Utilidades;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.BooleanCell.*;


import Clases.DatosFichada;


public class generaXLS extends AbstractExcelRPT{


	private static final String FILEPATH = System.getProperty("user.home") + File.separator + "reportes" + File.separator + "PlanillaDeHorarios_";
	
	// Formatos de celda
	private WritableCellFormat formatTahoma8Underline;
	private WritableCellFormat formatTahoma8Bold;
	private WritableCellFormat formatTahoma9;
	private WritableCellFormat formatTahoma9Right;
	private WritableCellFormat formatTahoma9Left;
	private WritableCellFormat cellDateFormat;
	
	private String FileName = "";
	
	
	public String getFileName() {
		return this.FileName;
	}
	
	public void setFileName(String value) {
		this.FileName = value;
	}
		
			
	int fila = 0;
		
	public generaXLS() {
		super();
		setFileName(System.currentTimeMillis() + ".xls");		
	}
	
	
	public String doIt(List<DatosFichada> datosfichada,String namePlanilla) 
		throws Exception 
	{
		
		this.crearFormatosCelda();
		
		WritableWorkbook workbook = this.crearWorkbook();
		WritableSheet planillahorarios = this.crearPlanillaHorarios(workbook);
		this.crearCabeceraPlanilla(planillahorarios,namePlanilla);
		
		try 
		{
			
			for(DatosFichada df : datosfichada)
				cargarDatos(planillahorarios,df);
			
			Abrixls(FILEPATH + getFileName());
			
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
	
	public static void Abrixls(String path)
	{
		try
		{
		   Runtime.getRuntime().exec ("rundll32 SHELL32.DLL,ShellExec_RunDLL " + path);		   
		}
		catch (Exception e)
		{
		   System.out.println("Error al abrir el archivo " + path + "\n" + e.getMessage());
		}
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
		
		DateFormat customDateFormat = new DateFormat("dd-mm-yyyy");
		cellDateFormat = new WritableCellFormat(customDateFormat);
		
	}
	
	
	private WritableWorkbook crearWorkbook()
		throws IOException
	{
		return this.crearLibro(FILEPATH + getFileName());
	}
	
	private WritableSheet crearPlanillaHorarios(WritableWorkbook workbook)
	{
		String tituloPlanilla = "Planilla de Fichada Diaria";
		return this.crearHoja(workbook, tituloPlanilla);
	}
	
	private void crearCabeceraPlanilla(WritableSheet sheet,String planilla) 
		throws RowsExceededException, 
			   WriteException
	{
		Locale currentLocale = Language.getLoginLanguage().getLocale();
		NumberFormat formatCurrency = NumberFormat.getNumberInstance(currentLocale);
		formatCurrency.setMinimumFractionDigits(2);
		formatCurrency.setMaximumFractionDigits(2);
		
		int columna = 0;
		
		this.addLabel(sheet, 0, fila++, "Viviendas Roca - Planilla de Horarios - " + planilla, 9, formatTahoma8Bold);
		this.addLabel(sheet, 0, fila++, "Fecha Proceso: " + FechaDMA(), 9, formatTahoma8Bold);
		
		this.addLabel(sheet, columna++, fila, "Nro. Documento", 30, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Fecha", 14, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Hora", 30, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Minutos", 30, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Segundos", 10, formatTahoma8Underline);
		this.addLabel(sheet, columna++, fila, "Nro. Maquina", 30, formatTahoma8Underline);		
	}
	
	public static String FechaDMA() {
		Date fecha = new Date();
		System.out.println("fecha:" + String.valueOf(fecha));
		if (!String.valueOf(fecha).equals("null")) {
			SimpleDateFormat sqlfmt = new SimpleDateFormat("dd-MM-yyyy");
			return sqlfmt.format(fecha);
		} else
			return "    -  -  ";
	}
	
	
	private boolean cargarDatos(WritableSheet sheet,DatosFichada df) throws RowsExceededException, WriteException 
    {
		fila++;
		int columna = 0;

		// Nro. Documento
		this.addLabel(sheet, columna++, fila, String.valueOf(df.getNrodocumento()) , formatTahoma9Left);
		
		// Fecha
		this.addDateTime(sheet, columna++, fila, df.getFecha(), cellDateFormat);
		
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

