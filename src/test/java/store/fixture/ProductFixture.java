package store.fixture;

import store.domain.product.Product;

import java.util.List;

import static store.fixture.PromotionFixture.*;

public class ProductFixture {

    private ProductFixture(){
    }

    public static final List<String> PRODUCTS_LIST = List.of(
            "- 콜라 1,000원 10개 탄산2+1",
            "- 콜라 1,000원 10개",
            "- 사이다 1,000원 8개 탄산2+1",
            "- 사이다 1,000원 7개",
            "- 오렌지주스 1,800원 9개 MD추천상품",
            "- 오렌지주스 1,800원 재고 없음",
            "- 탄산수 1,200원 5개 탄산2+1",
            "- 탄산수 1,200원 재고 없음",
            "- 물 500원 10개",
            "- 비타민워터 1,500원 6개",
            "- 감자칩 1,500원 5개 반짝할인",
            "- 감자칩 1,500원 5개",
            "- 초코바 1,200원 5개 MD추천상품",
            "- 초코바 1,200원 5개",
            "- 에너지바 2,000원 5개",
            "- 정식도시락 6,400원 8개",
            "- 컵라면 1,700원 1개 MD추천상품",
            "- 컵라면 1,700원 10개"
    );

    public static final Product 콜라 = new Product("콜라", 1000, 10, 탄산투플원, 10);
    public static final Product 사이다 = new Product("사이다", 1000, 7, 탄산투플원, 8);
    public static final Product 오렌지주스 = new Product("오렌지주스", 1800, 0, MD추천상품, 9);
    public static final Product 탄산수 = new Product("탄산수", 1200, 0, 탄산투플원, 5);
    public static final Product 물 = new Product("물", 500, 10, null, 0);
    public static final Product 비타민워터 = new Product("비타민워터", 1500, 6, null, 0);
    public static final Product 감자칩 = new Product("감자칩", 1500, 5, 반짝할인, 5);
    public static final Product 초코바 = new Product("초코바", 1200, 5, MD추천상품, 5);
    public static final Product 에너지바 = new Product("에너지바", 2000, 5, null, 0);
    public static final Product 정식도시락 = new Product("정식도시락", 6400, 8, null, 0);
    public static final Product 컵라면 = new Product("컵라면", 1700, 10, MD추천상품, 1);

    public static final List<Product> 재고 = List.of(콜라, 사이다, 오렌지주스, 탄산수, 물, 비타민워터, 감자칩, 초코바, 에너지바, 정식도시락, 컵라면);
}
