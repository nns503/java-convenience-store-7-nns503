package store.integration;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.Application;
import store.fixture.ProductFixture;

import java.time.LocalDate;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreIntegrationTest extends NsTest {

    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    ProductFixture.PRODUCTS_LIST
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 여러_개의_일반_상품_두번_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "Y", "[물-1]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "내실돈18,300",
                    "비타민워터1,500원3개",
                    "물500원8개",
                    "정식도시락6,400원6개",
                    "내실돈500");
        });
    }

    @Test
    void 추가_프로모션_상품_문의_재고_없으면_추가_묻지_않음_할인_미적용은_물어봄_YES() {
        assertSimpleTest(() -> {
            run("[사이다-8]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "내실돈6,000");
        });
    }

    @Test
    void 추가_프로모션_상품_문의_재고_없으면_추가_묻지_않음_할인_미적용은_물어봄_NO() {
        assertSimpleTest(() -> {
            run("[사이다-8]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "내실돈4,000");
        });
    }

    @Test
    void 추가_프로모션_상품_문의_YES() {
        assertSimpleTest(() -> {
            run("[초코바-3]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "초코바4",
                    "내실돈2,400");
        });
    }

    @Test
    void 추가_프로모션_상품_문의_NO() {
        assertSimpleTest(() -> {
            run("[초코바-3]", "N", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains(
                    "초코바3",
                    "내실돈2,400");
        });
    }

    @Test
    void 잘못된_구매_형식_입력_예외() {
        assertSimpleTest(() -> {
            runException("[물1]", "[물-1]", "N", "N");
            assertThat(output()).contains("[ERROR] 구매 입력 형식에 맞지 않습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void YorN외_입력_예외() {
        assertSimpleTest(() -> {
            runException("[물-1]", "T", "N", "N");
            assertThat(output()).contains("[ERROR] Y/N 중 하나를 입력해주세요. 다시 입력해 주세요.");
        });
    }

    @Test
    void 재고_초과_예외() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 구매_입력_재시도() {
        assertSimpleTest(() -> {
            run("[물1]", "[물-1]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈500");
        });
    }

    @Test
    void YorN_재시도() {
        assertSimpleTest(() -> {
            run("[물-1]", "T", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈500");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
