package com.utils;

// 导入FileUtils类，用于文件操作
import org.apache.commons.io.FileUtils;
// 导入HSSFCell类，用于操作Excel单元格
import org.apache.poi.hssf.usermodel.HSSFCell;
// 导入HSSFRow类，用于操作Excel行
import org.apache.poi.hssf.usermodel.HSSFRow;
// 导入HSSFSheet类，用于操作Excel工作表
import org.apache.poi.hssf.usermodel.HSSFSheet;
// 导入HSSFWorkbook类，用于操作Excel工作簿
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// 导入Cell类，用于操作Excel单元格
import org.apache.poi.ss.usermodel.Cell;

// 导入File类，用于表示文件
import java.io.File;
// 导入FileOutputStream类，用于文件输出流
import java.io.FileOutputStream;
// 导入ArrayList类，用于存储动态数组
import java.util.ArrayList;
// 导入List接口，用于存储列表数据
import java.util.List;

/**
 * 文件导入导出工具类
 */
public class PoiUtil {

    /**
     * 导入Excel文件
     *
     * @param url Excel文件的路径
     * @return 包含Excel数据的二维列表
     * @throws Exception 如果读取文件时发生异常
     */
    public static List<List<String>> poiImport(String url) throws Exception {
        // 创建一个二维列表存储Excel数据
        List<List<String>> list = new ArrayList<>();
        // 创建Excel工作簿对象，读取文件内容
        HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(new File(url)));
        /**
         * 第一种方式读取Sheet页
         */
        // HSSFSheet sheet = workbook.getSheet("Sheet0");
        /**
         * 第二种方式读取Sheet页
         */
        // 获取第一个工作表
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 遍历每一行
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            // 获取当前行
            HSSFRow row = sheet.getRow(i);
            // 创建一个列表存储当前行的数据
            List<String> rowlist = new ArrayList<>();
            // 遍历当前行的每一个单元格
            for (int j = 0; j < row.getLastCellNum(); j++) {
                // 获取当前单元格
                HSSFCell cell = row.getCell(j);
                // 设置单元格类型为字符串
                cell.setCellType(Cell.CELL_TYPE_STRING);
                // 获取单元格的字符串值
                String value = cell.getStringCellValue();
                // 将单元格值添加到当前行的列表中
                rowlist.add(value);
            }
            // 将当前行的列表添加到二维列表中
            list.add(rowlist);
        }
        // 返回包含Excel数据的二维列表
        return list;
    }

    /**
     * 导出Excel文件
     *
     * @param list 包含数据的二维列表
     * @param url  导出文件的路径
     * @throws Exception 如果写入文件时发生异常
     */
    public static void poiExport(List<List<String>> list, String url) throws Exception {
        // 创建Excel工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet();
        // 遍历二维列表中的每一行
        for (int i = 0; i < list.size(); i++) {
            // 创建当前行
            HSSFRow row = sheet.createRow(i);
            // 获取当前行的数据列表
            List<String> dataList = list.get(i);
            // 遍历当前行的每一个数据
            for (int j = 0; j < dataList.size(); j++) {
                // 创建当前单元格
                HSSFCell cell = row.createCell(j);
                // 设置单元格的值
                cell.setCellValue(dataList.get(j));
            }
        }
        // 创建文件输出流
        FileOutputStream stream = FileUtils.openOutputStream(new File(url));
        // 将工作簿写入文件
        workbook.write(stream);
        // 关闭文件输出流
        stream.close();
    }

    public static void main(String[] args) {
        try {
            // 导入Excel文件
            List<List<String>> lists = PoiUtil.poiImport("C:/Users/Administrator/Desktop/工作1.xls");
            System.out.println(lists);

            // 导出Excel文件
            PoiUtil.poiExport(lists, "C:/Users/Administrator/Desktop/工作1.xls");

            // 示例数据
//            List<List<String>> list = new ArrayList<>();
//            ArrayList<String> dataList = new ArrayList<>();
//            dataList.add("标题1");
//            dataList.add("标题2");
//            dataList.add("标题3");
//            list.add(dataList);
//            // 追加数据
//            for (int i = 1; i < 10; i++) {// 这里的int 起始是1 也就是第二行开始
//                ArrayList<String> dataList111 = new ArrayList<>();
//                dataList111.add("内容" + i);
//                dataList111.add("内容1111111121222222222333333333377777777411111111477777777" + i);
//                dataList111.add("内容" + i);
//                list.add(dataList111);
//            }
//            PoiUtil.poiExport(list, "C:/Users/Administrator/Desktop/工作1.xls");
        } catch (Exception e) {
            // 捕获并打印异常信息
            e.printStackTrace();
        }
    }
}
