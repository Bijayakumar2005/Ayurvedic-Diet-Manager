package com.ayurdiet.service;

import com.ayurdiet.model.DietPlan;
import com.ayurdiet.model.Meal;
import com.ayurdiet.model.FoodItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private DietPlanService dietPlanService;

    public byte[] generateDietPlanPdf(DietPlan dietPlan) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        
        document.open();
        
        // Add title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph("Ayurvedic Diet Plan", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Add plan details
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        
        // Plan information table
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingBefore(10);
        infoTable.setSpacingAfter(20);
        
        addTableHeader(infoTable, "Plan Information", "Details", headerFont);
        addTableRow(infoTable, "Plan Name", dietPlan.getPlanName(), contentFont);
        addTableRow(infoTable, "Patient", dietPlan.getUser().getName(), contentFont);
        addTableRow(infoTable, "Duration", dietPlan.getDurationDays() + " days", contentFont);
        addTableRow(infoTable, "Start Date", dietPlan.getStartDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")), contentFont);
        addTableRow(infoTable, "End Date", dietPlan.getEndDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")), contentFont);
        addTableRow(infoTable, "Primary Dosha", dietPlan.getPrimaryDosha(), contentFont);
        addTableRow(infoTable, "Diet Type", dietPlan.getDietType(), contentFont);
        addTableRow(infoTable, "Health Goals", dietPlan.getHealthGoals(), contentFont);
        
        if (dietPlan.getFoodPreferences() != null && !dietPlan.getFoodPreferences().isEmpty()) {
            addTableRow(infoTable, "Food Preferences", dietPlan.getFoodPreferences(), contentFont);
        }
        
        if (dietPlan.getAllergies() != null && !dietPlan.getAllergies().isEmpty()) {
            addTableRow(infoTable, "Allergies", dietPlan.getAllergies(), contentFont);
        }
        
        document.add(infoTable);
        
        // Get meals from JSON
        List<Meal> meals = dietPlanService.getMealsFromDietPlan(dietPlan);
        
        // Add meals section
        if (meals != null && !meals.isEmpty()) {
            Paragraph mealsHeader = new Paragraph("Daily Meal Plan", headerFont);
            mealsHeader.setSpacingBefore(20);
            mealsHeader.setSpacingAfter(10);
            document.add(mealsHeader);
            
            for (Meal meal : meals) {
                // Meal header
                Font mealFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.DARK_GRAY);
                Paragraph mealTitle = new Paragraph(meal.getMealType() + " (" + meal.getTime() + ")", mealFont);
                mealTitle.setSpacingAfter(5);
                document.add(mealTitle);
                
                // Food items
                if (meal.getFoodItems() != null && !meal.getFoodItems().isEmpty()) {
                    PdfPTable mealTable = new PdfPTable(3);
                    mealTable.setWidthPercentage(100);
                    mealTable.setSpacingBefore(5);
                    mealTable.setSpacingAfter(10);
                    
                    // Table headers
                    addMealTableHeader(mealTable, "Food Item", "Quantity", "Preparation", headerFont);
                    
                    // Table rows
                    for (FoodItem food : meal.getFoodItems()) {
                        addMealTableRow(mealTable, food.getName(), 
                                       food.getQuantity() + " " + food.getUnit(), 
                                       food.getPreparation(), contentFont);
                    }
                    
                    document.add(mealTable);
                }
                
                // Instructions
                if (meal.getInstructions() != null && !meal.getInstructions().isEmpty()) {
                    Paragraph instructions = new Paragraph("Instructions: " + meal.getInstructions(), contentFont);
                    instructions.setSpacingAfter(15);
                    instructions.setIndentationLeft(10);
                    document.add(instructions);
                }
            }
        }
        
        // Add recommendations
        if (dietPlan.getRecommendations() != null && !dietPlan.getRecommendations().isEmpty()) {
            Paragraph recHeader = new Paragraph("Ayurvedic Recommendations", headerFont);
            recHeader.setSpacingBefore(20);
            recHeader.setSpacingAfter(10);
            document.add(recHeader);
            
            Paragraph recommendations = new Paragraph(dietPlan.getRecommendations(), contentFont);
            recommendations.setSpacingAfter(20);
            document.add(recommendations);
        }
        
        // Add footer
        Paragraph footer = new Paragraph("Generated on " + 
                java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) + 
                " by Ayurvedic Diet Manager", 
                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
        
        document.close();
        
        return outputStream.toByteArray();
    }
    
    private void addTableHeader(PdfPTable table, String header1, String header2, Font font) {
        PdfPCell cell1 = new PdfPCell(new Phrase(header1, font));
        PdfPCell cell2 = new PdfPCell(new Phrase(header2, font));
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setPadding(5);
        cell2.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
    }
    
    private void addTableRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, font));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, font));
        cell1.setPadding(5);
        cell2.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
    }
    
    private void addMealTableHeader(PdfPTable table, String header1, String header2, String header3, Font font) {
        PdfPCell cell1 = new PdfPCell(new Phrase(header1, font));
        PdfPCell cell2 = new PdfPCell(new Phrase(header2, font));
        PdfPCell cell3 = new PdfPCell(new Phrase(header3, font));
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setPadding(5);
        cell2.setPadding(5);
        cell3.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }
    
    private void addMealTableRow(PdfPTable table, String item, String quantity, String preparation, Font font) {
        PdfPCell cell1 = new PdfPCell(new Phrase(item, font));
        PdfPCell cell2 = new PdfPCell(new Phrase(quantity, font));
        PdfPCell cell3 = new PdfPCell(new Phrase(preparation, font));
        cell1.setPadding(5);
        cell2.setPadding(5);
        cell3.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }
}