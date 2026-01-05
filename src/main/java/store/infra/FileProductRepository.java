package store.infra;

import store.common.error.ErrorMessage;
import store.common.utils.CSVParser;
import store.common.utils.TypeConverter;
import store.domain.Product;
import store.domain.ProductRepository;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.io.FileReader;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class FileProductRepository implements ProductRepository {
    private final FileReader fileReader = new FileReader();
    private static final Path productPath = Path.of("src/main/resources/products.md");
    private static final Path promotionPath = Path.of("src/main/resources/promotions.md");

    @Override
    public List<Product> findAllProducts() {
        List<String> rawProductLine = fileReader.readAllLines(productPath);
        return mapToProduct(rawProductLine);
    }

    private List<Product> mapToProduct(final List<String> rawProductLine) {
        List<List<String>> rawProductPartsList = rawProductLine.stream()
                .map(CSVParser::split)
                .toList();

        // name,price,quantity,promotion
        List<Product> defaultProducts = collectDefaultProduct(rawProductPartsList);
        registerPromotionProductToDefaultProducts(rawProductPartsList, defaultProducts);
        List<Product> onlyPromotionProducts = getOnlyPromotionProducts(rawProductPartsList, defaultProducts);

        return Stream.of(defaultProducts, onlyPromotionProducts)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<Product> getOnlyPromotionProducts(final List<List<String>> rawProductPartsList, final List<Product> defaultProducts) {
        return rawProductPartsList.stream()
                .filter(rawProductParts -> !rawProductParts.get(3).equals("null"))
                .filter(rawProductParts -> !containsProduct(rawProductParts.getFirst(), defaultProducts))
                .map(rawProductParts -> Product.onlyPromotionProduct(
                                rawProductParts.getFirst(),
                                TypeConverter.toInteger(rawProductParts.get(1)),
                                new PromotionProduct(findPromotionByName(rawProductParts.get(3)), TypeConverter.toInteger(rawProductParts.get(2)))
                        )
                )
                .toList();
    }

    private List<Product> collectDefaultProduct(final List<List<String>> rawProductPartsList) {
        return rawProductPartsList.stream()
                .filter(rawProductPart -> rawProductPart.get(3).equals("null"))
                .map(rawProductParts -> {
                    String productName = rawProductParts.getFirst();
                    int price = TypeConverter.toInteger(rawProductParts.get(1));
                    int stock = TypeConverter.toInteger(rawProductParts.get(2));
                    String promotionName = rawProductParts.get(3);
                    return createProduct(productName, price, stock, promotionName);
                })
                .toList();
    }

    private void registerPromotionProductToDefaultProducts(final List<List<String>> rawProductPartsList, final List<Product> defaultProducts) {
        rawProductPartsList.stream()
                .filter(rawProductPart -> !rawProductPart.get(3).equals("null"))
                .filter(rawProductPart -> containsProduct(rawProductPart.getFirst(), defaultProducts))
                .forEach(rawProductParts -> {
                    Promotion promotion = findPromotionByName(rawProductParts.get(3));
                    Product product = findProductByName(rawProductParts.getFirst(), defaultProducts);
                    product.registerPromotionProduct(new PromotionProduct(promotion, TypeConverter.toInteger(rawProductParts.get(2))));
                });
    }

    private Product findProductByName(String name, List<Product> products) {
        return products.stream()
                .filter(product -> product.isSameName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(ErrorMessage.NOT_EXISTS_PRODUCT.message()));
    }

    private boolean containsProduct(String name, List<Product> products) {
        return products.stream()
                .filter(product -> product.isSameName(name))
                .count() == 1;
    }

    private Product createProduct(final String productName, final int price, final int stock, final String promotionName) {
        if (promotionName.equals("null")) {
            return Product.defaultProduct(productName, price, stock);
        }
        Promotion promotion = findPromotionByName(promotionName);
        return new Product(productName, price, 0, new PromotionProduct(promotion, stock));
    }

    private Promotion findPromotionByName(String promotionName) {
        List<Promotion> promotions = findPromotions();
        return promotions.stream()
                .filter(promotion -> promotion.getPromotionName().equals(promotionName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(ErrorMessage.INVALID_PRODUCT_PROMOTION.message()));
    }

    private List<Promotion> findPromotions() {
        List<String> rawPromotionLine = fileReader.readAllLines(promotionPath);
        return mapToPromotion(rawPromotionLine);
    }

    private List<Promotion> mapToPromotion(List<String> rawPromotionLine) {
        List<List<String>> promotionPartsList = rawPromotionLine.stream()
                .map(CSVParser::split)
                .toList();

        return promotionPartsList.stream()
                .map(promotionParts -> {
                    // name,buy,get,start_date,end_date
                    String name = promotionParts.getFirst();
                    int buy = TypeConverter.toInteger(promotionParts.get(1));
                    int get = TypeConverter.toInteger(promotionParts.get(2));
                    LocalDate startDate = TypeConverter.toLocalDate(promotionParts.get(3));
                    LocalDate endDate = TypeConverter.toLocalDate(promotionParts.get(4));
                    return new Promotion(name, buy, get, startDate, endDate);
                })
                .toList();
    }
}
