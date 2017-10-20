package com.example.jisung.darimi;

        import org.apache.poi.hssf.usermodel.HSSFWorkbook;
        import org.apache.poi.hssf.util.HSSFColor;
        import org.apache.poi.ss.usermodel.Row;
        import org.apache.poi.ss.usermodel.Sheet;
        import org.apache.poi.ss.usermodel.Cell;
        import org.apache.poi.ss.usermodel.CellStyle;
        import org.apache.poi.ss.usermodel.Workbook;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.util.ArrayList;



public class Excel {

    public static boolean saveExcel(String path,String filename,ArrayList<Sales> salesList){

        boolean success = false;

        Workbook wb = new HSSFWorkbook();

        Cell c = null;

//        CellStyle cs = wb.createCellStyle();
//        cs.setFillForegroundColor(HSSFColor.LIME.index);

        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Order");

		/*
		 * Set header of row
		 */

        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("날짜");
      //  c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("이름");
        //c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("금액");
        //c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("지불방식");
        //c.setCellStyle(cs);

		/*
		 * Fill Content
		 */
        int i;
        for(i =1;i<=salesList.size();i++){
            Row item = sheet1.createRow(i);

            c = item.createCell(0);
            c.setCellValue(salesList.get(i-1).getDate());
          //  c.setCellStyle(cs);

            c = item.createCell(1);
            c.setCellValue(salesList.get(i-1).getName());
            //c.setCellStyle(cs);

            c = item.createCell(2);
            c.setCellValue(salesList.get(i-1).getSum());
            //c.setCellStyle(cs);

            c = item.createCell(3);
            int p = salesList.get(i-1).getPay();
            String pay = "";
            switch(p){
                case 1:
                    pay ="카드";
                    break;
                case 2:
                    pay= "카드";
                    break;
                case 3:
                    pay="현금";
                    break;
                case 4:
                    pay="현금";
                    break;
                default:
                    pay="현금";
                    break;
            }
            c.setCellValue(pay);

            //c.setCellStyle(cs);
        }


        sheet1.setColumnWidth(0, (15*500));
        sheet1.setColumnWidth(1, (15*500));
        sheet1.setColumnWidth(2, (15*500));
        sheet1.setColumnWidth(3, (15*500));

        File file = new File(path,filename);
        FileOutputStream os = null;

        try{
            os = new FileOutputStream(file);
            wb.write(os);
            success = true;
        }catch(IOException e){

        }catch(Exception e){

        }finally{
            try{
                if(null != os){
                    os.close();
                }
            }catch(Exception ex){

            }
        }


        return success;
    }
}

