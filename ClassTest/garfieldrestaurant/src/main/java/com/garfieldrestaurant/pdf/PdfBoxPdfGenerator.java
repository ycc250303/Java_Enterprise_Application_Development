package com.garfieldrestaurant.pdf;

import com.garfieldrestaurant.model.food.Food;
import com.garfieldrestaurant.model.Order;
import com.garfieldrestaurant.model.OrderItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Comparator;

/**
 * 使用 Apache PDFBox 库生成 PDF 报告的实现 (Task 4)。
 */
public class PdfBoxPdfGenerator implements PdfGenerator {

    @Override
    public String getName() {
        return "Apache PDFBox";
    }

    @Override
    public void generateReport(Order order, String outputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // 添加标题
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Garfield Restaurant Order Report");
                contentStream.endText();

                // 添加订单详情
                addOrderDetailsToDocument(contentStream, order, document, page);
            }

            // 保存文档
            document.save(outputPath);
            System.out.println(">>> [Apache PDFBox]: PDF Report saved to: " + outputPath);
        }
    }

    private void addOrderDetailsToDocument(PDPageContentStream contentStream, Order order,
                                     PDDocument document, PDPage page) throws IOException {
    float yPosition = 700;

    // 添加订单详情标题
    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
    contentStream.newLineAtOffset(50, yPosition);
    contentStream.showText("--- Order Details ---");
    contentStream.endText();
    yPosition -= 20;

    // 添加食物项列表
    for (OrderItem item : order.getItems().stream()
            .sorted(Comparator.comparing(i -> i.getFood().getName()))
            .toList()) {

        // 检查是否需要新页面
        if (yPosition < 50) {
            contentStream.endText(); // 结束当前文本操作
            contentStream.close();

            // 创建新页面
            page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            yPosition = 750;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(70, yPosition);

        String itemText = String.format("- %s (x%d) | Price: $%.2f | Calories: %d kcal",
                item.getFood().getName(),
                item.getQuantity(),
                item.getSubTotal(),
                item.getTotalCalories());

        contentStream.showText(itemText);
        contentStream.endText();
        yPosition -= 15;
    }

    // 添加折扣详情标题
    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
    contentStream.newLineAtOffset(50, yPosition);
    contentStream.showText("--- Discount Details (b) ---");
    contentStream.endText();
    yPosition -= 20;

    // 添加折扣详情
    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA, 12);
    contentStream.newLineAtOffset(70, yPosition);
    contentStream.showText(String.format("- Name: %s", order.getAppliedDiscount().getName()));
    contentStream.endText();
    yPosition -= 15;

    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.newLineAtOffset(70, yPosition);
    contentStream.showText(String.format("- Description: %s", order.getAppliedDiscount().getDescription()));
    contentStream.endText();
    yPosition -= 20;

    // 添加汇总信息标题
    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
    contentStream.newLineAtOffset(50, yPosition);
    contentStream.showText("--- Summary Information (c) ---");
    contentStream.endText();
    yPosition -= 20;

    // 添加汇总信息
    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA, 12);
    contentStream.newLineAtOffset(70, yPosition);
    contentStream.showText(String.format("- Subtotal: $%.2f", order.getSubTotal()));
    contentStream.endText();
    yPosition -= 15;

    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.newLineAtOffset(70, yPosition);
    contentStream.showText(String.format("- Final Price: $%.2f", order.getFinalPrice()));
    contentStream.endText();
    yPosition -= 15;

    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.newLineAtOffset(70, yPosition);
    contentStream.showText(String.format("- Total Calories: %d kcal", order.getTotalCalories()));
    contentStream.endText();
    yPosition -= 20;

    // 添加食物详细属性标题
    if (yPosition < 50) {
        contentStream.endText();
        contentStream.close();
        page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        yPosition = 750;
    }

    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
    contentStream.newLineAtOffset(50, yPosition);
    contentStream.showText("--- Food Detailed Attributes (d) ---");
    contentStream.endText();
    yPosition -= 20;

    // 添加食物详细属性
    for (Food foodItem : order.getItems().stream()
            .map(OrderItem::getFood)
            .distinct()
            .toList()) {

        // 检查是否需要新页面
        if (yPosition < 50) {
            contentStream.endText();
            contentStream.close();
            page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            yPosition = 750;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(70, yPosition);
        String attributeText = String.format("- [%s]: %s",
                foodItem.getName(),
                foodItem.getUniqueAttributesDisplay());
        contentStream.showText(attributeText);
        contentStream.endText();
        yPosition -= 15;
    }

    // 最后确保结束文本操作
    contentStream.endText();
}

}
