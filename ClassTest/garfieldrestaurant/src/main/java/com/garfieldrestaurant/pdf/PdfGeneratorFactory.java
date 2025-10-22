package com.garfieldrestaurant.pdf;

import java.util.List;

/**
 * PDF 报告生成器工厂类。
 * 用于获取可用的 PDF Generator 实现，方便扩展。
 */
public class PdfGeneratorFactory {
    private static final List<PdfGenerator> AVAILABLE_GENERATORS = List.of(
            new ITextPdfGenerator(),
            new PdfBoxPdfGenerator()
    );

    private PdfGeneratorFactory() {}

    /**
     * 获取所有可用的 PDF 生成器。
     * @return PDF Generator 列表。
     */
    public static List<PdfGenerator> getAvailableGenerators() {
        return AVAILABLE_GENERATORS;
    }
}