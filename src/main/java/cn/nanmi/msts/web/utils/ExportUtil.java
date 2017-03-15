package cn.nanmi.msts.web.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

/**
 * Created by tizhou on 2016/12/12.
 */
public class ExportUtil {

    private XSSFWorkbook wb = null;

    private XSSFSheet sheet = null;

    public ExportUtil(XSSFWorkbook wb, XSSFSheet sheet) {
        this.wb = wb;
        this.sheet = sheet;
    }

    /**
     * 合并单元格后给合并后的单元格加边框
     * @param region
     * @param cs
     */
    public void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs){
        int toprowNum = region.getFirstRow();
        for (int i = toprowNum; i <= region.getLastRow(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                XSSFCell cell = row.getCell(j);
                cell.setCellStyle(cs);
            }
        }
    }

    /**
     * 設置表頭的單元格樣式
     * @return
     */
    public XSSFCellStyle getHeadStyle(){
        //創建單元格樣式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        //設置單元格的背景顏色為淡藍色
        cellStyle.setFillBackgroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        //設置單元格居中對齊
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //設置單元格垂直居中對齊
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //創建單元格內容顯示不下時自動換行
        cellStyle.setWrapText(true);
        //設置單元格字體樣式
        XSSFFont font = wb.createFont();
        //設置字體加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)200);
        cellStyle.setFont(font);
        //设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }

    public XSSFCellStyle getBodyStyle(){
        //創建單元格樣式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        //設置單元格居中對齊
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //設置單元格垂直居中對齊
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //創建單元格內容顯示不下時自動換行
        cellStyle.setWrapText(true);
        //設置單元格字體樣式
        XSSFFont font = wb.createFont();
        //設置字體加粗
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short)200);
        cellStyle.setFont(font);
        //设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }
}
