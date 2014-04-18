/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import trekbankDatabaseObjects.User;
import trekbankDatabaseObjects.Worker;

//import org.artofsolving.jodconverter.OfficeDocumentConverter;
//import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
//import org.artofsolving.jodconverter.office.OfficeManager;

/**
 *
 * @author Johnny
 */
public class Excel {
 
    public static WorkOrder readExcelFile(File file) throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(file);
        Sheet sheet = wb.getSheet(0);
        
        String kantoorGebruiker = sheet.getCell(0, 0).getContents().substring(19);
        String trekBankWerker = sheet.getCell(0, 1).getContents().substring(21);
        
        String klant = sheet.getCell(0, 2).getContents().substring(7);
        String order = sheet.getCell(0, 3).getContents().substring(13);

        ArrayList<MeasureSubProfile> msps = new ArrayList<>();
        
        for (int i = 7; i < sheet.getRows(); i++) {
            //String meetNumer = sheet.getCell(0, i).getContents();
            String serie = sheet.getCell(1, i).getContents();
            String beschrijving = sheet.getCell(2, i).getContents();
            String proefLast = sheet.getCell(3, i).getContents();
            String certificaat = sheet.getCell(4, i).getContents();
            
            MeasureSubProfile msp = new MeasureSubProfile(Integer.parseInt(serie), beschrijving, certificaat, proefLast);
            msps.add(msp);
        }
        return new WorkOrder(klant, order, msps);
    }
    
    
    
    public static void createExcelFile(WorkOrder workOrder, User user, Worker worker, File file) throws IOException, WriteException{
        try {
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet("Bladzijde 0", 0);

            //<editor-fold defaultstate="collapsed" desc="Cell Formats">
            WritableCellFormat alignmentCentreFormat = new WritableCellFormat();
            alignmentCentreFormat.setAlignment(Alignment.CENTRE);
            

            WritableCellFormat horizontalBottomBorderCentreAlign = new WritableCellFormat();
            horizontalBottomBorderCentreAlign.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);
            horizontalBottomBorderCentreAlign.setAlignment(Alignment.CENTRE);
            
            
            WritableCellFormat verticalSideBorderCentreAlign = new WritableCellFormat();
            verticalSideBorderCentreAlign.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
            verticalSideBorderCentreAlign.setAlignment(Alignment.CENTRE);
            
            
            WritableCellFormat verticalSideHorizontalBottomBorderCentreAlign = new WritableCellFormat(horizontalBottomBorderCentreAlign);
            verticalSideHorizontalBottomBorderCentreAlign.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
            
            alignmentCentreFormat.setWrap(true);
            horizontalBottomBorderCentreAlign.setWrap(true);
            verticalSideBorderCentreAlign.setWrap(true);
            verticalSideHorizontalBottomBorderCentreAlign.setWrap(true);
            
            alignmentCentreFormat.setShrinkToFit(true);
            horizontalBottomBorderCentreAlign.setShrinkToFit(true);
            verticalSideBorderCentreAlign.setShrinkToFit(true);
            verticalSideHorizontalBottomBorderCentreAlign.setShrinkToFit(true);
            
            //</editor-fold>

            sheet.addCell(new Label(0, 0, "Kantoor gebruiker: " + user.getName()));

            sheet.addCell(new Label(0, 1, "Trekbank medewerker: " + worker.getName()));
            
            sheet.addCell(new Label(0, 2, "Klant: " + workOrder.getKlant()));
            sheet.addCell(new Label(0, 3, "Ordernummer: " + workOrder.getOrderNumber()));

            Timestamp today = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            SimpleDateFormat standardFormat = new SimpleDateFormat("dd-MM-yyyy");

            sheet.addCell(new Label(0, 4, "Datum: " + standardFormat.format(today)));

            Label[] labels = new Label[5];
            labels[0] = new Label(0, 6, "Meting nummer", verticalSideHorizontalBottomBorderCentreAlign);
            labels[1] = new Label(1, 6, "Aantal in serie", horizontalBottomBorderCentreAlign);
            labels[2] = new Label(2, 6, "Beschrijving", horizontalBottomBorderCentreAlign);
            labels[3] = new Label(3, 6, "Proef last", horizontalBottomBorderCentreAlign);
            labels[4] = new Label(4, 6, "Certificaat nummer", horizontalBottomBorderCentreAlign);


            for (int i = 0; i < labels.length; i++){  
                sheet.addCell(labels[i]);   
            }

            for (int i = 0; i < workOrder.getMeasureObjects().size(); i++){
                sheet.addCell(new jxl.write.Number(0, i + 7, i + 1, verticalSideBorderCentreAlign));
                sheet.addCell(new jxl.write.Number(1, i + 7, workOrder.getMeasureObjects().get(i).getAantal(), alignmentCentreFormat));
                sheet.addCell(new Label(2, i + 7, workOrder.getMeasureObjects().get(i).getBeschrijving(), alignmentCentreFormat)); 
                sheet.addCell(new Label(3, i + 7, workOrder.getMeasureObjects().get(i).getProefLast(), alignmentCentreFormat));
                sheet.addCell(new Label(4, i + 7, workOrder.getMeasureObjects().get(i).getCertificatNumber(), alignmentCentreFormat));
            }

            // Set auto size
            for (int i = 3; i < 5; i++) {
                CellView cell = sheet.getColumnView(i);
                cell.setAutosize(true);
                sheet.setColumnView(i, cell);    
            }
            
            
            sheet.setColumnView(0, 8);
            sheet.setColumnView(1, 7);
            sheet.setColumnView(2, 45);
            

            wb.write();
            wb.close();
            Runtime.getRuntime().exec("cmd /c start excel.exe");
            String[] commands = {"cmd", "/c", "start", "\"DummyTitle\"", file.getAbsolutePath()};
            Runtime.getRuntime().exec(commands);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "fout bij openen", "Insane warning", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
        
        
//                OfficeManager officeManager = new DefaultOfficeManagerConfiguration().buildOfficeManager();
//        
//                officeManager.start();
//                OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
                //converter.convert(file, out);
                //officeManager.stop();
        
                //Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + out.getAbsolutePath());

        
    }
    
    public static void createExcelFile(WorkOrder workOrder, String user, String worker, File file) throws IOException, WriteException{
        createExcelFile(workOrder, new User(0, user, ""), new Worker(0, worker), file);
    }
    
    public static void main(String... args) throws IOException, WriteException, BiffException {
        MeasureSubProfile msp = new MeasureSubProfile(10, "harpen 30 mm 12 mm sadfaasfasfsafsfafkljonsafjnsdjfasnb; u bdsweifuhbsiwba ", "#21312312", "100 ton");
        ArrayList<MeasureSubProfile> a = new ArrayList<>();
        a.add(msp);
        WorkOrder wo = new WorkOrder("Mouthaan & Interhijs b.v.", "#123123123", a);
        createExcelFile(wo, "Johnny Verhoeff", "Pietje janLul", new File("121313.xls"));
        wo = readExcelFile(new File("121313.xls"));
        System.out.println("workOrder = " + wo.toString());
    }
}


