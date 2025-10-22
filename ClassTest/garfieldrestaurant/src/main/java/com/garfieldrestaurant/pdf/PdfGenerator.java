package com.garfieldrestaurant.pdf;

import com.garfieldrestaurant.model.Order;
import java.io.IOException;

/**
 * PDF 报告生成器接口 (Strategy Pattern)。
 */
public interface PdfGenerator {
    String getName();
    /**
     * 生成订单报告 PDF 文件。
     * @param order 订单数据。
     * @param outputPath 文件输出路径。
     * @throws IOException 如果 PDF 生成失败。
     */
    void generateReport(Order order, String outputPath) throws IOException;
}