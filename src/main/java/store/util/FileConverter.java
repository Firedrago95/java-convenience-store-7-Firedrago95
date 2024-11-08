package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;

public class FileConverter {

    private List<Product> products = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();

    public void readPromotionsFile() {
        String filePath = "src/main/resources/promotions.md";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            makePromotions(br);
        } catch (IOException e) {
            System.err.println("[ERROR] 파일을 읽는 중 오류 발생");
        }
    }

    private void makePromotions(BufferedReader br) throws IOException {
        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            convertToPromotion(line);
        }
    }

    private void convertToPromotion(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            createPromotion(parts);
        }
    }

    private void createPromotion(String[] parts) {
        String name = parts[0].trim();
        int buy = Integer.parseInt(parts[1].trim());
        int get = Integer.parseInt(parts[2].trim());
        LocalDateTime startDate = parseDate(parts[3].trim());
        LocalDateTime endDate = parseDate(parts[4].trim());
        promotions.add(new Promotion(name, buy, get, startDate, endDate));
    }

    private LocalDateTime parseDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localDate.atStartOfDay();
    }

    public List<Product> readProductsFile() {
        String filepath = "src/main/resources/products.md";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            return makePoints(br);
        } catch (IOException e) {
            System.err.println("[ERROR] 파일을 읽는 중 오류 발생");
        }
        return null;
    }

    private List<Product> makePoints(BufferedReader br) throws IOException {
        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            convertToPoints(line);
        }
        return products;
    }

    private void convertToPoints(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            createPoint(parts);
        }
    }

    private void createPoint(String[] parts) {
        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        Promotion promotion = null;
        if (!parts[3].trim().equals("null")) {
            promotion = findPromotion(parts);
        }
        products.add(new Product(name, price, quantity, promotion));
    }

    private Promotion findPromotion(String[] parts) {
        String PromotionName = parts[3].trim();
        Promotion promotion = promotions.stream()
            .filter(promotion1 -> promotion1.getName().equals(PromotionName))
            .findFirst()
            .get();
        return promotion;
    }
}
